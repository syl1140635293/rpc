package com.syl.rpc.dto;

import java.io.Serializable;

public class Message implements Serializable {

    private String Message;

    private Long time;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "Message='" + Message + '\'' +
                ", time=" + time +
                '}';
    }
}
