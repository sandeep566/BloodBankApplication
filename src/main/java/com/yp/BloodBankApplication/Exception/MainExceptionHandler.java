package com.yp.BloodBankApplication.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.yp.BloodBankApplication.Response.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MainExceptionHandler {


    @ExceptionHandler(HospitalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(HospitalNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(BloodBankNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BloodBankNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BloodGroupNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BloodGroupNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BloodRequestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BloodRequestNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DonorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(DonorNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BloodNotSufficientException.class)
    public ResponseEntity<ErrorResponse> handleException(BloodNotSufficientException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ReportNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
