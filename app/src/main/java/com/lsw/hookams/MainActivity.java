package com.lsw.hookams;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lsw.hookams.handler.HookStartActivityHandler;

import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {

    // 这个方法比onCreate调用早; 在这里Hook比较好.
    @Override
    protected void attachBaseContext(Context newBase) {
        HookHelper.hookActivityManager();
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button tv = new Button(this);
        tv.setText("测试界面");

        setContentView(tv);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 测试AMS HOOK (调用其相关方法)
                Intent intent = new Intent(MainActivity.this, TargetActivity.class);
                startActivity(intent);
            }
        });    }
}
