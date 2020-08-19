package com.syl.rpc.service;

import com.syl.rpc.config.MiniRpcProperties;
import com.syl.rpc.interfacce.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class ExposeRpcService implements ApplicationContextAware, InitializingBean, EnvironmentAware {

    private Map<String,Object> RpcServiceExposeMap = new HashMap();

    private Integer port;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(RpcService.class);
        for (Map.Entry map:beansWithAnnotation.entrySet()) {
            RpcService annotation = map.getValue().getClass().getAnnotation(RpcService.class);
            String name = annotation.name();
            RpcServiceExposeMap.put(name,map.getValue());
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        final Integer port = getPort();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RpcListenServer rpcListenServer = new RpcListenServer(RpcServiceExposeMap,port);
                rpcListenServer.startListen();
            }
        }).start();
    }

    public Integer getPort() throws Exception{
        if(port==null){
            throw new Exception(" Please configure the RPC port information ");
        }
        return Integer.valueOf(port);
    }


    @Override
    public void setEnvironment(Environment environment) {
        Integer port = environment.getProperty("rpc.service.port", Integer.class);
        this.port = port;
    }
}
