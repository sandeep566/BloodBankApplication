package com.yp.BloodBankApplication.Exception;

public class AadharAlreadyExistException extends RuntimeException{
    public AadharAlreadyExistException(String msg){
        super(msg);
    }
}
