package com.yp.BloodBankApplication.Exception;


/**
 * Exception to indicate that a report is not found.
 */
public class ReportNotFoundException extends RuntimeException{

    /**
     * Constructs a new ReportNotFoundException with the specified error message.
     *
     * @param msg the error message
     */
    public ReportNotFoundException(String msg){
        super(msg);
    }
}
