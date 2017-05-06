package com.chifanhero.api.services.yelp.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shiyan on 4/26/17.
 */

@Component
public class Oauth {

//    public static String BEAR_TOKEN;

    private String bearToken;

    private final static Long ONE_SECOND = 1000L;
    private final static Long SECONDS_IN_DAY = 86400L;

    private final OauthService oauthService;

    @Autowired
    public Oauth(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    public String getBearToken() {
        return bearToken;
    }

    public void setBearToken(String bearToken) {
        this.bearToken = bearToken;
    }

    /**
     * Update Yelp oauth2 bear token.
     * Yelp bear token expires in 180 days. To be safe, we update the token every 30 days.
     */
    @PostConstruct
    @SuppressWarnings("unused")
    public void updateBearToken() {
//        System.out.println("Updating bear token");
//        Timer time = new Timer();
//        OauthTask st = new OauthTask();
//        time.schedule(st, 0, 30 * SECONDS_IN_DAY * ONE_SECOND);
    }

    /**
     * Call yelp oauth2 endpoint to obtain bear token
     */
    private class OauthTask extends TimerTask {
        @Override
        public void run() {
            bearToken = oauthService.getBearToken();
            System.out.println(bearToken);
        }
    }
}
