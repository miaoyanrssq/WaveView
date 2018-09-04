package cn.zgy.waveview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{


    TextView short_num, long_num, start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFullWindow();
        setContentView(R.layout.activity_splash);
        initView();

    }



    private void initView() {
        short_num = findViewById(R.id.short_num);
        long_num = findViewById(R.id.long_num);
        start = findViewById(R.id.start);
        start.setOnClickListener(this);
    }

    private void initData() {

        short_num.setText(RxSPTool.getInt(this, "shortNum") + "");
        long_num.setText(RxSPTool.getInt(this, "longNum") + "");

    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 配置窗口属性 设置全屏
     */
    private void initFullWindow() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View view) {

        startActivity(new Intent(this, MainActivity.class));

    }
}
