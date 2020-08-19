package com.syl.rpc.client;

import com.syl.rpc.dto.RpcInvokeInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RemoteInvocationHandler<T> implements InvocationHandler {

    private Class<T> proxyInterface;

    private String serviceName;
    //这里可以维护一个缓存，存这个接口的方法抽象的对象



    public RemoteInvocationHandler(Class<T> proxyInterface, String serviceName) {
        this.proxyInterface = proxyInterface;
        this.serviceName = serviceName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcInvokeInfo rpcInvokeInfo =  new RpcInvokeInfo( serviceName,method.getName(),args);
        RpcNetTransport rpcNetTransport = new RpcNetTransport("127.0.0.1", 10001, rpcInvokeInfo);
        Object send = rpcNetTransport.send();
        return send;
    }
    public T getProxy(){
        return (T) Proxy.newProxyInstance(proxyInterface.getClassLoader(),new Class[]{proxyInterface},this);
    }

}
