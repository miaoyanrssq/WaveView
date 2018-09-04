package cn.zgy.waveview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vondear
 */
public class RxWaveHelper {
    private RxWaveView mWaveView;


    private AnimatorSet mAnimatorSet_up;
    private AnimatorSet mAnimatorSet_dowm;

    List<Animator> upAnimators = new ArrayList<>();
    List<Animator> downAnimators = new ArrayList<>();

    public RxWaveHelper(RxWaveView waveView) {
        mWaveView = waveView;
        upAnimation();
        downAnimation();
    }

    public void start_up() {
        mWaveView.setShowWave(true);
        upAnimatorSet();
    }

    public void start_down(){
        mWaveView.setShowWave(true);
        downAnimatorSet();
    }

    private void upAnimation() {
        // horizontal animation.
        // wave waves infinitely.
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                mWaveView, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(5000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        upAnimators.add(waveShiftAnim);

        // vertical animation.
        // water level increases from 0 to center of RxWaveView
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                mWaveView, "waterLevelRatio", 0f, 1f);
        waterLevelAnim.setDuration(2000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        upAnimators.add(waterLevelAnim);

        // amplitude animation.
        // wave grows big then grows small, repeatedly
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                mWaveView, "amplitudeRatio", 0.0001f, 0.05f);
        amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(2000);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        upAnimators.add(amplitudeAnim);


    }



    private void downAnimation() {

        // horizontal animation.
        // wave waves infinitely.
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                mWaveView, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(5000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        downAnimators.add(waveShiftAnim);

        // vertical animation.
        // water level increases from 0 to center of RxWaveView
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                mWaveView, "waterLevelRatio", 1f, 0f);
        waterLevelAnim.setDuration(2000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        downAnimators.add(waterLevelAnim);

        // amplitude animation.
        // wave grows big then grows small, repeatedly
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                mWaveView, "amplitudeRatio", 0.0001f, 0.05f);
        amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(2000);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        downAnimators.add(amplitudeAnim);

    }

    private void upAnimatorSet(){
        //AnimatorSet在某些设备上只有执行第一次的时候有效，所以在此每次执行之前先清空AnimatorSet对象，再重新创建
        if(mAnimatorSet_up != null) {
            mAnimatorSet_up = null;
        }
        mAnimatorSet_up = new AnimatorSet();
        mAnimatorSet_up.playTogether(upAnimators);
        mAnimatorSet_up.start();
    }

    private void downAnimatorSet(){
        if(mAnimatorSet_dowm != null) {
            mAnimatorSet_dowm = null;
        }
        mAnimatorSet_dowm = new AnimatorSet();
        mAnimatorSet_dowm.playTogether(downAnimators);
        mAnimatorSet_dowm.start();
    }

    public void cancel() {
        if (mAnimatorSet_up != null) {
            mAnimatorSet_up.end();
        }
        if (mAnimatorSet_dowm != null) {
            mAnimatorSet_dowm.end();
        }
    }
}
