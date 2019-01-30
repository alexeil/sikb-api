package com.boschat.sikb.servlet;

import com.boschat.sikb.common.configuration.ConfigLoader;
import com.boschat.sikb.common.configuration.EnvVar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import java.util.Arrays;

import static com.boschat.sikb.servlet.ReloadServlet.reloadProperties;

public class InitServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(InitServlet.class);

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() {
        LOGGER.trace("Server starting...");
        printEnvs();
        try {
            reloadProperties();
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);
        } finally {
            LOGGER.info("Server started");
        }
    }

    private void printEnvs() {
        Arrays.stream(EnvVar.values()).forEach(this::printEnv);
    }

    private void printEnv(EnvVar envVar) {
        String value;
        if (envVar.isPrintable()) {
            value = envVar.getValue();
        } else {
            value = "******";
        }
        LOGGER.trace(envVar.getEnv() + " : " + value);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#destroy()
     */
    @Override
    public void destroy() {
        LOGGER.trace("Server destroyed...");
        ConfigLoader.getInstance().clear();
    }
}
