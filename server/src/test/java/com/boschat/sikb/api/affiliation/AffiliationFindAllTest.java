package com.boschat.sikb.api.affiliation;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.SeasonWithAffiliation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

import static com.boschat.sikb.PersistenceUtils.loadAffiliations;
import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.model.AffiliationStatus.SUBMITTED;
import static com.boschat.sikb.model.AffiliationStatus.VALIDATED;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Find all club's affiliations ")
@ExtendWith(JerseyTestExtension.class)
class AffiliationFindAllTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        loadSeasons();
        loadClubs();
        loadAffiliations();
    }

    @Test
    @DisplayName(" with no affiliation ")
    void withNoAffiliation() throws Exception {
        Response response = affiliationFindAll(V1, 2);

        checkResponse(response, OK);

        List<SeasonWithAffiliation> seasonWithAffiliations = getSeasonWithAffiliations(response);

        assertAll("Check season with affiliations ",
            () -> assertNotNull(seasonWithAffiliations, "Affiliations must not be null"),
            () -> assertEquals(0, seasonWithAffiliations.size(), "Affiliations size is incorrect")
        );
    }

    @Test
    @DisplayName(" (default) ")
    void defaultAffiliation() throws Exception {
        Response response = affiliationFindAll(V1, CLUB_DEFAULT_ID);

        checkResponse(response, OK);

        List<SeasonWithAffiliation> seasonWithAffiliations = getSeasonWithAffiliations(response);

        assertAll("Check season with affiliations ",
            () -> assertNotNull(seasonWithAffiliations, "Affiliations must not be null"),
            () -> assertEquals(3, seasonWithAffiliations.size(), "Affiliations size is incorrect"),

            () -> checkSeason(seasonWithAffiliations.get(0).getSeason(), SEASON_DEFAULT_ID, SEASON_DEFAULT_DESCRIPTION, SEASON_DEFAULT_BEGIN,
                SEASON_DEFAULT_END),
            () -> checkAffiliation(seasonWithAffiliations.get(0).getAffiliation(), AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY,
                AFFILIATION_DEFAULT_SIRET_NUMBER,
                AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
                AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, null, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
                AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
                AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE, AFFILIATION_DEFAULT_STATUS, AFFILIATION_DEFAULT_COMMENT),

            () -> checkSeason(seasonWithAffiliations.get(1).getSeason(), "20172018", "Saison 2017/2018", LocalDate.of(2017, 9, 1), LocalDate.of(2018, 8, 31)),
            () -> checkAffiliation(seasonWithAffiliations.get(1).getAffiliation(), AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY,
                AFFILIATION_DEFAULT_SIRET_NUMBER,
                AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
                AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, null, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
                AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
                AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE, VALIDATED, null),

            () -> checkSeason(seasonWithAffiliations.get(2).getSeason(), "20162017", "Saison 2016/2017", LocalDate.of(2016, 9, 1), LocalDate.of(2017, 8, 31)),
            () -> checkAffiliation(seasonWithAffiliations.get(2).getAffiliation(), AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY,
                AFFILIATION_DEFAULT_SIRET_NUMBER,
                AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
                AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, null, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
                AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
                AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE, SUBMITTED, null)

        );
    }
}
