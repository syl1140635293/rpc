package com.syl.rpc.client;

import com.syl.rpc.dto.RpcInvokeInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcNetTransport {

    private String host;

    private int port;

    private RpcInvokeInfo rpcInvokeInfo;


    public RpcNetTransport(String host, int port, RpcInvokeInfo rpcInvokeInfo) {
        this.host = host;
        this.port = port;
        this.rpcInvokeInfo = rpcInvokeInfo;
    }

    public Object send(){
        Object result = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            Socket socket =  new Socket(host,port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(rpcInvokeInfo);
            outputStream.flush();

            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
