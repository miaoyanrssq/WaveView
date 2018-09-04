package cn.zgy.waveview;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPoolUtils {
    // SoundPool对象
    public static SoundPool mSoundPlayer = new SoundPool(2,
            AudioManager.STREAM_SYSTEM, 5);
    public static SoundPoolUtils soundPlayUtils;
    // 上下文
    static Context mContext;

    /**
     * 初始化
     *
     * @param context
     */
    public static SoundPoolUtils init(Context context) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPoolUtils();
        }

        // 初始化声音
        mContext = context;

        mSoundPlayer.load(mContext, R.raw.vbf, 1);// 1
        mSoundPlayer.load(mContext, R.raw.vnl, 1);// 2
        mSoundPlayer.load(mContext, R.raw.v1_e, 1);// 2
        mSoundPlayer.load(mContext, R.raw.v1_je, 1);// 2
        mSoundPlayer.load(mContext, R.raw.v3_e, 1);// 2
        mSoundPlayer.load(mContext, R.raw.v3_je, 1);// 2

        return soundPlayUtils;
    }

    /**
     * 播放声音
     *
     * @param soundID
     */
    public static void play(int soundID) {
        mSoundPlayer.play(soundID, 1, 1, 0, 0, 1);
    }


}