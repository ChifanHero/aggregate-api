package com.chifanhero.api.configs;

import com.chifanhero.api.common.annotations.Cfg;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConfigHelperTest {

    @Test
    public void testGetProperty() {
        setEnv("test1", "test");
        Assert.assertEquals("test", ConfigHelper.getProperty("test1", null));
    }

    @Test
    public void testGetPropertyDefault() {
        Assert.assertEquals("test2", ConfigHelper.getProperty("test2", "test2"));
    }

    @Test
    public void testGetPropertyAsInt() {
        setEnv("test3", "1");
        Assert.assertEquals(1, ConfigHelper.getPropertyAsInt("test3", null).intValue());
    }

    @Test
    public void testGetPropertyAsIntDefault() {
        Assert.assertEquals(2, ConfigHelper.getPropertyAsInt("test4", 2).intValue());
    }

    @Test
    public void testGetPropertyAsList() {
        setEnv("test5", "a|a|a");
        List<String> list = ConfigHelper.getPropertyAsList("test5", null);
        Assert.assertEquals(3, list.size());
        list.forEach(e -> Assert.assertEquals("a", e));
    }

    @Test
    public void testGetPropertyAsListDefault() {
        List<String> list = ConfigHelper.getPropertyAsList("test6", Arrays.asList("b", "b", "b"));
        Assert.assertEquals(3, list.size());
        list.forEach(e -> Assert.assertEquals("b", e));
    }

    @Test
    public void testGetPropertyAsTypedList() {
        setEnv("test7", "1|1|1");
        List<Integer> list = ConfigHelper.getPropertyAsList(Integer.class, "test7", null, Integer::valueOf);
        Assert.assertEquals(3, list.size());
        list.forEach(e -> Assert.assertEquals(1, e.intValue()));
    }

    @Test
    public void testGetPropertyAsTypedListDefault() {
        List<Integer> list = ConfigHelper.getPropertyAsList(Integer.class, "test8", Arrays.asList(2, 2, 2), Integer::valueOf);
        Assert.assertEquals(3, list.size());
        list.forEach(e -> Assert.assertEquals(2, e.intValue()));
    }

    @Cfg(prefix = "test")
    private class TestConfig  {

    }

    @Test
    public void testGetPrefix() {
        String prefix = ConfigHelper.getPrefix(TestConfig.class);
        Assert.assertEquals("test", prefix);
    }

    private void setEnv(String key, String value) {
        try
        {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.put(key, value);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
            cienv.put(key, value);
        }
        catch (NoSuchFieldException e)
        {
            try {
                Class[] classes = Collections.class.getDeclaredClasses();
                Map<String, String> env = System.getenv();
                for(Class cl : classes) {
                    if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                        Field field = cl.getDeclaredField("m");
                        field.setAccessible(true);
                        Object obj = field.get(env);
                        Map<String, String> map = (Map<String, String>) obj;
                        map.clear();
                        map.put(key, value);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
