package com.boschat.sikb.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * The Class ErableServlet.
 */
@Path("/")
@Produces({"application/json"})
public class ReloadServlet {

    private static final Logger LOGGER = LogManager.getLogger(ReloadServlet.class);

   /* public static void reloadProperties() {
        ConfigLoader.getInstance().loadAndCheckTechnicalConfig(FamilyTechnicalProperties.class, ERABLE_CONFIG_TECH_DIR.getValue(), RS.getId());
        ConfigLoader.getInstance().loadFunctionalConfig(ERABLE_CONFIG_FUNC_DIR.getValue(), RS.getId(), FunctionalConfig.class);
    }

    static void reloadFamilyApp() {
        reloadProperties();
        // Reload interfaces with new properties
        CliperService.getInstance().reinitPartyPort();
        PortfolioService.getInstance().reinitApi();
        CacheAppService.getInstance().reinitApi();
        FamilyCircuitBreaker.reset();
    }*/

    /**
     * Reload configuration.
     *
     * @return the response
     */
    @GET
    @Path("reload")
    public Response reloadConfiguration() {
        LOGGER.trace("reload");
        return Response.status(HttpServletResponse.SC_OK).build();
      /* try {
            reloadFamilyApp();
            return Response.status(HttpServletResponse.SC_OK).build();
        } catch (Throwable e) {
            LOGGER.error(new LogMetadata(RELOAD_ERROR, e));
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }*/
    }
}
