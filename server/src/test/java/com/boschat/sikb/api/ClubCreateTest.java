package com.boschat.sikb.api;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Club;
import com.boschat.sikb.model.ClubForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.api.ResponseCode.CREATED;

@DisplayName(" Create a club ")
class ClubCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() {
        truncateData();
    }

    @Test
    @DisplayName(" with only a name ")
    void withAName() throws Exception {
        ClubForCreation clubForCreation = new ClubForCreation();
        clubForCreation.setName(DEFAULT_CLUB_NAME);
        Response response = clubCreate(V1, clubForCreation);

        checkResponse(response, CREATED);
        Club club = getClub(response);
        checkClub(club, DEFAULT_CLUB_NAME, null, null);
    }

    @Test
    @DisplayName(" with all information ")
    void withAllInformation() throws Exception {
        ClubForCreation clubForCreation = new ClubForCreation();
        clubForCreation.setName(DEFAULT_CLUB_NAME);
        clubForCreation.setShortName(DEFAULT_CLUB_SHORT_NAME);
        clubForCreation.setLogo(DEFAULT_CLUB_LOGO);

        Response response = clubCreate(V1, clubForCreation);

        checkResponse(response, CREATED);
        Club club = getClub(response);
        checkClub(club, DEFAULT_CLUB_NAME, DEFAULT_CLUB_SHORT_NAME, DEFAULT_CLUB_LOGO);
    }

}
