package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.ClubsApiService;
import com.boschat.sikb.api.NotFoundException;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.AFFILIATION_CREATE;
import static com.boschat.sikb.api.CallType.AFFILIATION_GET;
import static com.boschat.sikb.api.CallType.CLUB_CREATE;
import static com.boschat.sikb.api.CallType.CLUB_DELETE;
import static com.boschat.sikb.api.CallType.CLUB_FIND;
import static com.boschat.sikb.api.CallType.CLUB_GET;
import static com.boschat.sikb.api.CallType.CLUB_UPDATE;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class ClubsApiServiceImpl extends ClubsApiService {

    @Override
    public Response createClub(ClubForCreation clubForCreation, SecurityContext securityContext) throws NotFoundException {
        return runService(CLUB_CREATE, clubForCreation);
    }

    @Override
    public Response deleteClub(Integer clubId, SecurityContext securityContext) throws NotFoundException {
        return runService(CLUB_DELETE, clubId);
    }

    @Override
    public Response findClubs(SecurityContext securityContext) throws NotFoundException {
        return runService(CLUB_FIND);
    }

    @Override
    public Response getClubById(Integer clubId, SecurityContext securityContext) throws NotFoundException {
        return runService(CLUB_GET, clubId);
    }

    @Override
    public Response updateClub(Integer clubId, ClubForUpdate clubForUpdate, SecurityContext securityContext) throws NotFoundException {
        return runService(CLUB_UPDATE, clubId, clubForUpdate);

    }

    @Override
    public Response getAffiliation(Integer clubId, String season, Integer affiliationId, SecurityContext securityContext) throws NotFoundException {
        return runService(AFFILIATION_GET, clubId, season, affiliationId);
    }

    @Override
    public Response createAffiliation(Integer clubId, String season, AffiliationForCreation affiliationForCreation, SecurityContext securityContext)
            throws NotFoundException {
        return runService(AFFILIATION_CREATE, clubId, season, affiliationForCreation);
    }

    @Override
    public Response updateAffiliation(Integer clubId, String season, Integer affiliationId, SecurityContext securityContext) throws NotFoundException {
        return null;
    }

    @Override
    public Response deleteAffiliation(Integer clubId, String season, Integer affiliationId, SecurityContext securityContext) throws NotFoundException {
        return null;
    }
}
