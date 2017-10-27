package com.chifanhero.api.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shiyan on 7/17/17.
 */
public class StringUtilTest {

    @Test
    public void testRemoveSpaceBetweenChineseCharacters() {
        String s = StringUtil.removeSpaceBetweenChineseCharacters("汉 字");
        Assert.assertEquals("汉字", s);
    }

    @Test
    public void testRemoveSpaceBetweenChineseCharactersNull() {
        Assert.assertNull(StringUtil.removeSpaceBetweenChineseCharacters(null));
    }

    @Test
    public void testRemoveSpaceBetweenChineseCharactersEmpty() {
        Assert.assertEquals("", StringUtil.removeSpaceBetweenChineseCharacters(""));
    }

    @Test
    public void testRemoveSpaceBetweenChineseCharactersAllEnglish() {
        Assert.assertEquals("Sushi bar", StringUtil.removeSpaceBetweenChineseCharacters("Sushi bar"));
    }

    @Test
    public void testRemoveSpaceBetweenChineseCharactersNoSpace() {
        Assert.assertEquals("汉字", StringUtil.removeSpaceBetweenChineseCharacters("汉字"));
    }

    @Test
    public void testRemoveSpaceBetweenChineseCharactersMixed() {
        Assert.assertEquals("a b 汉字", StringUtil.removeSpaceBetweenChineseCharacters("a b 汉字"));
    }
}
