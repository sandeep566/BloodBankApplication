package com.yp.BloodBankApplication.Exception;

/**
 * Exception to indicate that there is not sufficient blood available.
 */
public class BloodNotSufficientException extends RuntimeException{

    /**
     * Constructs a new BloodNotSufficientException with the specified error message.
     *
     * @param msg the error message
     */
    public BloodNotSufficientException(String msg){
        super(msg);
    }
}
