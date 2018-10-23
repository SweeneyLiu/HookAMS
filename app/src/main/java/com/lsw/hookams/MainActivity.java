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

    /**
     * Created by sweeneyliu on 2018/10/22.
     */
    public static final class HookHelper {

        public static void hookActivityManager() {
            try {
                //获取AMN的gDefault单例gDefault，gDefault是静态的
                Object gDefault = RefInvoke.getFieldObject("android.app.ActivityManagerNative", null,"gDefault");

                // gDefault是一个 android.util.Singleton对象; 我们取出这个单例里面的mInstance字段，IActivityManager类型
                Object rawIActivityManager = RefInvoke.getFieldObject(
                        "android.util.Singleton",
                        gDefault, "mInstance");


                // 创建一个这个对象的代理对象iActivityManagerInterface, 然后替换这个字段, 让我们的代理对象帮忙干活
                Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
                Object proxy = Proxy.newProxyInstance(
                        Thread.currentThread().getContextClassLoader(),
                        new Class<?>[] { iActivityManagerInterface },
    //                    new HookHandler(rawIActivityManager));
                        new HookStartActivityHandler(rawIActivityManager));

                //把Singleton的mInstance替换为proxy
                RefInvoke.setFieldObject("android.util.Singleton", gDefault, "mInstance", proxy);

            } catch (Exception e) {
                throw new RuntimeException("Hook Failed", e);
            }
        }
    }
}
