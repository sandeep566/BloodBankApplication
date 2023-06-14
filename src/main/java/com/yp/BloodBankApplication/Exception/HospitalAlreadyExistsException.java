package com.yp.BloodBankApplication.Exception;

public class HospitalAlreadyExistsException extends RuntimeException{

    public HospitalAlreadyExistsException(String msg){
        super(msg);
    }
}
