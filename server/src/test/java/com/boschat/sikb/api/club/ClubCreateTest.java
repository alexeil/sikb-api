package com.boschat.sikb.api.club;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.PersistenceUtils;
import com.boschat.sikb.model.Club;
import com.boschat.sikb.model.ClubForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;

@DisplayName(" Create a club ")
class ClubCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() {
        PersistenceUtils.truncateData();
    }

    @Test
    @DisplayName(" with only a name ")
    void withAName() throws Exception {
        ClubForCreation clubForCreation = new ClubForCreation();
        clubForCreation.setName(CLUB_DEFAULT_NAME);
        Response response = clubCreate(V1, clubForCreation);

        checkResponse(response, CREATED);
        Club club = getClub(response);
        checkClub(club, CLUB_DEFAULT_NAME, null, null);
    }

    @Test
    @DisplayName(" with all information ")
    void withAllInformation() throws Exception {
        ClubForCreation clubForCreation = new ClubForCreation();
        clubForCreation.setName(CLUB_DEFAULT_NAME);
        clubForCreation.setShortName(CLUB_DEFAULT_SHORT_NAME);
        clubForCreation.setLogo(CLUB_DEFAULT_LOGO);

        Response response = clubCreate(V1, clubForCreation);

        checkResponse(response, CREATED);
        Club club = getClub(response);
        checkClub(club, CLUB_DEFAULT_NAME, CLUB_DEFAULT_SHORT_NAME, CLUB_DEFAULT_LOGO);
    }

}
