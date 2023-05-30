package com.yp.BloodBankApplication.Exception;

public class HospitalNotFoundException extends RuntimeException{
    public HospitalNotFoundException(String msg){
        super(msg);
    }
}
