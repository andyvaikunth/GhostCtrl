package com.android.engine.ui.view;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.android.engine.ExceptionAction;

import static android.view.SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS;

public class CustomSurfaceView extends SurfaceView implements Callback {

  private SurfaceHolder surfaceHolder;
  private Camera camera;

  public CustomSurfaceView(Context context, Camera camera) {
    super(context);

    try {
      this.camera = camera;
      this.surfaceHolder = getHolder();
      this.surfaceHolder.addCallback(this);
      this.surfaceHolder.setType(SURFACE_TYPE_PUSH_BUFFERS);
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    try {
      if (this.surfaceHolder.getSurface() != null) {
        try {
          this.camera.stopPreview();
        } catch (Exception e) {
        }

        try {
          this.camera.setPreviewDisplay(this.surfaceHolder);
          this.camera.startPreview();
        } catch (Exception e2) {
        }
      }
    } catch (Exception e3) {
      ExceptionAction.sendExceptionAction(e3.getMessage());
    }
  }

  public void surfaceCreated(SurfaceHolder surfaceHolder) {
    try {
      this.camera.setPreviewDisplay(surfaceHolder);
      this.camera.startPreview();
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
  }
}
