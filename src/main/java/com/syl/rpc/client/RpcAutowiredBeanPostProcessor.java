package com.syl.rpc.client;

import com.syl.rpc.interfacce.RpcAutowired;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

public class RpcAutowiredBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field:declaredFields ){
            RpcAutowired declaredAnnotation = field.getDeclaredAnnotation(RpcAutowired.class);
            if(declaredAnnotation!=null){
                String serviceName = declaredAnnotation.serviceName();
                RemoteInvocationHandler remoteInvocationHandler = new RemoteInvocationHandler(field.getType(),serviceName);
                Object proxy = remoteInvocationHandler.getProxy();
                try {
                    field.setAccessible(true);
                    field.set(bean,proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
