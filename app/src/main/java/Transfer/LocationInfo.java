package Transfer;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocationInfo implements Serializable {

  public List<Provider> PROVIDERS;

  public LocationInfo() {
    PROVIDERS = new ArrayList();
    try {
      LocationManager locationManager =
          (LocationManager) MyService.context.getSystemService(Context.LOCATION_SERVICE);
      for (String str : locationManager.getAllProviders()) {
        LocationProvider provider = locationManager.getProvider(str);
        Provider provider2 = new Provider();
        if (provider.getAccuracy() == 2) {
          provider2.ACCURACY = "Coarse";
        } else if (provider.getAccuracy() == 1) {
          provider2.ACCURACY = "Fine";
        } else if (provider.getAccuracy() == 3) {
          provider2.ACCURACY = "High";
        } else if (provider.getAccuracy() == 1) {
          provider2.ACCURACY = "Low";
        } else if (provider.getAccuracy() == 2) {
          provider2.ACCURACY = "Medium";
        }

        provider2.HASMONETARYCOST = provider.hasMonetaryCost();
        provider2.NAME = provider.getName();

        if (provider.getPowerRequirement() == 0) {
          provider2.POWERREQUIREMENT = "No Requirement";
        } else if (provider.getPowerRequirement() == 3) {
          provider2.POWERREQUIREMENT = "High";
        } else if (provider.getPowerRequirement() == 1) {
          provider2.POWERREQUIREMENT = "Low";
        } else if (provider.getPowerRequirement() == 2) {
          provider2.POWERREQUIREMENT = "Medium";
        }

        provider2.REQUIRESCELL = provider.requiresCell();
        provider2.REQUIRESNETWORK = provider.requiresNetwork();
        provider2.REQUIRESSATELLITE = provider.requiresSatellite();
        provider2.SUPPORTSALTITUDE = provider.supportsAltitude();
        provider2.SUPPORTSBEARING = provider.supportsBearing();
        provider2.SUPPORTSSPEED = provider.supportsSpeed();
        provider2.ENABLED = locationManager.isProviderEnabled(str);

        Location lastKnownLocation = locationManager.getLastKnownLocation(str);
        if (lastKnownLocation != null) {
          if (lastKnownLocation.hasAccuracy()) {
            provider2._ACCURACY = String.valueOf(lastKnownLocation.getAccuracy());
          } else {
            provider2._ACCURACY = "Not Available";
          }

          if (lastKnownLocation.hasAltitude()) {
            provider2.ALTITUDE = String.valueOf(lastKnownLocation.getAltitude());
          } else {
            provider2.ALTITUDE = "Not Available";
          }

          if (lastKnownLocation.hasBearing()) {
            provider2.BEARING = String.valueOf(lastKnownLocation.getBearing());
          } else {
            provider2.BEARING = "Not Available";
          }

          if (lastKnownLocation.hasSpeed()) {
            provider2.SPEED = String.valueOf(lastKnownLocation.getSpeed());
          } else {
            provider2.SPEED = "Not Available";
          }

          provider2.LATITUDE = String.valueOf(lastKnownLocation.getLatitude()).replace(",", ".");
          provider2.LONGITUDE = String.valueOf(lastKnownLocation.getLongitude()).replace(",", ".");

          if (VERSION.SDK_INT >= 18) {
            provider2.ISFROMMOCKPROVIDER = lastKnownLocation.isFromMockProvider();
          } else {
            provider2.ISFROMMOCKPROVIDER = false;
          }
        }
        PROVIDERS.add(provider2);
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
