package com.boschat.sikb;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.boschat.sikb.AbstractTest.initJerseyTest;
import static com.boschat.sikb.AbstractTest.shutDownJerseyTest;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class JerseyTestExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static boolean started = false;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!started) {
            started = true;
            initJerseyTest();
            // Your "before all tests" startup logic goes here
            // The following line registers a callback hook when the root test context is shut down
            context.getRoot().getStore(GLOBAL).put("any unique name", this);
        }
    }

    public void close() throws Exception {
        shutDownJerseyTest();
    }
}
