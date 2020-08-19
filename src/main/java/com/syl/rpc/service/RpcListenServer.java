package com.syl.rpc.service;

import com.syl.rpc.dto.RpcConfig;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcListenServer {

    private Map<String,Object> RpcServiceExposeMap;

    private Integer port;
    
    boolean flag = true;

    public RpcListenServer(Map<String, Object> rpcServiceExposeMap, Integer port) {
        RpcServiceExposeMap = rpcServiceExposeMap;
        this.port = port;
    }

    public RpcListenServer() {
    }

    public void startListen(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            ServerSocket serverSocket =  new ServerSocket(port);
            System.out.println("开启监听 :"+port);
            while (flag){
                Socket socket = serverSocket.accept();
                RpcProcessorHandler rpcProcessorHandler = new RpcProcessorHandler(socket, RpcServiceExposeMap);
                executorService.execute(rpcProcessorHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
