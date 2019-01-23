package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.ClubsApiService;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.AffiliationForUpdate;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.AFFILIATION_CREATE;
import static com.boschat.sikb.api.CallType.AFFILIATION_DELETE;
import static com.boschat.sikb.api.CallType.AFFILIATION_GET;
import static com.boschat.sikb.api.CallType.AFFILIATION_UPDATE;
import static com.boschat.sikb.api.CallType.CLUB_CREATE;
import static com.boschat.sikb.api.CallType.CLUB_DELETE;
import static com.boschat.sikb.api.CallType.CLUB_FIND;
import static com.boschat.sikb.api.CallType.CLUB_GET;
import static com.boschat.sikb.api.CallType.CLUB_UPDATE;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class ClubsApiServiceImpl extends ClubsApiService {

    @Override
    public Response createClub(ClubForCreation clubForCreation, SecurityContext securityContext) {
        return runService(CLUB_CREATE, null, clubForCreation);
    }

    @Override
    public Response deleteClub(Integer clubId, SecurityContext securityContext) {
        return runService(CLUB_DELETE, null, clubId);
    }

    @Override
    public Response findClubs(SecurityContext securityContext) {
        return runService(CLUB_FIND, null);
    }

    @Override
    public Response getClubById(String accessToken, Integer clubId, SecurityContext securityContext) {
        return runService(CLUB_GET, accessToken, clubId);
    }

    @Override
    public Response updateClub(String accessToken, Integer clubId, ClubForUpdate clubForUpdate, SecurityContext securityContext) {
        return runService(CLUB_UPDATE, accessToken, clubId, clubForUpdate);

    }

    @Override
    public Response getAffiliation(String accessToken, Integer clubId, String season, SecurityContext securityContext) {
        return runService(AFFILIATION_GET, accessToken, clubId, season);
    }

    @Override
    public Response createAffiliation(String accessToken, Integer clubId, String season, AffiliationForCreation affiliationForCreation,
        SecurityContext securityContext) {
        return runService(AFFILIATION_CREATE, accessToken, clubId, season, affiliationForCreation);
    }

    @Override
    public Response updateAffiliation(String accessToken, Integer clubId, String season, AffiliationForUpdate affiliationForUpdate,
        SecurityContext securityContext) {
        return runService(AFFILIATION_UPDATE, accessToken, clubId, season, affiliationForUpdate);
    }

    @Override
    public Response deleteAffiliation(String accessToken, Integer clubId, String season, SecurityContext securityContext) {
        return runService(AFFILIATION_DELETE, accessToken, clubId, season);
    }
}
