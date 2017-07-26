package com.android.engine;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

class ReceiveMasterCommunication implements Runnable {

  private final ExecuterControlCenter executerControlCenter;
  private final Socket socket;
  private ObjectInputStream objectInputStream;

  ReceiveMasterCommunication(Socket socket) {
    this.executerControlCenter = new ExecuterControlCenter();
    this.socket = socket;
    try {
      this.socket.setKeepAlive(true);
    } catch (SocketException e) {
    }
  }

  public void run() {
    while (true) {
      try {
        this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
        this.executerControlCenter.executeAction(this.objectInputStream.readObject());
      } catch (Exception e) {
        SendMasterCommunication.startSocket();
        return;
      }
    }
  }
}
