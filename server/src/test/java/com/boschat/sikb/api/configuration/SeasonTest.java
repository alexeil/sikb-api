package com.boschat.sikb.api.configuration;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Season;
import com.boschat.sikb.model.SeasonForCreation;
import com.boschat.sikb.model.SeasonForUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.PersistenceUtils.truncateData;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.SEASON_ALREADY_EXISTS;
import static com.boschat.sikb.common.configuration.ResponseCode.SEASON_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Test season API : ")
class SeasonTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        truncateData();
        loadSeasons();
    }

    @Nested
    @DisplayName(" Create new season ")
    class create {

        @Test
        @DisplayName(" right case ")
        void newSeason() throws Exception {
            String id = "20192020";
            String description = "Season 2019/2020";
            LocalDate begin = LocalDate.of(2019, 9, 1);
            LocalDate end = LocalDate.of(2020, 8, 31);

            SeasonForCreation seasonForCreation = new SeasonForCreation();
            seasonForCreation.setDescription(description);
            seasonForCreation.setBegin(begin);
            seasonForCreation.setEnd(end);

            Response response = seasonCreate(V1, seasonForCreation);

            checkResponse(response, CREATED);
            Season season = getSeason(response);
            checkSeason(season, id, description, begin, end);
        }

        @Test
        @DisplayName(" already existing ")
        void newSeasonAlreadyExists() throws Exception {
            SeasonForCreation seasonForCreation = new SeasonForCreation();
            seasonForCreation.setDescription(SEASON_DEFAULT_DESCRIPTION);
            seasonForCreation.setBegin(SEASON_DEFAULT_BEGIN);
            seasonForCreation.setEnd(SEASON_DEFAULT_END);
            Response response = seasonCreate(V1, seasonForCreation);

            checkResponse(response, SEASON_ALREADY_EXISTS, SEASON_DEFAULT_ID);
        }
    }

    @Nested
    @DisplayName(" Update a season ")
    class update {

        @Test
        @DisplayName(" description ")
        void updateDescription() throws Exception {
            SeasonForUpdate seasonForUpdate = new SeasonForUpdate();
            seasonForUpdate.setDescription("New description");
            Response response = seasonUpdate(V1, SEASON_DEFAULT_ID, seasonForUpdate);

            checkResponse(response, OK);
            Season season = getSeason(response);
            checkSeason(season, SEASON_DEFAULT_ID, "New description", SEASON_DEFAULT_BEGIN, SEASON_DEFAULT_END);
        }

        @Test
        @DisplayName(" begin ")
        void updateBeginDate() throws Exception {
            SeasonForUpdate seasonForUpdate = new SeasonForUpdate();
            seasonForUpdate.setBegin(LocalDate.of(2018, 8, 1));
            Response response = seasonUpdate(V1, SEASON_DEFAULT_ID, seasonForUpdate);

            checkResponse(response, OK);
            Season season = getSeason(response);
            checkSeason(season, SEASON_DEFAULT_ID, SEASON_DEFAULT_DESCRIPTION, LocalDate.of(2018, 8, 1), SEASON_DEFAULT_END);
        }

        @Test
        @DisplayName(" unknown ")
        void updateUnknown() throws Exception {
            SeasonForUpdate seasonForUpdate = new SeasonForUpdate();
            seasonForUpdate.setBegin(LocalDate.of(2018, 8, 1));
            Response response = seasonUpdate(V1, "19901991", seasonForUpdate);
            checkResponse(response, SEASON_NOT_FOUND, "19901991");
        }
    }

    @Nested
    @DisplayName(" Delete a season ")
    class delete {

        @Test
        @DisplayName(" right case ")
        void updateDescription() throws Exception {
            Response response = seasonDelete(V1, SEASON_DEFAULT_ID);
            checkResponse(response, NO_CONTENT);
        }

        @Test
        @DisplayName(" unknown ")
        void updateUnknown() throws Exception {
            Response response = seasonDelete(V1, "19901991");
            checkResponse(response, SEASON_NOT_FOUND, "19901991");
        }
    }

    @Nested
    @DisplayName(" Find all seasons ")
    class find {

        @Test
        @DisplayName(" right case ")
        void findAll() throws Exception {
            Response response = seasonFind(V1);
            checkResponse(response, OK);
            List<Season> seasons = getSeasons(response);

            assertAll("Check seasons ",
                () -> assertNotNull(seasons, " clubs shouldn't be null"),
                () -> assertEquals(2, seasons.size(), " only 2 clubs "),
                () -> checkSeason(seasons.get(0), SEASON_DEFAULT_ID, SEASON_DEFAULT_DESCRIPTION, SEASON_DEFAULT_BEGIN, SEASON_DEFAULT_END),
                () -> checkSeason(seasons.get(1), "20172018", "Saison 2017/2018", LocalDate.of(2017, 9, 1), LocalDate.of(2018, 8, 31)));
        }

    }
}
