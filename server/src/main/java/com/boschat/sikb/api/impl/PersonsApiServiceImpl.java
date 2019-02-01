package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.PersonsApiService;
import com.boschat.sikb.model.Licence;
import com.boschat.sikb.model.PersonForCreation;
import com.boschat.sikb.model.PersonForUpdate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.PERSON_CREATE;
import static com.boschat.sikb.api.CallType.PERSON_UPDATE;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class PersonsApiServiceImpl extends PersonsApiService {

    @Override
    public Response createPerson(String accessToken, PersonForCreation personForCreation, SecurityContext securityContext) {
        return runService(PERSON_CREATE, accessToken, securityContext, personForCreation);
    }

    @Override
    public Response createPersonFormation(String accessToken, Integer personId, Integer clubId, String season, Licence licence,
        SecurityContext securityContext) {
        return null;
    }

    @Override
    public Response deletePerson(String accessToken, Integer personId, SecurityContext securityContext) {
        return null;
    }

    @Override
    public Response findPersons(String accessToken, SecurityContext securityContext) {
        return null;
    }

    @Override
    public Response getPerson(String accessToken, Integer personId, SecurityContext securityContext) {
        return null;
    }

    @Override
    public Response updatePerson(String accessToken, Integer personId, PersonForUpdate personForUpdate, SecurityContext securityContext) {
        return runService(PERSON_UPDATE, accessToken, securityContext, personId, personForUpdate);

    }
}
