package com.lsw.hookams.handler;

import android.util.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by sweeneyliu on 2018/10/22.
 */
public class HookHandler  implements InvocationHandler {

    private static final String TAG = "HookHandler";

    private Object mBase;

    public HookHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "hey, baby; you are hooked!!");
        Log.d(TAG, "method:" + method.getName() + " called with args:" + Arrays.toString(args));

        return method.invoke(mBase, args);
    }
}
