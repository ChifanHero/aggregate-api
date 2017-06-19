package com.chifanhero.api.models.request;

/**
 * Created by shiyan on 6/18/17.
 */
public abstract class RequestComponent<T> {

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object obj);

    public abstract T clone();

}
