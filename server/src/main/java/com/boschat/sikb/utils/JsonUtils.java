package com.boschat.sikb.utils;

import com.boschat.sikb.common.exceptions.TechnicalException;
import com.boschat.sikb.model.Formation;
import com.boschat.sikb.model.FormationType;
import com.boschat.sikb.model.LicenceType;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.servlet.JacksonJsonProvider;
import com.boschat.sikb.tables.pojos.Formationtype;
import com.boschat.sikb.tables.pojos.Licencetype;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.boschat.sikb.Helper.convertFormationTypesBeansToModels;
import static com.boschat.sikb.Helper.convertLicenceTypesBeansToModels;
import static com.boschat.sikb.common.configuration.ResponseCode.JSON_PARSE_ERROR;

public class JsonUtils {

    private JsonUtils() {

    }

    public static List<LicenceType> findLicenceTypes(Integer[] ids) {
        List<Licencetype> licenceTypes = DAOFactory.getInstance().getLicenceTypeDAO().fetchById(ids);
        return convertLicenceTypesBeansToModels(licenceTypes);
    }

    public static List<FormationType> findFormationNeed(Integer[] ids) {
        List<Formationtype> formationTypes = DAOFactory.getInstance().getFormationTypeDAO().fetchById(ids);
        return convertFormationTypesBeansToModels(formationTypes);
    }

    public static JsonNode formationsToJsonNode(List<Formation> formations) {
        return objectToJsonNode(formations);
    }

    public static List<Formation> jsonNodeToFormations(JsonNode formations) {
        return Arrays.asList(jsonNodeToObject(formations, Formation[].class));
    }

    private static <T> JsonNode objectToJsonNode(T object) {
        try {
            return JacksonJsonProvider.getMapper().readTree(JacksonJsonProvider.getMapper().writeValueAsString(object));
        } catch (IOException e) {
            throw new TechnicalException(JSON_PARSE_ERROR, e);
        }
    }

    private static <T> T jsonNodeToObject(JsonNode jsonNode, Class<T> clazz) {
        try {
            return JacksonJsonProvider.getMapper().readValue(jsonNode.toString(), clazz);
        } catch (IOException e) {
            throw new TechnicalException(JSON_PARSE_ERROR, e);
        }
    }
}
