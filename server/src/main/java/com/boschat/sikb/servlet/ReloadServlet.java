package com.boschat.sikb.servlet;

import com.boschat.sikb.common.configuration.ConfigLoader;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.utils.MailUtils;
import com.boschat.sikb.utils.PDFGeneratorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Validation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;

import static com.boschat.sikb.common.configuration.EnvVar.CONFIG_PATH;

@Path("/")
@Produces({ "application/json" })
public class ReloadServlet {

    private static final Logger LOGGER = LogManager.getLogger(ReloadServlet.class);

    private static void reloadProperties() {
        ConfigLoader.getInstance().loadAndCheckTechnicalConfig(CONFIG_PATH.getValue(), "application.properties");
    }

    public static void reloadEverything() {
        MailUtils.reset();
        DAOFactory.reset();
        PDFGeneratorUtils.reset();
        reloadProperties();

        File properties = new File(CONFIG_PATH.getValue(), "ValidationMessages.properties");
        Validation.byDefaultProvider()
                  .configure()
                  .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator(properties.getAbsolutePath())))
                  .buildValidatorFactory();
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
            reloadEverything();
            return Response.status(HttpServletResponse.SC_OK).build();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
    }
}
