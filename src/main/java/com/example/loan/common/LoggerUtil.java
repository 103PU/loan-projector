package com.example.loan.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple centralized logger utility.
 */
public final class LoggerUtil {

    private LoggerUtil() {
    }

    // Shared logger instance
    private static final Logger LOG = LoggerFactory.getLogger(LoggerUtil.class);

    /** Log info message */
    public static void info(String tag, String msg) {
        LOG.info("[{}] {}", tag, msg);
    }

    /** Log warning message */
    public static void warn(String tag, String msg) {
        LOG.warn("[{}] {}", tag, msg);
    }

    /** Log error with stacktrace */
    public static void error(String tag, String msg, Throwable t) {
        LOG.error("[{}] {}", tag, msg, t);
    }
}
