package com.yp.BloodBankApplication.Exception;

public class OtpMissMatchException extends RuntimeException{

    public OtpMissMatchException(String msg){
        super(msg);
    }
}
