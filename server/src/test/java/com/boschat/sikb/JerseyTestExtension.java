package com.boschat.sikb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.boschat.sikb.AbstractTest.initContext;
import static com.boschat.sikb.AbstractTest.initJerseyTest;
import static com.boschat.sikb.AbstractTest.initServlet;
import static com.boschat.sikb.AbstractTest.shutDownJerseyTest;
import static com.boschat.sikb.AbstractTest.wiser;
import static com.boschat.sikb.PersistenceUtils.executeScript;

public class JerseyTestExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static final Logger LOGGER = LogManager.getLogger(JerseyTestExtension.class);

    private static boolean started = false;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!started) {
            long start = System.currentTimeMillis();
            started = true;

            initContext();
            initJerseyTest();

            executeScript("../persistence/src/main/resources/initdb.sql");
            executeScript("../persistence/src/main/resources/initData.sql");

            LOGGER.info("Init full Tests context : " + (System.currentTimeMillis() - start) + " ms");
        }
    }

    public void close() throws Exception {
        shutDownJerseyTest();
        initServlet.destroy();
        wiser.stop();
    }
}
