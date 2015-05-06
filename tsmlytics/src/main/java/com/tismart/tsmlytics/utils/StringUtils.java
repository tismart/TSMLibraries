package com.tismart.tsmlytics.utils;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class StringUtils {

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
