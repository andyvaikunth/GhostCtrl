package Transfer;

import java.io.Serializable;

public class Services implements Serializable {

  public long ACTIVESINCE;
  public String CLASSNAME;
  public String FLAGS;
  public long LASTACTIVITYTIME;
  public String PACKAGENAME;
  public int PID;
  public String PROCESS;
  public boolean STARTED;
  public int UID;

  public Services() {
    this.FLAGS = "";
  }
}
