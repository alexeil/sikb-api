package com.boschat.sikb.common.configuration;

import com.boschat.sikb.common.exceptions.TechnicalException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import static com.boschat.sikb.common.configuration.ResponseCode.CONFIG_TECH_LOADING_ERROR;

public class ConfigLoader {

    private static ConfigLoader loader = null;

    private Properties properties;

    public static ConfigLoader getInstance() {
        if (null == loader) {
            loader = new ConfigLoader();
        }
        return loader;
    }

    private static String findProperties(IProperties property, Properties properties) {
        if (null == properties) {
            throw new TechnicalException(CONFIG_TECH_LOADING_ERROR, "Properties not load.");
        }
        String propValue = (String) properties.get(property.getPropName());
        if (null == propValue) {
            throw new TechnicalException(CONFIG_TECH_LOADING_ERROR, "Properties '" + property.getPropName() + "' not set.");
        }
        return propValue;
    }

    private static void checkAnyPropertyMissing(Properties newProperties) {
        for (ApplicationProperties property : ApplicationProperties.class.getEnumConstants()) {
            findProperties(property, newProperties);
        }
    }

    public String findProperties(IProperties property) {
        return findProperties(property, properties);
    }

    public void setProperties(ApplicationProperties propName, String value) {
        properties.setProperty(propName.getPropName(), value);
    }

    public void loadAndCheckTechnicalConfig(String configDir, String configFileFilename) {
        Properties familyProp = new Properties();
        if (null != this.properties) {
            familyProp.putAll(this.properties);
        } else {
            this.properties = new Properties();
        }

        try (InputStream is = getPropertiesInputStream(configDir, configFileFilename)) {
            familyProp.load(is);
        } catch (Throwable t) {
            throw new TechnicalException(CONFIG_TECH_LOADING_ERROR, t, t.getMessage());
        }

        checkAnyPropertyMissing(familyProp);
        this.properties.putAll(familyProp);
    }

    private InputStream getPropertiesInputStream(String configDir, String configFileFilename) throws IOException {
        InputStream is;
        if (configDir.contains("http")) {
            is = new URL(configDir + "/" + configFileFilename).openStream();
        } else {
            File f = new File(configDir, configFileFilename);
            is = new FileInputStream(f);
        }
        return is;
    }

    public void clear() {
        properties = null;
    }

}
