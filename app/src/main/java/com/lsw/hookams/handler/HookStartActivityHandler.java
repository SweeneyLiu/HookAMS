package com.lsw.hookams.handler;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by sweeneyliu on 2018/10/23.
 */
public class HookStartActivityHandler implements InvocationHandler {

        private static final String TAG = "HookStartActivityHandler";

        Object mBase;

        public HookStartActivityHandler (Object base) {
            mBase = base;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("startActivity".equals(method.getName())) {
                Log.e("invoke_startActivity", method.getName());
            }

            return method.invoke(mBase, args);
        }
}
