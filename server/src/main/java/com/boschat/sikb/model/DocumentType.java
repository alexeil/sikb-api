package com.boschat.sikb.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.boschat.sikb.common.configuration.ApplicationProperties.MEDICAL_CERTIFICATE_BASE_PATH;

public enum DocumentType {

    MEDICAL_CERTIFICATE_TYPE("medicalCertificate", "image/png", "image/jpeg", "application/pdf"),
    LICENCE_TYPE("licence", "application/pdf");

    private String key;

    private Set<String> contentTypes = new HashSet<>();

    DocumentType(String key, String... contentTypes) {
        this.key = key;
        Collections.addAll(this.contentTypes, contentTypes);
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
}
