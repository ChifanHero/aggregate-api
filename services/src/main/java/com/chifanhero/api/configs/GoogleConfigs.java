package com.chifanhero.api.configs;

import com.chifanhero.api.common.annotations.Cfg;

/**
 * Created by shiyan on 5/5/17.
 */
@Cfg(prefix = "GOOGLE_")
public class GoogleConfigs {

    public final static String API_KEY;

    static {
        String prefix = ConfigHelper.getPrefix(GoogleConfigs.class);
        System.out.println(prefix);
        API_KEY = ConfigHelper.getProperty(prefix + "API_KEY", "AIzaSyDbWSwTi-anJJf25HxNrfBNicmrR0JSaOY");
    }

}
