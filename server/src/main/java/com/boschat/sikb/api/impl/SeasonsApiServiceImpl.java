package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.SeasonsApiService;
import com.boschat.sikb.model.SeasonForCreation;
import com.boschat.sikb.model.SeasonForUpdate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.SEASON_CREATE;
import static com.boschat.sikb.api.CallType.SEASON_DELETE;
import static com.boschat.sikb.api.CallType.SEASON_FIND;
import static com.boschat.sikb.api.CallType.SEASON_UPDATE;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-02-18T09:58:22.375+01:00[Europe/Paris]")
public class SeasonsApiServiceImpl extends SeasonsApiService {

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

}
