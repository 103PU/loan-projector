package com.example.loan.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * Loads application configuration from classpath properties file.
 */
public final class ConfigManager {

    // Internal properties store
    private static final Properties PROPS = new Properties();

    // Default config file
    private static final String DEFAULT_FILE = "application.properties";

    static {
        load(DEFAULT_FILE); // Load default configuration
    }

    private ConfigManager() {
    }

    /**
     * Load config from a given properties file.
     */
    public static void load(String fileName) {
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName)) {

            if (in != null) {
                PROPS.load(in); // Load properties
            } else {
                LoggerUtil.warn("ConfigManager", "File not found: " + fileName);
            }

        } catch (IOException e) {
            LoggerUtil.error("ConfigManager", "Config loading failed", e);
        }
    }

    /**
     * Get a config value wrapped in Optional.
     */
    public static Optional<String> get(String key) {
        return Optional.ofNullable(PROPS.getProperty(key));
    }

    /**
     * Get config value or default if missing.
     */
    public static String getOrDefault(String key, String defaultVal) {
        return PROPS.getProperty(key, defaultVal);
    }
}
