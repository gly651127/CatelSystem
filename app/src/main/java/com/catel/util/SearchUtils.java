package com.catel.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class SearchUtils implements PopupWindow.OnDismissListener {
    private Activity mActivity;
    private View searchView;
    private AutoCompleteTextView ediSearch;
    private View alphaView;
    private TextView tvCancle;
    private PopupWindow popupWindow;
    private int moveHeight;
    private int statusBarHeight;
    private View fatherView;// 界面的跟布局
    private View topTitleView;// 标题栏布局
    public InputMethodManager imm;
    private EditText edit;

    /**
     * @param activity
     * @param rlTopTitle    ,标题栏布局,顶部标题栏的布局id
     * @param llFather      ,界面的跟布局,是整个界面的根布局id
     * @param //textWatcher ，文本框监听
     * @param //strHint     ,搜索框的提示语
     */
    public SearchUtils(Activity activity, View rlTopTitle, final View llFather, View searchView) {
        super();
        this.mActivity = activity;
        this.topTitleView = rlTopTitle;
        this.fatherView = llFather;
        this.searchView = searchView;
        initViews();
        initListener();
    }

    private void initListener() {
        // TODO Auto-generated method stub
//        Log.i("TAG", "edit==" + ediSearch.getText().toString());


//        alphaView.setOnClickListener(onClick);


        // tvCancle.setOnClickListener(onClick);
        // ediSearch.addTextChangedListener(textWatcher);
    }

    private void initViews() {
        // LayoutInflater mInflater = LayoutInflater.from(mActivity);
        // searchView = mInflater.inflate(R.layout.item_popup_search, null);

//        ediSearch = (AutoCompleteTextView) searchView
//                .findViewById(R.id.edi_search);
//        ediSearch.setHint(strHint);
//        ediSearch.setFocusable(true);
//        alphaView = searchView.findViewById(R.id.popup_window_v_alpha);
        // tvCancle = (TextView) searchView.findViewById(R.id.tvCanale);

        popupWindow = new PopupWindow(searchView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //dw.setAlpha(90);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOnDismissListener(this);

    }

    public void showSearchBar() {
        getStatusBarHeight();
        moveHeight = topTitleView.getHeight();
        Animation translateAnimation = new TranslateAnimation(0, 0, 0,
                -moveHeight);
        translateAnimation.setDuration(300);
        fatherView.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 0);
                fatherView.setAnimation(anim);
                topTitleView.setVisibility(View.GONE);
                topTitleView.setPadding(0, -moveHeight, 0, 0);

                popupWindow.showAtLocation(fatherView, Gravity.CLIP_VERTICAL,
                        0, statusBarHeight);
                openKeyboard();
            }
        });


    }

//    OnClickListener onClick = new OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            // TODO Auto-generated method stub
//            switch (v.getId()) {
////                case R.id.popup_window_v_alpha:
////                    dismissPopupWindow();
////                    break;
//                case R.id.tvCanale:
//                    dismissPopupWindow();
//                    break;
//            }
//        }
//    };

    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            resetUI();
        }
    }

    private void getStatusBarHeight() {
        Rect frame = new Rect();
        mActivity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(frame);// 这里得到的是除了系统自带显示区域之外的所有区域，这里就是除了最上面的一条显示电量的状态栏之外的所有区域
        statusBarHeight = frame.top; // 这里便可以得到状态栏的高度，即最上面一条显示电量，信号,时间等

    }

    /**
     * 展开键盘
     */
    private void openKeyboard() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {


            @Override
            public void run() {
                imm = (InputMethodManager) mActivity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }

    public void resetUI() {
        topTitleView.setPadding(0, 0, 0, 0);
        topTitleView.setVisibility(View.VISIBLE);
        Animation translateAnimation = new TranslateAnimation(0, 0,
                -moveHeight, 0);
        translateAnimation.setDuration(300);
        fatherView.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 0);
                fatherView.setAnimation(anim);
            }
        });
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }


    @Override
    public void onDismiss() {
        resetUI();
        if (null != edit) {
            edit.setText("");
        }
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public void setEditText(EditText edit) {
        this.edit = edit;
    }
}
