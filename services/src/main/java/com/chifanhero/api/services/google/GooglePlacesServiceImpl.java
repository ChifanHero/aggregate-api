package com.chifanhero.api.services.google;

import com.chifanhero.api.async.FutureResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shiyan on 5/1/17.
 */
@Service
public class GooglePlacesServiceImpl implements GooglePlacesService {

    private final FutureResolver futureResolver;

    @Autowired
    public GooglePlacesServiceImpl(FutureResolver futureResolver) {
        this.futureResolver = futureResolver;
    }
}
