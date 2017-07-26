package com.android.engine.receivers;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.engine.SendMasterCommunication;

public class BluetoothDiscoveryReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    try {
      String action = intent.getAction();
      if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
        SendMasterCommunication.sendAction(114);
      } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
        SendMasterCommunication.sendAction(115);
      }
    } catch (Exception e) {
    }
  }
}
