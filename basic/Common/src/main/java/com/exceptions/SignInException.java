package com.exceptions;

import java.io.Serializable;

public class SignInException extends Exception implements Serializable {
    public SignInException(String msg){
        super(msg);
    }
}

