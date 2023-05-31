package com.yp.BloodBankApplication.Exception;

public class DonorNotFoundException extends RuntimeException{

    public DonorNotFoundException(String msg){
        super(msg);
    }
}
