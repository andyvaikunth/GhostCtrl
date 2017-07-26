package com.android.engine.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.engine.service.MyService;

import static android.content.Intent.ACTION_BOOT_COMPLETED;

public class MyBroadcastReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(ACTION_BOOT_COMPLETED)) {
      context.startService(new Intent(context, MyService.class));
    }
  }
}
