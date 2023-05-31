package com.yp.BloodBankApplication.Exception;

public class BloodRequestNotFoundException extends RuntimeException{

    public BloodRequestNotFoundException(String msg){
        super(msg);
    }
}
