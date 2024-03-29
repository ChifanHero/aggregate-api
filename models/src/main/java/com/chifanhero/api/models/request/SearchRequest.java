package com.chifanhero.api.models.request;

import com.chifanhero.api.models.response.Error;

import java.util.List;

/**
 * Created by shiyan on 5/6/17.
 */
public abstract class SearchRequest<T> extends RequestComponent<T>{

    abstract List<Error> validate();
}
