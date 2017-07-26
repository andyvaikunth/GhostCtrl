package com.android.engine;

import android.annotation.TargetApi;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

@TargetApi(18) public class NotificationListener extends NotificationListenerService {

  public void onNotificationPosted(StatusBarNotification statusBarNotification) {
    // TODO
  }

  public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
    // TODO
  }
}
