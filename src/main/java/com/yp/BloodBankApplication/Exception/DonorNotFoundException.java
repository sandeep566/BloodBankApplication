package com.yp.BloodBankApplication.Exception;

/**
 * Exception to indicate that a donor is not found.
 */
public class DonorNotFoundException extends RuntimeException{

    /**
     * Constructs a new DonorNotFoundException with the specified error message.
     *
     * @param msg the error message
     */
    public DonorNotFoundException(String msg){
        super(msg);
    }
}
