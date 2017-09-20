package com.catel.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.catel.system.R;
import com.catel.util.Logger;
import com.catel.util.ScreenUtil;
import com.catel.util.TtsHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.catel.application.MyApplication.TAG;

public class RingActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    public static MediaPlayer mediaPlayer = null;
    private List<String> messages = new ArrayList<>();
    private TtsHelper ttsHelper;
    private Context mContext;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        mContext = this;
        ButterKnife.bind(this);
        ttsHelper = TtsHelper.getInstance();
        ttsHelper.init(this);
        ttsHelper.setSpeechLinstener(speechLinstener);
        ScreenUtil.lightWindow(this);
//        startPlay();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        Logger.d(mContext, "2");
        Bundle b = getIntent().getExtras();
        if (null != b) {
            String message = b.getString(JPushInterface.EXTRA_MESSAGE);
            if (!TextUtils.isEmpty(message)) {
                if (!ttsHelper.isSpeaking()) {
                    Logger.d(mContext, "3");
                    ttsHelper.play(message);
                    tv_title.setText(message);
                } else {
                    Logger.d(mContext, "4");
                    messages.add(message);
                }
            }
        }
    }

    /**
     * 合成回调监听。
     */
    private TtsHelper.SpeechLinstener speechLinstener = new TtsHelper.SpeechLinstener() {
        @Override
        public void onInit(int status) {
            Logger.d(mContext, "onInit:" + status + "--" + messages.size());
            if (messages.size() > 0) {
                String mess = messages.get(0);
                ttsHelper.play(mess);
                tv_title.setText(mess);
                messages.remove(0);
            }
        }

        @Override
        public void onStart() {
            Logger.d(mContext, "onStart:");
        }

        @Override
        public void onDone() {
            Logger.d(mContext, "onDone:");
            if (messages.size() > 0) {
                final String s = messages.get(0);
                Log.d(TAG, "messages: " + s);
                RingActivity.this
                        .runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_title.setText(s);
                            }
                        });

                ttsHelper.play(s);
                messages.remove(0);
            }
        }
    };

    public void startPlay() {
        AssetManager assets = mContext.getAssets();
        // TODO Auto-generated method stub
        AssetFileDescriptor fileDescriptor;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("onCompletion");
                startPlay();
            }
        });
        try {
            fileDescriptor = assets.openFd("notify.mp3");
            mediaPlayer
                    .setDataSource(fileDescriptor.getFileDescriptor(),
                            fileDescriptor.getStartOffset(),
                            fileDescriptor.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mediaPlayer) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                onBackPressed();
                break;
            case R.id.button2:
                onBackPressed();
                break;
        }
    }

}
