package com.syl.rpc.config;

import com.syl.rpc.client.RpcAutowiredBeanPostProcessor;
import com.syl.rpc.service.ExposeRpcService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(MiniRpcProperties.class)
public class MiniRpcAutoConfiguration {

    @Bean
    public ExposeRpcService exposeRpcService(){
        return new ExposeRpcService();
    }

    @Bean
    public RpcAutowiredBeanPostProcessor rpcAutowiredBeanPostProcessor(){
        return new RpcAutowiredBeanPostProcessor();
    }
}
