package com.chifanhero.api.configs.type;

/**
 * Created by shiyan on 5/17/17.
 */
public class Host {

    private String host;
    private Integer port;

    public Host(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
