package com.yp.BloodBankApplication.Exception;

public class BloodBankAlreadyPresentException extends RuntimeException{

    public BloodBankAlreadyPresentException(String msg){
        super(msg);
    }
}
