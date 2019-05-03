package com.boschat.sikb.common.configuration;

public enum ApplicationProperties implements IProperties {
    ACTIVATION_TOKEN_EXPIRATION_DAYS("activation.token.expirationDays"),
    RESET_TOKEN_EXPIRATION_DAYS("reset.token.expirationDays"),
    MAIL_ENABLE("mail.enable"),
    SMTP_HOST("mail.smtp.host"),
    SMTP_PORT("mail.smtp.port"),
    SMTP_LOGIN("mail.smtp.login"),
    SMTP_PASSWORD("mail.smtp.password"),
    SMTP_DEFAULT_RECIPIENT("mail.smtp.defaultRecipient"),
    SMTP_DEBUG("mail.smtp.debug"),
    TEMPLATE_PATH("mail.template.path"),
    TEMPLATE_CREATE_USER_NAME("mail.template.createUser.name"),
    TEMPLATE_CREATE_USER_TITLE("mail.template.createUser.title"),
    TEMPLATE_RESET_USER_PASSWORD_NAME("mail.template.resetUserPassword.name"),
    TEMPLATE_RESET_USER_PASSWORD_TITLE("mail.template.resetUserPassword.title"),

    CHECK_QUERY_STRING_SEASON_ID_REGEXP("check.queryString.seasonId.regexp"),
    CHECK_BODY_FIELD_MEDICAL_CERTIFICATE_VALIDITY_REGEXP("check.bodyField.medicalCertificate.validity.regexp"),
    MEDICAL_CERTIFICATE_LIMIT_YEARS("medicalCertificate.limitYears"),
    DOCUMENT_BASE_PATH("document.basePath"),

    CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_ORIGIN("cors.header.accessControlAllowOrigin"),
    CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_METHODS("cors.header.accessControlAllowMethods"),
    CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_HEADERS("cors.header.accessControlAllowHeaders"),

    JASPER_TEMPLATE_DIRECTORY("jasper.template.directory"),
    JASPER_TEMPLATE_LICENCE_NAME("jasper.template.licence.name"),
    JASPER_TEMPLATE_LICENCE_COLORS_BY_LICENCE_TYPE("jasper.template.licence.colorsByLicenceType"),
    JASPER_TEMPLATE_LICENCE_DEFAULT_PHOTO("jasper.template.licence.defaultPhoto"),

    JASPER_TEMPLATE_LICENCE_LOGO("jasper.template.licence.logo"),
    JASPER_TEMPLATE_LICENCE_BACKGROUND("jasper.template.licence.background"),
    JASPER_TEMPLATE_LICENCE_INFORMATION_BACKGROUND("jasper.template.licence.informationBackground");

    private String propName;

    ApplicationProperties(String propName) {
        this.propName = propName;
    }

    public String getPropName() {
        return propName;
    }

    public String getValue() {
        return ConfigLoader.getInstance().findProperties(this);
    }

    public Integer getIntegerValue() {
        return Integer.valueOf(getValue());
    }

    public Boolean getBooleanValue() {
        return Boolean.valueOf(getValue());
    }

}
