package com.boschat.sikb;

public class MyThreadLocal {

    private static final ThreadLocal threadLocal = new ThreadLocal();

    public static void init() {
        threadLocal.set(new Context());
    }

    public static void unset() {
        threadLocal.remove();
    }

    public static Context get() {
        return (Context) threadLocal.get();
    }
}
