package com.chifanhero.api.services.google.client.response.converters;

import com.chifanhero.api.models.google.Photo;
import com.chifanhero.api.models.response.Picture;
import com.chifanhero.api.services.google.client.response.converters.PictureConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shiyan on 6/16/17.
 */
public class PictureConverterTest {

    @Test
    public void test() {
        Photo photo = new Photo();
        photo.setPhotoReference("reference");
        photo.setHtmlAttributions(Arrays.asList("attribution1", "attribution2"));
        Picture picture = PictureConverter.toPicture(photo);
        Assert.assertEquals("reference", picture.getPhotoReference());
        List<String> htmlAttributions = picture.getHtmlAttributions();
        Assert.assertEquals("attribution1", htmlAttributions.get(0));
        Assert.assertEquals("attribution2", htmlAttributions.get(1));
    }
}
