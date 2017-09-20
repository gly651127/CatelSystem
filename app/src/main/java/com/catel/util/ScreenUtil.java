package com.catel.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import static com.catel.application.MyApplication.mHandler;

@SuppressWarnings("deprecation")
public class ScreenUtil {


    public static void lightWindow(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        final PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");
        wakeLock.acquire();
//然后定时
        mHandler.postDelayed(new Runnable() {
            public void run() {
                wakeLock.release();
            }
        }, 10 * 1000);//延时10秒灭屏
    }

    /**
     * 获取屏幕可见范围的宽�??和高度，必须使用 XXView.post(new Runnable(){....}) 才可以获取到真实数据
     *
     * @param activity
     * @return
     */
    public static int[] getScreenVisiable(Activity activity) {
        // 获取状况栏高�??
        Rect frame = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取标题栏高�??
        // 获取到的view就是程序不包括标题栏的部�??
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT)
                .getTop();
        int titleBarHeight = contentViewTop - statusBarHeight;

        // 获取屏幕 宽度 �??高度
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels - statusBarHeight - titleBarHeight;
        return new int[]{screenWidth, screenHeight};
    }

    /**
     * 获取屏幕可见范围的宽�??和高度，必须使用 XXView.post(new Runnable(){....}) 才可以获取到真实数据
     *
     * @param activity
     * @return
     */
    public static int[] getScreenDisplay(Activity activity) {
        // 获取屏幕 宽度 �??高度
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return new int[]{screenWidth, screenHeight};
    }

    public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg

                .getWidth(), sourceImg.getHeight());// 获得图片的ARGB�?

        number = number * 255 / 100;

        for (int i = 0; i < argb.length; i++) {

            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

        }

        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg

                .getHeight(), Config.ARGB_8888);

        return sourceImg;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) // drawable
    // 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长�?
        int height = drawable.getIntrinsicHeight();
        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565;// 取drawable的颜色格�?
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画�?
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布�?
        return bitmap;
    }

    @SuppressLint("Wakelock")
    public static void lightScreen(Context context) {
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        // 获取电源管理器对�?
        PowerManager.WakeLock wl = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个�?,�?后的是LogCat里用的Tag
        wl.acquire();
        // 点亮屏幕
        KeyguardManager km = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        // 得到键盘锁管理器对象
        KeyguardLock kl = km.newKeyguardLock("unLock");
        // 参数是LogCat里用的Tag
        kl.disableKeyguard();
        // 解锁

		/*
         * 这里写程序的其他代码
		 */

        kl.reenableKeyguard();
        // 重新启用自动加锁
        wl.release();
    }

    @SuppressLint("Wakelock")
    public static boolean isLock(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        return mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    public static View getView(String id, Intent intent,
                               LocalActivityManager mlam) {
        return mlam.startActivity(id, intent).getDecorView();
    }
}
