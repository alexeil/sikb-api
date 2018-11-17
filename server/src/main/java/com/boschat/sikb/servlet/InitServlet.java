package com.boschat.sikb.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;

/**
 * The Class InitServlet.
 */
public class InitServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(InitServlet.class);

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() {
        LOGGER.trace("init servlet");
      /*  try {
            ErableServlet.reloadFamilyApp();
        } catch (Throwable t) {
            LOGGER.error(new LogMetadata(INI_SER_FAIL, t));
        } finally {
            LOGGER.info(new LogMetadata(INI_SER, INI_SER.getMessage()));
        }*/
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#destroy()
     */
    @Override
    public void destroy() {
        // ConfigLoader.getInstance().clear();
    }
}
