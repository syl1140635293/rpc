package com.syl.rpc.service;

import com.syl.rpc.dto.Message;
import com.syl.rpc.dto.RpcInvokeInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;


public class RpcProcessorHandler implements Runnable{

    private Socket socket;

    private Map<String,Object> RpcServiceExposeMap;


    public RpcProcessorHandler(Socket socket, Map<String, Object> rpcServiceExposeMap) {
        this.socket = socket;
        RpcServiceExposeMap = rpcServiceExposeMap;
    }

    @Override
    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
             outputStream = new ObjectOutputStream(socket.getOutputStream());
            Object object = inputStream.readObject();

            Object invoke = invoke((RpcInvokeInfo) object);
            outputStream.writeObject(invoke);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Object invoke(RpcInvokeInfo rpcInvokeInfo){
        Object result = null;

        String serviceName = rpcInvokeInfo.getServiceName();
        Object[] params = rpcInvokeInfo.getParams();
        String methodName = rpcInvokeInfo.getMethodName();
        Object o = RpcServiceExposeMap.get(serviceName);
        Class<?> clazz = o.getClass();
        try {
            Method method  = clazz.getMethod(methodName, Message.class);
            result = method.invoke(o, params);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
