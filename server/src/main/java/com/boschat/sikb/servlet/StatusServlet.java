package com.boschat.sikb.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class StatusServlet {

    private static final Logger LOGGER = LogManager.getLogger(StatusServlet.class);

    /**
     * Reload configuration.
     *
     * @return the response
     */
    @GET
    @Path("status")
    public Response reloadConfiguration() {
        LOGGER.trace("Status");
        return Response.status(HttpServletResponse.SC_OK).build();
    }
}
