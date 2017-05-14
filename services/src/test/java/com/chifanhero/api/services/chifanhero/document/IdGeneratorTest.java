package com.chifanhero.api.services.chifanhero.document;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by shiyan on 5/14/17.
 */
public class IdGeneratorTest {

    @Test
    public void test() {
        String id = IdGenerator.getNewObjectId();
        assertEquals(10, id.length());
    }

    @Test
    public void testDuplicates() {
        Set<String> idSet = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            String newId = IdGenerator.getNewObjectId();
            if (!idSet.contains(newId)) {
                idSet.add(newId);
            } else {
                fail(newId);
            }
        }
    }
}
