package com.boschat.sikb.api.affiliation;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.PersistenceUtils;
import com.boschat.sikb.model.Affiliation;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;

@DisplayName(" Create an affiliation ")
class AffiliationCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        PersistenceUtils.truncateData();
        PersistenceUtils.loadClubs();
    }

    @Test
    @DisplayName(" with everything ")
    void withAName() throws Exception {
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

        Response response = affiliationCreate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_SHORT_NAME, affiliationForCreation);

        checkResponse(response, CREATED);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
            AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
            AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, NOW, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
            AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
            AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE);
    }

}
