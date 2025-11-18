package com.example.loan.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Lightweight runtime feature toggle system.
 */
public final class FeatureFlag {

    // Stores feature states
    private static final Map<String, Boolean> FLAGS = new ConcurrentHashMap<>();

    private FeatureFlag() {
    }

    /** Enable or disable a feature flag */
    public static void set(String name, boolean enabled) {
        FLAGS.put(name, enabled);
    }

    /** Check if a feature is enabled */
    public static boolean isEnabled(String name) {
        return FLAGS.getOrDefault(name, false);
    }
}
