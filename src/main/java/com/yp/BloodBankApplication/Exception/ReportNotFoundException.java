package com.yp.BloodBankApplication.Exception;

public class ReportNotFoundException extends RuntimeException{
    public ReportNotFoundException(String msg){
        super(msg);
    }
}
