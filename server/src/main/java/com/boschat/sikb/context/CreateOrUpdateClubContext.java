package com.boschat.sikb.context;

import com.boschat.sikb.common.exceptions.TechnicalException;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;

import static com.boschat.sikb.common.configuration.ResponseCode.INVALID_BODY_FIELD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_MEDICAL_CERTIFICATE;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_MEDICAL_CERTIFICATE_FILE_NAME;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_NAME;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_SHORT_NAME;
import static com.boschat.sikb.model.DocumentType.MEDICAL_CERTIFICATE_TYPE;
import static com.boschat.sikb.service.PersonUtils.checkContentType;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class CreateOrUpdateClubContext {

    private String name;

    private String shortName;

    private byte[] logoInputStream;

    private static CreateOrUpdateClubContext buildCommon(ClubForUpdate clubForUpdate) {
        CreateOrUpdateClubContext createOrUpdateContext = new CreateOrUpdateClubContext();
        createOrUpdateContext.setName(clubForUpdate.getName());
        createOrUpdateContext.setShortName(clubForUpdate.getShortName());
        return createOrUpdateContext;
    }

    public static CreateOrUpdateClubContext create(ClubForCreation clubForCreation) {
        checkRequestBodyField(clubForCreation.getName(), BODY_FIELD_NAME);
        checkRequestBodyField(clubForCreation.getShortName(), BODY_FIELD_SHORT_NAME);
        return buildCommon(clubForCreation);
    }

    public static CreateOrUpdateClubContext create(ClubForUpdate clubForUpdate) {
        return buildCommon(clubForUpdate);
    }

    public static CreateOrUpdateClubContext create(InputStream stream, FormDataContentDisposition formDataContentDisposition) {
        checkContentType(MEDICAL_CERTIFICATE_TYPE, formDataContentDisposition.getFileName());
        checkRequestBodyField(stream, BODY_FIELD_MEDICAL_CERTIFICATE_FILE_NAME);

        CreateOrUpdateClubContext createOrUpdateContext = new CreateOrUpdateClubContext();
        createOrUpdateContext.setLogoInputStream(stream);
        return createOrUpdateContext;
    }

    public byte[] getLogoInputStream() {
        return logoInputStream;
    }

    public void setLogoInputStream(InputStream logoInputStream) {
        try {
            this.logoInputStream = IOUtils.toByteArray(logoInputStream);
        } catch (Exception e) {
            throw new TechnicalException(INVALID_BODY_FIELD, e, BODY_FIELD_MEDICAL_CERTIFICATE, e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}
