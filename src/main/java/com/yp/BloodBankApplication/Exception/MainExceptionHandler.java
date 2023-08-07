package com.yp.BloodBankApplication.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.yp.BloodBankApplication.Response.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Global exception handler for handling specific exceptions.
 */
@RestControllerAdvice
public class MainExceptionHandler {


    /**
     * Exception handler for handling HospitalNotFoundException.
     */
    @ExceptionHandler(HospitalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(HospitalNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    /**
     * Exception handler for handling BloodBankNotFoundException.
     */
    @ExceptionHandler(BloodBankNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BloodBankNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    /**
     * Exception handler for handling BloodRequestNotFoundException.
     */
    @ExceptionHandler(BloodRequestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BloodRequestNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }



    /**
     * Exception handler for handling DonorNotFoundException.
     */
    @ExceptionHandler(DonorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(DonorNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for handling BloodNotSufficientException.
     */
    @ExceptionHandler(BloodNotSufficientException.class)
    public ResponseEntity<ErrorResponse> handleException(BloodNotSufficientException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }



    /**
     * Exception handler for handling ReportNotFoundException.
     */
    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ReportNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BloodBankAlreadyPresentException.class)
    public ResponseEntity<ErrorResponse> handleException(BloodBankAlreadyPresentException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HospitalAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(HospitalAlreadyExistsException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AadharAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleException(AadharAlreadyExistException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OtpMissMatchException.class)
    public ResponseEntity<ErrorResponse> handleException(OtpMissMatchException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
