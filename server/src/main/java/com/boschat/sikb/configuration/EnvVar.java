package com.boschat.sikb.configuration;

import org.apache.commons.lang3.StringUtils;

public enum EnvVar {

    CONFIG_TECH_PATH("CONFIG_TECH_PATH");

    private String env;

    EnvVar(String env) {
        this.env = env;
    }

    public String getEnv() {
        return env;
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
