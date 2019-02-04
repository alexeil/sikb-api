package com.boschat.sikb.api;

import com.boschat.sikb.model.*;
import com.boschat.sikb.api.ConfigurationsApiService;
import com.boschat.sikb.api.factories.ConfigurationsApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import com.boschat.sikb.model.FormationType;
import com.boschat.sikb.model.LicenceType;
import com.boschat.sikb.model.Season;
import com.boschat.sikb.model.SeasonForCreation;
import com.boschat.sikb.model.SeasonForUpdate;
import com.boschat.sikb.model.ZError;

import java.util.Map;
import java.util.List;
import com.boschat.sikb.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/v1/configurations")


@io.swagger.annotations.Api(description = "the configurations API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-02-04T15:53:46.704+01:00[Europe/Paris]")
public class ConfigurationsApi  {
   private final ConfigurationsApiService delegate;

   public ConfigurationsApi(@Context ServletConfig servletContext) {
      ConfigurationsApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("ConfigurationsApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (ConfigurationsApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = ConfigurationsApiServiceFactory.getConfigurationsApi();
      }

      this.delegate = delegate;
   }

    @POST
    @Path("/seasons")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create a season", notes = "", response = Season.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "configurations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Newly created season", response = Season.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Default error sample response", response = ZError.class) })
    public Response createSeason(@ApiParam(value = "Once the user is logged, the user receive an access token and have to send it for each further requests" ,required=true)@HeaderParam("access_token") String accessToken
,@ApiParam(value = "body" ,required=true) @Valid SeasonForCreation seasonForCreation
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createSeason(accessToken,seasonForCreation,securityContext);
    }
    @DELETE
    @Path("/seasons/{seasonId}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete a season", notes = "", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "configurations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "The season is deleted", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Default error sample response", response = ZError.class) })
    public Response deleteSeason(@ApiParam(value = "Once the user is logged, the user receive an access token and have to send it for each further requests" ,required=true)@HeaderParam("access_token") String accessToken
,@ApiParam(value = "Identification of the season",required=true) @PathParam("seasonId") String seasonId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.deleteSeason(accessToken,seasonId,securityContext);
    }
    @GET
    @Path("/formationTypes")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find all formation types", notes = "", response = FormationType.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "configurations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "find all formation Types", response = FormationType.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Default error sample response", response = ZError.class) })
    public Response findFormationTypes(@ApiParam(value = "Once the user is logged, the user receive an access token and have to send it for each further requests" ,required=true)@HeaderParam("access_token") String accessToken
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.findFormationTypes(accessToken,securityContext);
    }
    @GET
    @Path("/licenceTypes")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find all licence types", notes = "", response = LicenceType.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "configurations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "find all licence Types", response = LicenceType.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Default error sample response", response = ZError.class) })
    public Response findLicenceTypes(@ApiParam(value = "Once the user is logged, the user receive an access token and have to send it for each further requests" ,required=true)@HeaderParam("access_token") String accessToken
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.findLicenceTypes(accessToken,securityContext);
    }
    @GET
    @Path("/seasons")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find all seasons", notes = "", response = Season.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "configurations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "find all seasons", response = Season.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Default error sample response", response = ZError.class) })
    public Response findSeasons(@ApiParam(value = "Once the user is logged, the user receive an access token and have to send it for each further requests" ,required=true)@HeaderParam("access_token") String accessToken
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.findSeasons(accessToken,securityContext);
    }
    @PUT
    @Path("/seasons/{seasonId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Update a season", notes = "", response = Season.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "configurations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Newly updated season", response = Season.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Default error sample response", response = ZError.class) })
    public Response updateSeason(@ApiParam(value = "Once the user is logged, the user receive an access token and have to send it for each further requests" ,required=true)@HeaderParam("access_token") String accessToken
,@ApiParam(value = "Identification of the season",required=true) @PathParam("seasonId") String seasonId
,@ApiParam(value = "body" ,required=true) @Valid SeasonForUpdate seasonForUpdate
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.updateSeason(accessToken,seasonId,seasonForUpdate,securityContext);
    }
}
