package com.inv.op.backend.util;

import java.util.Date;

import javax.xml.crypto.Data;

public final class UpdateValidator {

    public static boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isEmptyOrNull(Date date) {
        return date == null;
    }

}
