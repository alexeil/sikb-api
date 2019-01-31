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

import static com.boschat.sikb.ApiVersion.V1;
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

        affiliationForCreation.setPrefectureNumber(DEFAULT_AFFILIATION_PREFECTURE_NUMBER);
        affiliationForCreation.setPrefectureCity(DEFAULT_AFFILIATION_PREFECTURE_CITY);
        affiliationForCreation.setSiretNumber(DEFAULT_AFFILIATION_SIRET_NUMBER);
        affiliationForCreation.setAddress(DEFAULT_AFFILIATION_ADDRESS);
        affiliationForCreation.setPostalCode(DEFAULT_AFFILIATION_POSTAL_CODE);
        affiliationForCreation.setCity(DEFAULT_AFFILIATION_CITY);
        affiliationForCreation.setPhoneNumber(DEFAULT_AFFILIATION_PHONE_NUMBER);
        affiliationForCreation.setEmail(DEFAULT_AFFILIATION_EMAIL);
        affiliationForCreation.setWebSite(DEFAULT_AFFILIATION_WEBSITE);

        Board board = new Board();
        board.setPresident(DEFAULT_AFFILIATION_PRESIDENT);
        board.setPresidentSex(DEFAULT_AFFILIATION_PRESIDENT_SEX);
        board.setSecretary(DEFAULT_AFFILIATION_SECRETARY);
        board.setSecretarySex(DEFAULT_AFFILIATION_SECRETARY_SEX);
        board.setTreasurer(DEFAULT_AFFILIATION_TREASURER);
        board.setTreasurerSex(DEFAULT_AFFILIATION_TREASURER_SEX);
        board.setMembersNumber(DEFAULT_AFFILIATION_MEMBERS_NUMBER);
        board.setElectedDate(DEFAULT_AFFILIATION_ELECTED_DATE);
        affiliationForCreation.setBoard(board);

        Response response = affiliationCreate(V1, DEFAULT_CLUB_ID, DEFAULT_SEASON, affiliationForCreation);

        checkResponse(response, CREATED);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, DEFAULT_AFFILIATION_PREFECTURE_NUMBER, DEFAULT_AFFILIATION_PREFECTURE_CITY, DEFAULT_AFFILIATION_SIRET_NUMBER,
            DEFAULT_AFFILIATION_ADDRESS, DEFAULT_AFFILIATION_POSTAL_CODE, DEFAULT_AFFILIATION_CITY, DEFAULT_AFFILIATION_PHONE_NUMBER,
            DEFAULT_AFFILIATION_EMAIL, DEFAULT_AFFILIATION_WEBSITE, NOW, null, DEFAULT_AFFILIATION_PRESIDENT, DEFAULT_AFFILIATION_PRESIDENT_SEX,
            DEFAULT_AFFILIATION_SECRETARY, DEFAULT_AFFILIATION_SECRETARY_SEX, DEFAULT_AFFILIATION_TREASURER, DEFAULT_AFFILIATION_TREASURER_SEX,
            DEFAULT_AFFILIATION_MEMBERS_NUMBER, DEFAULT_AFFILIATION_ELECTED_DATE);
    }

}
