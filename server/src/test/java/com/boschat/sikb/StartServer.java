package com.boschat.sikb;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

class StartServer {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8081);
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
