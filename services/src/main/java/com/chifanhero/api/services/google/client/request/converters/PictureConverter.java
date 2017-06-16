package com.chifanhero.api.services.google.client.request.converters;

import com.chifanhero.api.models.google.Photo;
import com.chifanhero.api.models.response.Picture;

/**
 * Created by shiyan on 6/16/17.
 */
public class PictureConverter {

    public static Picture toPicture(Photo photo) {
        if (photo == null) {
            return null;
        }
        Picture picture = new Picture();
        picture.setPhotoReference(photo.getPhotoReference());
        picture.setHtmlAttributions(photo.getHtmlAttributions());
        return picture;
    }
}
