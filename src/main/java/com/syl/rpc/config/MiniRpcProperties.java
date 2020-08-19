package com.syl.rpc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = MiniRpcProperties.class)
@ConfigurationProperties(prefix=MiniRpcProperties.HELLO_FORMAT_PREFIX)
public class MiniRpcProperties {

    public static final String HELLO_FORMAT_PREFIX="rpc.service";

    private Integer port;

    public MiniRpcProperties(Integer port) {
        this.port = port;
    }

    public MiniRpcProperties() {
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
