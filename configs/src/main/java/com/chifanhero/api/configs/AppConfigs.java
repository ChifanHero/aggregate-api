package com.chifanhero.api.configs;

import com.chifanhero.api.configs.annotation.Cfg;
import com.chifanhero.api.configs.helper.ConfigHelper;

@Cfg(prefix = "APP_")
public class AppConfigs {

    public final static int MIN_USER_VIEWS;

    static {
        String prefix = ConfigHelper.getPrefix(AppConfigs.class);

        MIN_USER_VIEWS = ConfigHelper.getPropertyAsInt(prefix + "MIN_USER_VIEWS", 3);

    }
}
