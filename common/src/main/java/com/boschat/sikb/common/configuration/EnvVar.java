package com.boschat.sikb.common.configuration;

import org.apache.commons.lang3.StringUtils;

public enum EnvVar {

    CONFIG_PATH("CONFIG_PATH", true),
    POSTGRES_DB("POSTGRES_DB", true),
    POSTGRES_USER("POSTGRES_USER", true),
    POSTGRES_PASSWORD("POSTGRES_PASSWORD", false),
    POSTGRES_HOST("POSTGRES_HOST", true),
    POSTGRES_PORT("POSTGRES_PORT", true);

    private String env;

    private boolean printable;

    EnvVar(String env, boolean printable) {
        this.env = env;
        this.printable = printable;
    }

    public String getEnv() {
        return env;
    }

    public boolean isPrintable() {
        return printable;
    }

    /**
     * Gets the value.
     *
     * @return First value from :
     * <ol>
     * <li>La {@link System#getenv(String) env var} defined in system
     * <li>La {@link System#getProperty(String) system property} defined by application
     * </ol>
     */
    public String getValue() {
        return StringUtils.isNotEmpty(System.getenv(env)) ? System.getenv(env) : System.getProperty(env);
    }
}
