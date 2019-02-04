package com.boschat.sikb.servlet;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.model.ApplicationBean;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Application;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import static com.boschat.sikb.common.configuration.ResponseCode.UNAUTHORIZED;
import static com.boschat.sikb.utils.HashUtils.basicDecode;

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
        //Get the authentication passed in HTTP headers parameters
        String auth = containerRequest.getHeaderString("authorization");

        //If the UserBean does not have the right (does not provide any HTTP Basic Auth)
        if (auth == null) {
            throw new FunctionalException(UNAUTHORIZED);
        }

        auth = auth.substring("Basic".length()).trim();
        Credentials credentials = basicDecode(auth);
        //If login or password fail
        if (credentials == null) {
            throw new FunctionalException(UNAUTHORIZED);
        }

        Application application = DAOFactory.getInstance().getApplicationDAO().fetchByLoginPassword(credentials.getLogin(), credentials.getPassword());
        if (application == null) {
            throw new FunctionalException(UNAUTHORIZED);
        }

        // We configure your Security Context here
        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
        containerRequest.setSecurityContext(new ApplicationSecurityContext(new ApplicationBean(application), scheme));
    }
}