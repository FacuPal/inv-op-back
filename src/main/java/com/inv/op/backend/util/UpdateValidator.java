package com.inv.op.backend.util;

import java.util.Date;
public final class UpdateValidator {

    public static boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isEmptyOrNull(Date date) {
        return date == null;
    }

}
