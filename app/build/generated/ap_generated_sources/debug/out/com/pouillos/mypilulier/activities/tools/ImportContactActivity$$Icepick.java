// Generated code from Icepick. Do not modify!
package com.pouillos.mypilulier.activities.tools;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class ImportContactActivity$$Icepick<T extends ImportContactActivity> extends com.pouillos.mypilulier.activities.NavDrawerActivity$$Icepick<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
      
  }

  private final static Helper H = new Helper("com.pouillos.mypilulier.activities.tools.ImportContactActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.importContact = H.getSerializable(state, "importContact");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putSerializable(state, "importContact", target.importContact);
  }
}