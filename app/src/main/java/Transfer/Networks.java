package Transfer;

import java.io.Serializable;

public class Networks implements Serializable {

  public String CAPABILITIES;
  public String DNSADDRESSES;
  public String DOMAINS;
  public int DOWNSTREAMBANDWITHKBPS;
  public String INTERFACENAME;
  public String LINKADDRESS;
  public String ROUTES;
  public String TRANSPORTS;
  public int UPSTREAMBANDWITHKBPS;

  public Networks() {
    CAPABILITIES = "";
    TRANSPORTS = "";
  }
}
