package com.boschat.sikb.model;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Club;
import com.boschat.sikb.tables.pojos.Licence;
import com.boschat.sikb.tables.pojos.Person;
import com.boschat.sikb.tables.pojos.Season;
import com.boschat.sikb.utils.PDFGeneratorUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.boschat.sikb.common.configuration.ApplicationProperties.MEDICAL_CERTIFICATE_BASE_PATH;
import static com.boschat.sikb.common.configuration.ResponseCode.DOCUMENT_TYPE_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.LICENCE_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.MEDICAL_CERTIFICATE_NOT_FOUND;

public enum DocumentType {

    MEDICAL_CERTIFICATE_TYPE("medicalCertificate", "image/png", "image/jpeg", "application/pdf") {
        @Override
        public void writeDocument(String id, OutputStream outputStream) throws IOException {
            Person person = DAOFactory.getInstance().getPersonDAO().fetchOneByMedicalcertificatekey(id);
            if (person == null) {
                throw new FunctionalException(MEDICAL_CERTIFICATE_NOT_FOUND, id);
            } else {
                byte[] data = person.getMedicalcertificatedata();
                outputStream.write(data);
            }
        }
    },
    LICENCE_TYPE("licence", "application/pdf") {
        @Override
        public void writeDocument(String id, OutputStream outputStream) {
            Licence licence = DAOFactory.getInstance().getLicenceDAO().fetchOneByNumber(id);
            if (licence == null) {
                throw new FunctionalException(LICENCE_NOT_FOUND, id);
            } else {
                Person person = DAOFactory.getInstance().getPersonDAO().fetchOneById(licence.getPersonid());
                Club club = DAOFactory.getInstance().getClubDAO().fetchOneById(licence.getClubid());
                Season season = DAOFactory.getInstance().getSeasonDAO().fetchOneById(licence.getSeason());

                PDFGeneratorUtils.getInstance().generateLicencePdf(person, club, season, licence, outputStream);
            }
        }
    };

    private String key;

    private Set<String> contentTypes = new HashSet<>();

    DocumentType(String key, String... contentTypes) {
        this.key = key;
        Collections.addAll(this.contentTypes, contentTypes);
    }

    public static DocumentType fromValue(String text) {
        for (DocumentType b : DocumentType.values()) {
            if (String.valueOf(b.key).equals(text)) {
                return b;
            }
        }
        throw new FunctionalException(DOCUMENT_TYPE_NOT_FOUND, text);
    }

    public String getKey() {
        return key;
    }

    public boolean isAuthorized(String contentType) {
        return contentTypes.contains(contentType);
    }

    public String buildUrl(String id) {
        return MEDICAL_CERTIFICATE_BASE_PATH.getValue().replace("{type}", key).replace("{id}", id);
    }

    public abstract void writeDocument(String id, OutputStream outputStream) throws IOException;

}
