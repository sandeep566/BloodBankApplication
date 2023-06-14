package com.yp.BloodBankApplication.Exception;

/**
 * Exception to indicate that a hospital is not found.
 */
public class HospitalNotFoundException extends RuntimeException{

    /**
     * Constructs a new HospitalNotFoundException with the specified error message.
     *
     * @param msg the error message
     */
    public HospitalNotFoundException(String msg){
        super(msg);
    }
}
