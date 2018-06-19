package com.exceptions;

import java.io.Serializable;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public class LogOutException extends Exception implements Serializable {
    public LogOutException(String msg){
        super(msg);
    }
}
