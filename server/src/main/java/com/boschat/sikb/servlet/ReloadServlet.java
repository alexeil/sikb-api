package com.boschat.sikb.servlet;

import com.boschat.sikb.common.configuration.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static com.boschat.sikb.common.configuration.EnvVar.CONFIG_PATH;

@Path("/")
@Produces({ "application/json" })
public class ReloadServlet {

    private static final Logger LOGGER = LogManager.getLogger(ReloadServlet.class);

    public static void reloadProperties() {
        ConfigLoader.getInstance().loadAndCheckTechnicalConfig(CONFIG_PATH.getValue(), "application.properties");
    }

    /**
     * Reload configuration.
     *
     * @return the response
     */
    @GET
    @Path("reload")
    public Response reloadConfiguration() {
        LOGGER.trace("reload");
        try {
            reloadProperties();
            return Response.status(HttpServletResponse.SC_OK).build();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
    }
}
