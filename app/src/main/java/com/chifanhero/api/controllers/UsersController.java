package com.chifanhero.api.controllers;

import com.chifanhero.api.common.exceptions.ChifanheroException;
import com.chifanhero.api.models.response.UserInfo;
import com.chifanhero.api.services.chifanhero.ChifanheroRestaurantService;
import com.chifanhero.api.services.chifanhero.document.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by shiyan on 7/30/17.
 */

@RestController
public class UsersController {

    private final ChifanheroRestaurantService chifanheroRestaurantService;
    private static final int RETRY_TIMES = 2;

    @Autowired
    public UsersController(ChifanheroRestaurantService chifanheroRestaurantService) {
        this.chifanheroRestaurantService = chifanheroRestaurantService;
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public UserInfo getNewUser(HttpServletResponse response) {
        UserInfo userInfo = null;
        int times = 1;
        String id;
        while (userInfo == null && times <= RETRY_TIMES) {
            id = IdGenerator.getNewObjectId();
            try {
                userInfo = chifanheroRestaurantService.createNewUser(id);
            } catch (ChifanheroException e) {
                times++;
            }
        }
        if (userInfo != null) {
            response.setStatus(200);
            return userInfo;
        } else {
            response.setStatus(500);
            return null;
        }
    }
}
