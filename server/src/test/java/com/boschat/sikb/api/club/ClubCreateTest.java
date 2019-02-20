package com.boschat.sikb.api.club;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Club;
import com.boschat.sikb.model.ClubForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.MISSING_BODY_FIELD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_NAME;

@DisplayName(" Create a club ")
@ExtendWith(JerseyTestExtension.class)
class ClubCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadClubs();
    }

    @Test
    @DisplayName(" with only a name and shortName")
    void withAName() throws Exception {
        ClubForCreation clubForCreation = new ClubForCreation();
        clubForCreation.setName(CLUB_DEFAULT_NAME);
        clubForCreation.setShortName(CLUB_DEFAULT_SHORT_NAME);
        Response response = clubCreate(V1, clubForCreation);

        checkResponse(response, CREATED);
        Club club = getClub(response);
        checkClub(club, CLUB_DEFAULT_NAME, CLUB_DEFAULT_SHORT_NAME, false);
    }

    @Test
    @DisplayName(" with all information ")
    void withAllInformation() throws Exception {
        ClubForCreation clubForCreation = new ClubForCreation();
        clubForCreation.setName(CLUB_DEFAULT_NAME);
        clubForCreation.setShortName(CLUB_DEFAULT_SHORT_NAME);

        Response response = clubCreate(V1, clubForCreation);

        checkResponse(response, CREATED);
        Club club = getClub(response);
        checkClub(club, CLUB_DEFAULT_NAME, CLUB_DEFAULT_SHORT_NAME, false);
    }

    @Test
    @DisplayName(" missing field")
    void missingField() throws Exception {
        ClubForCreation clubForCreation = new ClubForCreation();
        Response response = clubCreate(V1, clubForCreation);
        checkResponse(response, MISSING_BODY_FIELD, BODY_FIELD_NAME);
    }

}
