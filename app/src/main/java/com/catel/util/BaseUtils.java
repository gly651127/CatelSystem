package com.catel.util;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class BaseUtils {
  private static Context context;

  public static void initialize(@NonNull Context context) {
    BaseUtils.context = context;
  }
  public static Context getContext() {
    synchronized (BaseUtils.class) {
      if (BaseUtils.context == null)
        throw new NullPointerException("Call BaseUtils.initialize(mContext) within your Application onCreate() method.");

      return BaseUtils.context.getApplicationContext();
    }
  }
}
