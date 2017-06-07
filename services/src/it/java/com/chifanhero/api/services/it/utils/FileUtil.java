package com.chifanhero.api.services.it.utils;


import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by shiyan on 6/6/17.
 */
public class FileUtil {

    public static String readFile(String fileName) {
        try {
            return IOUtils.toString(FileUtil.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
