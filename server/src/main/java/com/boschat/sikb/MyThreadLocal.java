package com.boschat.sikb;

import com.boschat.sikb.api.CallType;

public class MyThreadLocal {

    private static final ThreadLocal<Context> threadLocal = new ThreadLocal<>();

    private MyThreadLocal() {

    }

    public static void init(CallType callType) {
        threadLocal.set(new Context(callType));
    }

    public static void unset() {
        threadLocal.remove();
    }

    public static Context get() {
        return threadLocal.get();
    }
}
