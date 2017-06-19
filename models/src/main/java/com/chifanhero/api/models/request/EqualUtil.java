package com.chifanhero.api.models.request;

/**
 * Created by shiyan on 6/18/17.
 */
public class EqualUtil {

    public static boolean equal(Object obj1, Object obj2) {
        return (obj1 == null && obj2 == null) || !(obj1 == null || obj2 == null) && obj1.equals(obj2);
    }
}
