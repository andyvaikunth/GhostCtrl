package com.android.engine;

import Transfer.Action;

public class ExceptionAction {

  public static void sendExceptionAction(String str) {
    try {
      Action action = new Action();
      action.ACTION = 71;
      action.DATA = str;
      SendMasterCommunication.sendAction(action);
    } catch (Exception e) {
    }
  }
}
