package com.chifanhero.api.services.chifanhero.document;

import com.chifanhero.api.models.response.UserInfo;
import com.chifanhero.api.services.chifanhero.KeyNames;
import org.bson.Document;

/**
 * Created by shiyan on 7/31/17.
 */
public class UserInfoDocumentConverter {

    public static UserInfo toUserInfo(Document document) {
        if (document == null) {
            return null;
        }
        UserInfo user = new UserInfo();
        user.setUserId(document.getString(KeyNames.ID));
        return user;
    }
}
