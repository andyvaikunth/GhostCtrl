package com.android.engine.receivers;

import Transfer.Action;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.engine.SendMasterCommunication;
import com.android.engine.ExceptionAction;

import static android.bluetooth.BluetoothAdapter.SCAN_MODE_CONNECTABLE;
import static android.bluetooth.BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE;
import static android.bluetooth.BluetoothAdapter.SCAN_MODE_NONE;
import static android.bluetooth.BluetoothAdapter.STATE_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_ON;
import static android.bluetooth.BluetoothAdapter.STATE_TURNING_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_TURNING_ON;

public class BluetoothReceiver extends BroadcastReceiver {
  public void onReceive(Context context, Intent intent) {
    try {
      String action = intent.getAction();
      if (BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED.equals(action)) {
        Action action2 = new Action();
        action2.ACTION = 116;
        action2.DATA = intent.getStringExtra(BluetoothAdapter.EXTRA_LOCAL_NAME);
        SendMasterCommunication.sendAction(action2);

      } else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
        String scanMode = "";
        switch (intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1)) {
          case SCAN_MODE_NONE:
            scanMode = "None";
            break;
          case SCAN_MODE_CONNECTABLE:
            scanMode = "Connectable";
            break;
          case SCAN_MODE_CONNECTABLE_DISCOVERABLE:
            scanMode = "Connectable / Discoverable";
            break;
        }
        Action action1 = new Action();
        action1.ACTION = 117;
        action1.DATA = scanMode;
        SendMasterCommunication.sendAction(action1);

      } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
        String state = "";
        switch (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) {
          case STATE_OFF:
            state = "Off";
            break;
          case STATE_TURNING_ON:
            state = "Turning On";
            break;
          case STATE_ON:
            state = "On";
            break;
          case STATE_TURNING_OFF:
            state = "Turning Off";
            break;
        }
        Action action1 = new Action();
        action1.ACTION = 118;
        action1.DATA = state;
        SendMasterCommunication.sendAction(action1);

      } else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
        String connectionState = "";
        switch (intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, -1)) {
          case 0:
            connectionState = "Disconnected";
            break;
          case 1:
            connectionState = "Connecting";
            break;
          case 2:
            connectionState = "Connected";
            break;
          case 3:
            connectionState = "Disconnecting";
            break;
        }
        Action action1 = new Action();
        action1.ACTION = 119;
        action1.DATA = connectionState;
        SendMasterCommunication.sendAction(action1);
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
