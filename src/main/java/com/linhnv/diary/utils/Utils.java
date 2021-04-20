package com.linhnv.diary.utils;

import com.fasterxml.uuid.Generators;

import java.util.Collection;

public class Utils {

    private Utils() {

    }

    /**
     * check str is not null & empty
     *
     * @param str input string
     * @return true if not null & empty
     */
    public static boolean notNullAndEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * @param objects
     * @return
     */
    public static boolean notNullAndEmpty(Object[] objects) {
        return objects != null && objects.length > 0;
    }


    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNullOrEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    public static boolean isNullOrEmpty(Collection<? extends Object> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * @param collection
     * @return
     */
    public static boolean notNullAndEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static String generateUUID() {
        return Generators.randomBasedGenerator().generate().toString();
    }
}
