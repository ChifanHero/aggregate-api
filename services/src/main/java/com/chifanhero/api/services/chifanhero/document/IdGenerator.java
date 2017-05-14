package com.chifanhero.api.services.chifanhero.document;

import java.util.Random;

/**
 * Created by shiyan on 5/14/17.
 */
public class IdGenerator {

    public static String getNewObjectId() {
        return randomString(10);
    }

    private static String randomString(int size) {
        if (size == 0) {
            throw new IllegalArgumentException("Zero-length randomString is useless.");
        }
        char[] chars= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        String objectId = "";
        byte[] bytes = new byte[size];
        new Random().nextBytes(bytes);
        for (byte aByte : bytes) {
            objectId += chars[UIntFromByte(aByte) % chars.length];
        }
        return objectId;
    }

    private static int UIntFromByte(byte b) {
        int value = b;
        if (value < 0) value += 256;
        return value;
    }
}
