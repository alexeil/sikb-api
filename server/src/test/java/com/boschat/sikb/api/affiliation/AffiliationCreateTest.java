package com.boschat.sikb.api.affiliation;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Affiliation;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.INVALID_QUERY_STRING_PARAMETER;
import static com.boschat.sikb.common.configuration.ResponseCode.MISSING_BODY_FIELD;
import static com.boschat.sikb.common.configuration.ResponseCode.SEASON_NOT_FOUND;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_PREFECTURE_NUMBER;
import static com.boschat.sikb.common.configuration.SikbConstants.QUERY_STRING_SEASON_ID;

@DisplayName(" Create an affiliation ")
@ExtendWith(JerseyTestExtension.class)
class AffiliationCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        loadSeasons();
        loadClubs();
    }

    private AffiliationForCreation buildCommon() {
        AffiliationForCreation affiliationForCreation = new AffiliationForCreation();

        affiliationForCreation.setPrefectureNumber(AFFILIATION_DEFAULT_PREFECTURE_NUMBER);
        affiliationForCreation.setPrefectureCity(AFFILIATION_DEFAULT_PREFECTURE_CITY);
        affiliationForCreation.setSiretNumber(AFFILIATION_DEFAULT_SIRET_NUMBER);
        affiliationForCreation.setAddress(AFFILIATION_DEFAULT_ADDRESS);
        affiliationForCreation.setPostalCode(AFFILIATION_DEFAULT_POSTAL_CODE);
        affiliationForCreation.setCity(AFFILIATION_DEFAULT_CITY);
        affiliationForCreation.setPhoneNumber(AFFILIATION_DEFAULT_PHONE_NUMBER);
        affiliationForCreation.setEmail(AFFILIATION_DEFAULT_EMAIL);
        affiliationForCreation.setWebSite(AFFILIATION_DEFAULT_WEBSITE);

        Board board = new Board();
        board.setPresident(AFFILIATION_DEFAULT_PRESIDENT);
        board.setPresidentSex(AFFILIATION_DEFAULT_PRESIDENT_SEX);
        board.setSecretary(AFFILIATION_DEFAULT_SECRETARY);
        board.setSecretarySex(AFFILIATION_DEFAULT_SECRETARY_SEX);
        board.setTreasurer(AFFILIATION_DEFAULT_TREASURER);
        board.setTreasurerSex(AFFILIATION_DEFAULT_TREASURER_SEX);
        board.setMembersNumber(AFFILIATION_DEFAULT_MEMBERS_NUMBER);
        board.setElectedDate(AFFILIATION_DEFAULT_ELECTED_DATE);
        affiliationForCreation.setBoard(board);

        return affiliationForCreation;
    }

    @Test
    @DisplayName(" with everything ")
    void withAName() throws Exception {
        AffiliationForCreation affiliationForCreation = buildCommon();
        Response response = affiliationCreate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, affiliationForCreation);

        checkResponse(response, CREATED);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
            AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
            AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, NOW, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
            AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
            AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE);
    }

    @Test
    @DisplayName(" unknown season ")
    void unknownSeason() throws Exception {
        AffiliationForCreation affiliationForCreation = buildCommon();
        Response response = affiliationCreate(V1, CLUB_DEFAULT_ID, "10001000", affiliationForCreation);
        checkResponse(response, SEASON_NOT_FOUND, "10001000");
    }

    @Test
    @DisplayName(" incorrect season ")
    void incorrectSeason() throws Exception {
        AffiliationForCreation affiliationForCreation = buildCommon();
        Response response = affiliationCreate(V1, CLUB_DEFAULT_ID, "IncorrectValue", affiliationForCreation);
        checkResponse(response, INVALID_QUERY_STRING_PARAMETER, QUERY_STRING_SEASON_ID, "IncorrectValue");
    }

    @Test
    @DisplayName(" unknown club ")
    void unknownClub() throws Exception {
        AffiliationForCreation affiliationForCreation = buildCommon();
        Response response = affiliationCreate(V1, 999, SEASON_DEFAULT_ID, affiliationForCreation);
        checkResponse(response, CLUB_NOT_FOUND, 999);
    }

    @Test
    @DisplayName(" missing required body field prefectureNumber")
    void missingPrefectureNumber() throws Exception {
        AffiliationForCreation affiliationForCreation = new AffiliationForCreation();
        Response response = affiliationCreate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, affiliationForCreation);
        checkResponse(response, MISSING_BODY_FIELD, BODY_FIELD_PREFECTURE_NUMBER);
    }
}
