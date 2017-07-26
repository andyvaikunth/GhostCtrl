package Transfer;

import java.io.Serializable;

public class TransferFile implements Serializable {

  public byte[] CONTENT;
  public String NAME;

  public TransferFile(long j) {
    this.CONTENT = new byte[((int) j)];
  }
}
