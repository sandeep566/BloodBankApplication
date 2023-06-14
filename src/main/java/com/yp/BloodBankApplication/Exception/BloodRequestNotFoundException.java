package com.yp.BloodBankApplication.Exception;



/**
 * Exception to indicate that a blood request is not found.
 */
public class BloodRequestNotFoundException extends RuntimeException{

    /**
     * Constructs a new BloodRequestNotFoundException with the specified error message.
     *
     * @param msg the error message
     */
    public BloodRequestNotFoundException(String msg){
        super(msg);
    }
}
