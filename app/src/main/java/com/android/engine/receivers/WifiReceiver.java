package com.android.engine.receivers;

import Transfer.Action;
import Transfer.Wifi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.engine.SendMasterCommunication;

public class WifiReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    try {
      if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
        SendMasterCommunication.sendAction(new Wifi());
        return;
      }

      Action action = new Action();
      action.ACTION = 121;
      action.DATA = intent.getIntExtra("newRssi", -1);
      SendMasterCommunication.sendAction(action);
    } catch (Exception e) {
    }
  }
}
