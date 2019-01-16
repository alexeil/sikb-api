package com.boschat.sikb.api;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Affiliation;
import com.boschat.sikb.model.AffiliationForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.time.OffsetDateTime;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.api.ResponseCode.CREATED;

@DisplayName(" Create an affiliation ")
class AffiliationCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        truncateData();
    }

    @Test
    @DisplayName(" with a name ")
    void withAName() throws Exception {
        AffiliationForCreation affiliationForCreation = new AffiliationForCreation();
        affiliationForCreation.setAssociationName("KBAR");
        affiliationForCreation.setCreationDate(OffsetDateTime.now());
        Response response = affiliationCreate(V1, affiliationForCreation);

        checkResponse(response, CREATED);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, "KBAR");
    }

}
