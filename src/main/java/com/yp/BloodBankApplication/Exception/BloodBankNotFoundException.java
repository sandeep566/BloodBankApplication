package com.yp.BloodBankApplication.Exception;



/**
 * Exception to indicate that a blood bank is not found.
 */
public class BloodBankNotFoundException extends RuntimeException{


    /**
     * Constructs a new BloodBankNotFoundException with the specified error message.
     *
     * @param msg the error message
     */
    public BloodBankNotFoundException(String msg){
        super(msg);
    }
}
