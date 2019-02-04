package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.ConfigurationsApiService;
import com.boschat.sikb.model.SeasonForCreation;
import com.boschat.sikb.model.SeasonForUpdate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.FORMATION_TYPE_FIND;
import static com.boschat.sikb.api.CallType.LICENCE_TYPE_FIND;
import static com.boschat.sikb.api.CallType.SEASON_CREATE;
import static com.boschat.sikb.api.CallType.SEASON_DELETE;
import static com.boschat.sikb.api.CallType.SEASON_FIND;
import static com.boschat.sikb.api.CallType.SEASON_UPDATE;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class ConfigurationsApiServiceImpl extends ConfigurationsApiService {

    @Override
    public Response createSeason(String accessToken, SeasonForCreation seasonForCreation, SecurityContext securityContext) {
        return runService(SEASON_CREATE, accessToken, securityContext, seasonForCreation);
    }

    @Override
    public Response deleteSeason(String accessToken, String seasonId, SecurityContext securityContext) {
        return runService(SEASON_DELETE, accessToken, securityContext, seasonId);
    }

    @Override
    public Response findSeasons(String accessToken, SecurityContext securityContext) {
        return runService(SEASON_FIND, accessToken, securityContext);
    }

    @Override
    public Response updateSeason(String accessToken, String seasonId, SeasonForUpdate seasonForUpdate, SecurityContext securityContext) {
        return runService(SEASON_UPDATE, accessToken, securityContext, seasonId, seasonForUpdate);
    }

    @Override
    public Response findFormationTypes(String accessToken, SecurityContext securityContext) {
        return runService(FORMATION_TYPE_FIND, accessToken, securityContext);
    }

    @Override
    public Response findLicenceTypes(String accessToken, SecurityContext securityContext) {
        return runService(LICENCE_TYPE_FIND, accessToken, securityContext);
    }
}
