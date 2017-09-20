package com.catel.util;

import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2017/8/14.
 */

public class Logger {

    public static void d(Context context, String mess) {
        Log.d(context.getClass().getSimpleName(), mess);
    }
}
