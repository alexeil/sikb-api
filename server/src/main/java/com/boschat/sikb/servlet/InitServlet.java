package com.boschat.sikb.servlet;

import com.boschat.sikb.configuration.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;

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
        try {
            reloadProperties();
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);
        } finally {
            LOGGER.info("Serveur started");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#destroy()
     */
    @Override
    public void destroy() {
        ConfigLoader.getInstance().clear();
    }
}
