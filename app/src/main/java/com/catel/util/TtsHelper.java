package com.catel.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;

import static com.catel.application.MyApplication.mContext;

public class TtsHelper {
    private static String TAG = TtsHelper.class.getSimpleName();
    private static final TtsHelper instance = new TtsHelper();
    // 语音合成对象
    private SpeechSynthesizer mTts;
    private SharedPreferences mSharedPreferences;
    private SpeechLinstener speechLinstener;
    private boolean isInit = false;

    public void init(Context context) {
        SpeechUtility.createUtility(context, "APPID=59a513ff");
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
        mSharedPreferences = context.getSharedPreferences("mts", Context.MODE_PRIVATE);
    }

    public static TtsHelper getInstance() {
        return instance;
    }

    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int status) {
            isInit = true;
            if (null != speechLinstener) {
                speechLinstener.onInit(status);
            }
            Logger.d(mContext, "onInit: " + status);
//            if (status == TextToSpeech.SUCCESS) {/**如果装载TTS成功*/
//                int result = mTts.setLanguage(Locale.ENGLISH);/**有Locale.CHINESE,但是不支持中文*/
//                if (result == TextToSpeech.LANG_MISSING_DATA/**表示语言的数据丢失。*/
//                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {/**语言不支持*/
//                    Toast.makeText(mContext, "我说不出口", Toast.LENGTH_SHORT).show();
//                } else {
//                    mTts.speak("I miss you", TextToSpeech.QUEUE_FLUSH,
//                            null);
//                }
//            }
        }
    };

    public void stop() {
        if (mTts != null) {
            mTts.stopSpeaking();
        }
    }

    public void play(String text) {
        if (null == mTts) {
            return;
        }
        // 设置参数
        FlowerCollector.onEvent(mContext, "tts_play");
        setParam();
        int code = mTts.startSpeaking(text, mTtsListener);
//			/**
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
//            showTip("开始播放");
            if (null != speechLinstener) {
                speechLinstener.onStart();
            }
        }

        @Override
        public void onSpeakPaused() {
//            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
//            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
//            // 合成进度
//            mPercentForBuffering = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
//            mPercentForPlaying = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (null != speechLinstener) {
                speechLinstener.onDone();
            }

            if (error == null) {
//                showTip("播放完成");
            } else if (error != null) {
//                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                Log.d(TAG, "session id =" + sid);
            }
        }
    };


    public boolean isInit() {
        return isInit;
    }


    public boolean isSpeaking() {
        if (null != mTts) {
            return mTts.isSpeaking();

        } else return false;
    }

    public void setSpeechLinstener(SpeechLinstener speechLinstener) {
        this.speechLinstener = speechLinstener;

    }


    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

    public interface SpeechLinstener {
        void onInit(int status);

        void onStart();

        void onDone();

    }

}
