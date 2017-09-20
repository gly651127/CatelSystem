package com.catel.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.catel.application.MyApplication;
import com.catel.fragment.ControlFragment;
import com.catel.fragment.MessageFragment;
import com.catel.fragment.SetCenterFragment;
import com.catel.jpush.LocalBroadcastManager;
import com.catel.jpush.Logger;
import com.catel.modle.ControlInfo;
import com.catel.modle.MessageInfo;
import com.catel.system.R;
import com.catel.util.AnimFragmentUtil;
import com.catel.util.ScreenUtil;
import com.catel.util.TtsHelper;
import com.catel.views.popuJarLib.popuJar.PopuItem;
import com.catel.views.popuJarLib.popuJar.PopuJar;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.listener.TabEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static boolean isForeground;
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.imb_left)
    ImageButton imbLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imb_right)
    ImageButton imbRight;
    private AnimFragmentUtil animFragmentUtil;
    private MessageFragment messageFragment;
    private ControlFragment controlFragment;
    private SetCenterFragment centerFragment;
    private Context mContext;

    private List<MessageInfo> messageDatas = new ArrayList<>();
    private List<ControlInfo> controlDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        MyApplication.size = ScreenUtil.getScreenDisplay(this);
        animFragmentUtil = new AnimFragmentUtil(getSupportFragmentManager(), R.id.continor);
        ButterKnife.bind(this);
        initTabLayout();
        initFragments();
        registerMessageReceiver();
    }

    private void initFragments() {
        for (int i = 0; i < 3; i++) {
            messageDatas.add(new MessageInfo(i + "号库房", i + "00" + i + "楼", "呼叫服务", i % 2 == 0 ? true : false));
        }
        for (int i = 0; i < 30; i++) {
            controlDatas.add(new ControlInfo(i + "号库房", i + "00"));
        }

        messageFragment = new MessageFragment();
        messageFragment.setData(messageDatas);
        controlFragment = new ControlFragment();
        controlFragment.setData(controlDatas);
        centerFragment = new SetCenterFragment();
        animFragmentUtil.selectFragment(messageFragment);
    }

    private void initTabLayout() {
        ArrayList<CustomTabEntity> datas = new ArrayList<>();
        datas.add(new TabEntity("消息管理", R.drawable.messagecontrol_pressed, R.drawable.messagecontrol_unpressed));
        datas.add(new TabEntity("设备管理", R.drawable.equipmentcontrol_pressed, R.drawable.equipmentcontrol_unpressed));
        datas.add(new TabEntity("设置中心", R.drawable.setting_pressed, R.drawable.setting_unpressed));
        commonTabLayout.setTabData(datas);
        commonTabLayout.setTextUnselectColor(getResources().getColor(R.color.black));
        commonTabLayout.setTextSelectColor(getResources().getColor(R.color.fontcolors14));
        commonTabLayout.setTextSelectColor(R.color.fontcolors4);
        commonTabLayout.setTextUnselectColor(Color.BLACK);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int i) {
                switch (i) {
                    case 0:
                        animFragmentUtil.selectFragment(messageFragment);
                        tvTitle.setText("消息管理");
                        break;
                    case 1:
                        animFragmentUtil.selectFragment(controlFragment);
                        tvTitle.setText("设备管理");
                        break;
                    case 2:
                        animFragmentUtil.selectFragment(centerFragment);
                        tvTitle.setText("设置中心");
                        break;
                }
            }

            @Override
            public void onTabReselect(int i) {

            }
        });

//        commonTabLayout.showMsg(0, 55);
//        commonTabLayout.setMsgMargin(0, -5, 5);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        animFragmentUtil.refreshAll();
        Logger.d("aaa", messageDatas.size() + "");
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "tv_title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        if (null != mMessageReceiver) {
            mMessageReceiver = null;
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        }
        super.onDestroy();
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.d("MessageReceiver", "[MyReceiver] 接收Registration Id : " + intent.getAction());
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

                    String extraValue = intent.getStringExtra(KEY_EXTRAS);
                    if (null != extraValue) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(extraValue);
                            String code = jsonObject.get("code").toString();
                            messageDatas.add(new MessageInfo(code + "号库房", "1楼", "呼叫服务", false));
                            messageFragment.refresh();
                            Logger.d("jsonObject", jsonObject.toString());
                            String message = jsonObject.get("message").toString();
                            TtsHelper.getInstance().play(message);
                        } catch (JSONException e) {

                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setCostomMsg(String msg) {
//        if (null != msgText) {
//            msgText.setText(msg);
//            msgText.setVisibility(android.view.View.VISIBLE);
//        }
    }


    @OnClick({R.id.imb_left, R.id.imb_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imb_left:
                final PopuJar leftPopuJar = new PopuJar(mContext);
                leftPopuJar.setMarginT(true);
                leftPopuJar.addPopuItem(new PopuItem(0, getResources().getString(R.string.mark_served)));
                leftPopuJar.addPopuItem(new PopuItem(1, getResources().getString(R.string.clear_view)));
                leftPopuJar.addPopuItem(new PopuItem(1, getResources().getString(R.string.recove_view)));
                leftPopuJar.setAnimStyle(PopuJar.ANIM_REFLECT);
                leftPopuJar.setBackground(R.drawable.left_popu_bg);
                //  设置屏幕的背景透明度(需要在PopupWindows类打开backgroundAlpha()方法)
                leftPopuJar.setOnDismissListener(new PopuJar.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        leftPopuJar.backgroundAlpha((Activity) mContext, 1f);//1.0-0.0
                    }
                });

                leftPopuJar.setOnPopuItemClickListener(new PopuJar.OnPopuItemClickListener() {
                    @Override
                    public void onItemClick(PopuJar source, int pos, int actionId) {
                        switch (pos) {
                            case 0:
                                for (MessageInfo info : messageDatas) {
                                    info.setSure(true);
                                }
                                messageFragment.refresh();
                                break;
                            case 1:
                                messageDatas.clear();
                                messageFragment.refresh();
                                break;

                            default:
                                break;
                        }
                    }
                });
                leftPopuJar.show(view);

                break;
            case R.id.imb_right:
                final PopuJar rightPopu = new PopuJar(mContext);
                rightPopu.setMarginT(true);
                rightPopu.addPopuItem(new PopuItem(0, getResources().getString(R.string.mark_served)));
                rightPopu.addPopuItem(new PopuItem(1, getResources().getString(R.string.look_call_note)));
                rightPopu.setAnimStyle(PopuJar.ANIM_REFLECT);
                rightPopu.setBackground(R.drawable.right_popu_bg);
                //  设置屏幕的背景透明度(需要在PopupWindows类打开backgroundAlpha()方法)
                rightPopu.setOnDismissListener(new PopuJar.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        rightPopu.backgroundAlpha((Activity) mContext, 1f);//1.0-0.0
                    }
                });

                rightPopu.setOnPopuItemClickListener(new PopuJar.OnPopuItemClickListener() {
                    @Override
                    public void onItemClick(PopuJar source, int pos, int actionId) {
                        switch (pos) {
                            case 0:
                                for (MessageInfo info : messageDatas) {
                                    info.setSure(true);
                                }
                                break;

                            default:
                                break;
                        }
                    }
                });
                rightPopu.show(view);
                break;
        }
    }
}
