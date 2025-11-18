package com.example.loan.common;

import java.util.Collection;

/**
 * Basic collection helpers.
 */
public final class CollectionUtils {

    private CollectionUtils() {
    }

    /** Check if collection is null or empty */
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    /** Check if collection has elements */
    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }
}
