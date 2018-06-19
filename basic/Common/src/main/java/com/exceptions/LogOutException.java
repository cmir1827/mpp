package com.exceptions;

import java.io.Serializable;

public class LogOutException extends Exception implements Serializable {
    public LogOutException(String msg){
        super(msg);
    }
}
