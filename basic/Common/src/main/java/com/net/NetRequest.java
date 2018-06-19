package com.net;

import java.io.Serializable;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public class NetRequest implements Serializable {
    RequestType requestType;
    String JsonString;

    public NetRequest(RequestType requestType) {
        this.requestType = requestType;
    }

    public NetRequest(RequestType requestType, String jsonString) {
        this.requestType = requestType;
        JsonString = jsonString;
    }

    @Override
    public String toString() {
        return "BSRequest{" +
                "requestType=" + requestType +
                ", JsonString='" + JsonString + '\'' +
                '}';
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getJsonString() {
        return JsonString;
    }
}
