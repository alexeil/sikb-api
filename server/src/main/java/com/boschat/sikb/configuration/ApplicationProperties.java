package com.boschat.sikb.configuration;

public enum ApplicationProperties {
    ACTIVATION_TOKEN_EXPIRATION_DAYS("activation.token.expirationDays");

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

}
