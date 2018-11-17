package com.boschat.sikb;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by flocard on 07/04/2016.
 */
class StartServer {

    /**
     * The main method.
     * Pré-requis :
     * <p>
     * <ul>
     * <li>Variables d'environnement dans Launch Configuration d'Eclipse : <ol>
     * <li>ERABLE_CONFIG_TECH_DIR
     * <li>ERABLE_CONFMETIER_DIR
     * <li>ERABLE_SERVICE
     * </ol>
     * </ul>
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);
        HandlerCollection handlers = new HandlerCollection();
        WebAppContext webApp = new WebAppContext();
        webApp.setResourceBase("server/src/main/webapp");
        webApp.setContextPath("/*");
        webApp.setDefaultsDescriptor("server/src/main/webapp/WEB-INF/web.xml");
        handlers.addHandler(webApp);
        server.setHandler(handlers);
        server.start();
    }
}
