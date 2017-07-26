package Transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Activity<T> implements Serializable {

  public List<T> LIST;
  public int TYPE;

  public Activity() {
    this.LIST = new ArrayList();
  }
}
