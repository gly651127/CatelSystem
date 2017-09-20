package com.catel.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by gly on 2017/3/27 0027.
 */

public class DelayStartHelper {
    private static DelayStarter DELAYSTARTER;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    if (null != DELAYSTARTER) {
                        DELAYSTARTER.start();
                    }
                    break;
                default:
                    break;
            }

        }
    };

    public DelayStartHelper(long time, DelayStarter delayStarter) {
        DELAYSTARTER = delayStarter;
        handler.removeMessages(100);
        handler.sendEmptyMessageDelayed(100, time);
        Log.d(DelayStartHelper.class.getSimpleName(), "start: " + time);
    }

    public interface DelayStarter {
        void start();
    }
}
