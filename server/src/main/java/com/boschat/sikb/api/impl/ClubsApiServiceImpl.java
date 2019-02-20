package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.ClubsApiService;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.AffiliationForUpdate;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;
import com.boschat.sikb.model.TeamForCreation;
import com.boschat.sikb.model.TeamForUpdate;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.InputStream;

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
import static com.boschat.sikb.api.CallType.LOGO_UPLOAD;
import static com.boschat.sikb.api.CallType.TEAM_CREATE;
import static com.boschat.sikb.api.CallType.TEAM_DELETE;
import static com.boschat.sikb.api.CallType.TEAM_FIND;
import static com.boschat.sikb.api.CallType.TEAM_GET;
import static com.boschat.sikb.api.CallType.TEAM_MEMBERS_FIND;
import static com.boschat.sikb.api.CallType.TEAM_UPDATE;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class ClubsApiServiceImpl extends ClubsApiService {

    @Override
    public Response createClub(String accessToken, ClubForCreation clubForCreation, SecurityContext securityContext) {
        return runService(CLUB_CREATE, accessToken, securityContext, clubForCreation);
    }

    @Override
    public Response createTeam(String accessToken, Integer clubId, String seasonId, TeamForCreation teamForCreation, SecurityContext securityContext) {
        return runService(TEAM_CREATE, accessToken, securityContext, clubId, seasonId, teamForCreation);
    }

    @Override
    public Response deleteClub(String accessToken, Integer clubId, SecurityContext securityContext) {
        return runService(CLUB_DELETE, accessToken, securityContext, clubId);
    }

    @Override
    public Response deleteTeam(String accessToken, Integer clubId, String seasonId, Integer teamId, SecurityContext securityContext) {
        return runService(TEAM_DELETE, accessToken, securityContext, clubId, seasonId, teamId);

    }

    @Override
    public Response findClubs(String accessToken, SecurityContext securityContext) {
        return runService(CLUB_FIND, accessToken, securityContext);
    }

    @Override
    public Response findTeams(String accessToken, Integer clubId, String seasonId, SecurityContext securityContext) {
        return runService(TEAM_FIND, accessToken, securityContext, clubId, seasonId);

    }

    @Override
    public Response getClubById(String accessToken, Integer clubId, SecurityContext securityContext) {
        return runService(CLUB_GET, accessToken, securityContext, clubId);
    }

    @Override
    public Response getTeam(String accessToken, Integer clubId, String seasonId, Integer teamId, SecurityContext securityContext) {
        return runService(TEAM_GET, accessToken, securityContext, clubId, seasonId, teamId);

    }

    @Override
    public Response getTeamMembers(String accessToken, Integer clubId, String seasonId, Integer teamId, SecurityContext securityContext) {
        return runService(TEAM_MEMBERS_FIND, accessToken, securityContext, clubId, seasonId, teamId);
    }

    @Override
    public Response updateClub(String accessToken, Integer clubId, ClubForUpdate clubForUpdate, SecurityContext securityContext) {
        return runService(CLUB_UPDATE, accessToken, securityContext, clubId, clubForUpdate);

    }

    @Override
    public Response updateTeam(String accessToken, Integer clubId, String seasonId, Integer teamId, TeamForUpdate teamForUpdate,
        SecurityContext securityContext) {
        return runService(TEAM_UPDATE, accessToken, securityContext, clubId, seasonId, teamId, teamForUpdate);

    }

    @Override
    public Response uploadLogo(String accessToken, Integer clubId, InputStream logoFileNameInputStream, FormDataContentDisposition logoFileNameDetail,
        SecurityContext securityContext) {
        return runService(LOGO_UPLOAD, accessToken, securityContext, clubId, logoFileNameInputStream, logoFileNameDetail);
    }

    @Override
    public Response getAffiliation(String accessToken, Integer clubId, String season, SecurityContext securityContext) {
        return runService(AFFILIATION_GET, accessToken, securityContext, clubId, season);
    }

    @Override
    public Response createAffiliation(String accessToken, Integer clubId, String season, AffiliationForCreation affiliationForCreation,
        SecurityContext securityContext) {
        return runService(AFFILIATION_CREATE, accessToken, securityContext, clubId, season, affiliationForCreation);
    }

    @Override
    public Response updateAffiliation(String accessToken, Integer clubId, String season, AffiliationForUpdate affiliationForUpdate,
        SecurityContext securityContext) {
        return runService(AFFILIATION_UPDATE, accessToken, securityContext, clubId, season, affiliationForUpdate);
    }

    @Override
    public Response deleteAffiliation(String accessToken, Integer clubId, String season, SecurityContext securityContext) {
        return runService(AFFILIATION_DELETE, accessToken, securityContext, clubId, season);
    }
}
