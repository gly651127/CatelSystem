package com.catel.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.catel.application.MyApplication;


/**
 * Created by Administrator on 2016/11/30 0030.
 */

public class AnimUtils {
    /*
    *旋转按钮动画
    */
    public static void rotateAnim(View v, int rotate) {
        int start;
        int end;
        int mode;
        if (null == v.getAnimation() || v.getAnimation().getRepeatMode() == 0) {
            start = 0;
            end = rotate;
            mode = 1;
        } else {
            end = 0;
            start = rotate;
            mode = 0;
        }
        RotateAnimation animation = new RotateAnimation(start, end,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setRepeatMode(mode);
        animation.setDuration(300);
        animation.setFillAfter(true);
        v.startAnimation(animation);
    }


    /*
    * 伸缩动画
    * percent 缩放百分比  <=1  >=0
    */

    public static void scalAnim(final View v, float percent) {
        float oldHeight = SharedPreferencesUtil.getInt(MyApplication.mContext, "scalAnim" + v.getId());
        if (-1 == oldHeight || 0 == oldHeight) {
            oldHeight = v.getHeight();
            SharedPreferencesUtil.putInt(MyApplication.mContext, "scalAnim" + v.getId(), v.getHeight());
        }

        float startHeight = v.getHeight();
        float endHeight = 0;
        if (startHeight == oldHeight) {
            endHeight = oldHeight * (1f - percent);
        } else {
            endHeight = oldHeight;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(startHeight, endHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float height = (float) animation.getAnimatedValue();
                v.getLayoutParams().height = (int) height;
                v.requestLayout();
            }
        });
        final float finalOldHeight = oldHeight;
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (v.getVisibility() == View.GONE) {
                    v.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(300);
        animator.start();
    }

}
