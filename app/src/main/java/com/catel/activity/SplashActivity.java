package com.catel.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.catel.application.MyApplication;
import com.catel.system.R;
import com.catel.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/18.
 */

public class SplashActivity extends BaseActivity {
    int count = 5;
    @BindView(R.id.btn_count)
    Button btnCount;
    @BindView(R.id.tv_vision)
    TextView tvVision;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        MyApplication.size = ScreenUtil.getScreenDisplay(this);
        tvVision.setText(getVersion());
        handler.sendEmptyMessageDelayed(100, 1000);
    }


    // 判断手机中是否安装了讯飞语音+

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    if (0 >= --count) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        handler.removeMessages(100);
                        SplashActivity.this.finish();
                    } else {
                        if (count >= 0) {
                            btnCount.setText(count + "s");
                            handler.sendEmptyMessageDelayed(100, 1000);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @OnClick(R.id.btn_count)
    public void onViewClicked() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        handler.removeMessages(100);
        this.finish();
    }
}
