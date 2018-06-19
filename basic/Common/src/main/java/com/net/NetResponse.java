package com.net;

import java.io.Serializable;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public class NetResponse implements Serializable {
    String message;
    ResponseType type;
    String jSonResponse;

    public NetResponse(ResponseType type,String message) {
        this.message = message;
        this.type = type;
        jSonResponse = null;
    }

    public NetResponse(ResponseType type, String message, String jSonResponse) {
        this.message = message;
        this.type = type;
        this.jSonResponse = jSonResponse;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", type=" + type +
                '}';
    }

    public String getjSonResponse() {
        return jSonResponse;
    }

    public String getMessage() {
        return message;
    }

    public ResponseType getType() {
        return type;
    }
}
