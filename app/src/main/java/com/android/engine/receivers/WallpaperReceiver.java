package com.android.engine.receivers;

import Transfer.Wallpaper;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.engine.SendMasterCommunication;

public class WallpaperReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    SendMasterCommunication.sendAction(new Wallpaper());
  }
}
