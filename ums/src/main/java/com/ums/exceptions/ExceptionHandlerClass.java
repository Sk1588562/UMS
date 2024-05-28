package com.ums.exceptions;


import com.ums.payload.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerClass {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerClass.class);

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(
            ResourceNotFound exception,
            WebRequest webRequest
    ){

        String message = exception.getMessage();
          logger.error(message);
         ErrorDetails error = new ErrorDetails(message,new Date(),webRequest.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR)  ;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(
            Exception exception,
            WebRequest webRequest
    ){

        String message = exception.getMessage();
        logger.error(message);
        ErrorDetails error = new ErrorDetails(message,new Date(),webRequest.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR)  ;
    }
}
