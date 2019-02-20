package com.boschat.sikb;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.boschat.sikb.AbstractTest.initContext;
import static com.boschat.sikb.AbstractTest.initJerseyTest;
import static com.boschat.sikb.AbstractTest.initServlet;
import static com.boschat.sikb.AbstractTest.shutDownJerseyTest;
import static com.boschat.sikb.AbstractTest.wiser;
import static com.boschat.sikb.PersistenceUtils.executeScript;

public class JerseyTestExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static boolean started = false;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!started) {
            started = true;
            initJerseyTest();
            initContext();
            executeScript("initDb.sql");
            executeScript("initData.sql");
        }
    }

    public void close() throws Exception {
        shutDownJerseyTest();
        initServlet.destroy();
        wiser.stop();
    }
}
