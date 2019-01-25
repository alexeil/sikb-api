package com.boschat.sikb.configuration;

public enum ApplicationProperties {
    ACTIVATION_TOKEN_EXPIRATION_DAYS("activation.token.expirationDays"),
    SMTP_HOST("mail.smtp.host"),
    SMTP_PORT("mail.smtp.port"),
    SMTP_LOGIN("mail.smtp.login"),
    SMTP_PASSWORD("mail.smtp.password"),
    SMTP_DEFAULT_RECIPIENT("mail.smtp.defaulRecipient"),
    SMTP_DEBUG("mail.smtp.debug");

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
