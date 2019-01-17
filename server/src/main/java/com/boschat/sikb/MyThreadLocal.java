package com.boschat.sikb;

import com.boschat.sikb.api.CallType;

public class MyThreadLocal {

    private static final ThreadLocal threadLocal = new ThreadLocal();

    public static void init(CallType callType) {
        threadLocal.set(new Context(callType));
    }

    public static void unset() {
        threadLocal.remove();
    }

    public static Context get() {
        return (Context) threadLocal.get();
    }
}
