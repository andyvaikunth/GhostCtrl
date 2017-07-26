package Transfer;

import android.content.Intent;
import android.content.IntentFilter;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

public class Battery implements Serializable {

  public String HEALTH;
  public int LEVEL;
  public String PLUGGED;
  public boolean PRESENT;
  public int SCALE;
  public String STATUS;
  public String TECHNOLOGY;
  public int TEMPERATURE;
  public int VOLTAGE;

  public Battery() {
    try {
      Intent registerReceiver = MyService.context.registerReceiver(null,
          new IntentFilter("android.intent.action.BATTERY_CHANGED"));
      if (registerReceiver.getIntExtra("health", -1) == 7) {
        HEALTH = "Cold";
      } else if (registerReceiver.getIntExtra("health", -1) == 4) {
        HEALTH = "Dead";
      } else if (registerReceiver.getIntExtra("health", -1) == 2) {
        HEALTH = "Good";
      } else if (registerReceiver.getIntExtra("health", -1) == 5) {
        HEALTH = "Over Voltage";
      } else if (registerReceiver.getIntExtra("health", -1) == 3) {
        HEALTH = "Overheat";
      } else if (registerReceiver.getIntExtra("health", -1) == 1) {
        HEALTH = "Unknown";
      } else if (registerReceiver.getIntExtra("health", -1) == 6) {
        HEALTH = "Unspeicified Failure";
      }

      LEVEL = registerReceiver.getIntExtra("level", -1);

      if (registerReceiver.getIntExtra("plugged", -1) == 1) {
        PLUGGED = "AC";
      } else if (registerReceiver.getIntExtra("plugged", -1) == 2) {
        PLUGGED = "USB";
      } else if (registerReceiver.getIntExtra("plugged", -1) == 4) {
        PLUGGED = "Wireless";
      } else {
        PLUGGED = "None";
      }

      PRESENT = registerReceiver.getBooleanExtra("present", false);
      SCALE = registerReceiver.getIntExtra("scale", -1);

      if (registerReceiver.getIntExtra("status", -1) == 2) {
        STATUS = "Charging";
      } else if (registerReceiver.getIntExtra("status", -1) == 3) {
        STATUS = "Discharging";
      } else if (registerReceiver.getIntExtra("status", -1) == 5) {
        STATUS = "Full";
      } else if (registerReceiver.getIntExtra("status", -1) == 4) {
        STATUS = "Not Charging";
      } else if (registerReceiver.getIntExtra("status", -1) == 1) {
        STATUS = "Unknown";
      }

      TECHNOLOGY = registerReceiver.getStringExtra("technology");
      TEMPERATURE = registerReceiver.getIntExtra("temperature", -1) / 10;
      VOLTAGE = registerReceiver.getIntExtra("voltage", -1);
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
