package cn.zgy.waveview;

import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long DURATION_SHORT = 3000;
    private static final long DURATION_LONG = 10000;
    RxWaveView rxWaveView;
    RxWaveHelper rxWaveHelper;

    TextView time1, time2, btn1, btn2, time, notice;
    SeekBar seekBar;

    private int mBorderColor = Color.parseColor("#00BFFF");
    private int mBorderWidth = 10;

    MyHandler handler;
    private long duration = 3000;
    boolean isFull = false;

    private int short_time = -1;
    private int long_time = -1;

    private int countdown = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFullWindow();
        setContentView(R.layout.activity_main);
        initView();
        handler = new MyHandler(this);
        SoundPoolUtils.init(this);

    }

    private void initView() {
        rxWaveView = findViewById(R.id.wave);
        rxWaveView.setBorder(mBorderWidth, mBorderColor);
        rxWaveView.setShowWave(true);
        rxWaveHelper = new RxWaveHelper(rxWaveView);

        time = findViewById(R.id.time);
        notice = findViewById(R.id.notice);

        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                duration = i * 1000;
                SoundPoolUtils.play(2);
                reset();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void reset(){
        time.setText(0 + "");
        handler.removeMessages(0);
        handler.removeMessages(1);
        isFull = false;
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        rxWaveHelper.cancel();
        handler.removeMessages(0);
        handler.removeMessages(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn1) {
            duration = DURATION_SHORT;
            SoundPoolUtils.play(1);
        } else if (id == R.id.btn2) {
            duration = DURATION_LONG;
            SoundPoolUtils.play(2);
        }
        reset();
    }

    public static class MyHandler extends Handler{
        static MainActivity activity;
        public MyHandler(AppCompatActivity appCompatActivity) {
            activity = (MainActivity) appCompatActivity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                if(activity.isFull){
                    SoundPoolUtils.play(5);
                    activity.notice.setText("relax");
                    activity.rxWaveHelper.start_down();
                    activity.isFull = false;
                    activity.handler.sendEmptyMessageDelayed(0, activity.duration);
                }else{
                    SoundPoolUtils.play(3);
                    if(activity.duration == DURATION_SHORT){
                        activity.time1.setText( ++activity.short_time + "");
                    }else{
                        activity.time2.setText( ++activity.long_time + "");
                    }
                    activity.notice.setText("tight");
                    activity.rxWaveHelper.start_up();
                    activity.isFull = true;
                    activity.handler.sendEmptyMessageDelayed(0, activity.duration);
                }
                activity.countdown = (int)activity.duration/1000 - 2;
                activity.handler.sendEmptyMessageDelayed(1, 2000);
            }else if(msg.what == 1){
                if(activity.countdown >= 0){
                    activity.time.setText(activity.countdown -- + "");
                    activity.handler.removeMessages(1);
                    activity.handler.sendEmptyMessageDelayed(1, 1000);
                }

            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int shortNum = RxSPTool.getInt(this, "shortNum") + Integer.parseInt(time1.getText().toString());
            int longNum = RxSPTool.getInt(this, "longNum") + Integer.parseInt(time2.getText().toString());

            RxSPTool.putInt(this,"shortNum", shortNum);
            RxSPTool.putInt(this,"longNum", longNum);
        }
        return super.onKeyDown(keyCode, event);
    }



    /**
     * 配置窗口属性 设置全屏
     */
    private void initFullWindow() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
