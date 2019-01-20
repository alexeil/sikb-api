package com.boschat.sikb.servlet;

import com.boschat.sikb.model.UserBean;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 * Jersey HTTP Basic Auth filter
 *
 * @author Deisss (LGPLv3)
 */
@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {

    /**
     * Apply the filter : check input request, validate or not with UserBean auth
     *
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        //GET, POST, PUT, DELETE, ...
        String method = containerRequest.getMethod();
        // myresource/get/56bCA for example
        String path = containerRequest.getUriInfo().getPath(true);

        //We do allow wadl to be retrieve
        if (method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))) {
            return;
        }

        //Get the authentification passed in HTTP headers parameters
        String auth = containerRequest.getHeaderString("authorization");

        //If the UserBean does not have the right (does not provide any HTTP Basic Auth)
        if (auth == null) {
            //            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        //lap : loginAndPassword
        String[] lap = { "basic", "test" };// BasicAuth.decode(auth);

        //If login or password fail
        if (lap == null || lap.length != 2) {
            //          throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        //DO YOUR DATABASE CHECK HERE (replace that line behind)...
        UserBean authentificationResult = new UserBean();// =  AuthentificationThirdParty.authentification(lap[0], lap[1]);
        authentificationResult.setEmail("Test");
        //Our system refuse login and password
        if (authentificationResult == null) {
            //        throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        // We configure your Security Context here
        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
        containerRequest.setSecurityContext(new ApplicationSecurityContext(authentificationResult, scheme));

        //TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER UserBean ON YOUR REST SERVICE...
    }
}