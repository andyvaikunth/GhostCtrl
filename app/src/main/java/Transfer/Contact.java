package Transfer;

import java.io.Serializable;

public class Contact implements Serializable {

  public String ADDRESS;
  public String NAME;

  public boolean equals(Object obj) {
    Contact contact = (Contact) obj;
    return contact.NAME.equals(this.NAME) && contact.ADDRESS.equals(this.ADDRESS);
  }
}
