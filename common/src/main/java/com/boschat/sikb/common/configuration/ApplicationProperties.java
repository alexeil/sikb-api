package com.boschat.sikb.common.configuration;

public enum ApplicationProperties implements IProperties {
    ACTIVATION_TOKEN_EXPIRATION_DAYS("activation.token.expirationDays"),
    SMTP_HOST("mail.smtp.host"),
    SMTP_PORT("mail.smtp.port"),
    SMTP_LOGIN("mail.smtp.login"),
    SMTP_PASSWORD("mail.smtp.password"),
    SMTP_DEFAULT_RECIPIENT("mail.smtp.defaultRecipient"),
    SMTP_DEBUG("mail.smtp.debug"),
    TEMPLATE_PATH("mail.template.path"),
    TEMPLATE_CREATE_USER_NAME("mail.template.createUser.name"),
    TEMPLATE_CREATE_USER_TITLE("mail.template.createUser.title");

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
