package com.boschat.sikb.context;

import com.boschat.sikb.api.CallType;

public class MyThreadLocal {

    private static final ThreadLocal<Context> threadLocal = new ThreadLocal<>();

    private MyThreadLocal() {

    }

    public static void init(CallType callType, String accessToken) {
        threadLocal.set(new Context(callType, accessToken));
    }

    public static void unset() {
        threadLocal.remove();
    }

    public static Context get() {
        return threadLocal.get();
    }
}
