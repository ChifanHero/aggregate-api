package com.chifanhero.api.services.it;

import com.chifanhero.api.services.it.initializers.Initializer;
import com.chifanhero.api.services.it.initializers.MongoDbInitializer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shiyan on 6/4/17.
 */
public class TestEnvInitializer {

    private final static List<Initializer> INITIALIZERS = Arrays.asList(new MongoDbInitializer());

    public static void main(String[] args) {
        System.out.println("test env intializer");
    }
}
