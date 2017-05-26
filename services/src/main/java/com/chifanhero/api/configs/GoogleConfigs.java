package com.chifanhero.api.configs;

import com.chifanhero.api.common.annotations.Cfg;
import com.chifanhero.api.configs.helper.ConfigHelper;

/**
 * Created by shiyan on 5/5/17.
 */
@Cfg(prefix = "GOOGLE_")
public class GoogleConfigs {

    public final static String API_KEY;

    static {
        String prefix = ConfigHelper.getPrefix(GoogleConfigs.class);
        API_KEY = ConfigHelper.getProperty(prefix + "API_KEY", "AIzaSyDbWSwTi-anJJf25HxNrfBNicmrR0JSaOY");
    }

}
