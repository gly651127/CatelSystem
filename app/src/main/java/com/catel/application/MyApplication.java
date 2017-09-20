
package com.catel.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.catel.jpush.Logger;
import com.catel.util.TtsHelper;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();
    public static Context mContext;
    private static MyApplication instance;
    public static int[] size;
    public static Handler mHandler = new Handler();


    @Override
    public void onCreate() {
        Logger.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        TtsHelper.getInstance().init(this); // 设置开启日志,发布时请关闭日志
        Set<String> tag = new HashSet<>();
        tag.add("tag2");
        Log.d(TAG, "aaaaaaaaaaaaaaaa");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);            // 初始化 JPush
        initJpushNotifycation();
        JPushInterface.setAliasAndTags(mContext, "gly", tag, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d(TAG, s + set.toString());
            }
        });
    }

    private void initJpushNotifycation() {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(mContext);
//        builder.statusBarDrawable = R.drawable.ic_launcher;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
//                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
//        builder.notificationDefaults = Notification.DEFAULT_SOUND
//                | Notification.DEFAULT_VIBRATE
//                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(0, builder);
    }


    public static MyApplication getInstance() {
        return instance;
    }

}
