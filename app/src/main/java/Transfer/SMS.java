package Transfer;

import java.io.Serializable;

public class SMS implements Serializable {

  public String BODY;
  public long DATE;
  public boolean ISUSERSENDER;

  public SMS(long date, String body, boolean isUserSender) {
    this.DATE = date;
    this.BODY = body;
    this.ISUSERSENDER = isUserSender;
  }
}
