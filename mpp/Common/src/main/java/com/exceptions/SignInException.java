package com.exceptions;

import java.io.Serializable;

/**
 * Created by sergiubulzan on 17/06/2017.
 */
public class SignInException extends Exception implements Serializable {
    public SignInException(String msg){
        super(msg);
    }
}

