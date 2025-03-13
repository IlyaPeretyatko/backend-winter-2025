package ru.cft.peretyatko.error.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.cft.peretyatko.error.response.ServiceErrorResponse;
import ru.cft.peretyatko.error.exception.ServiceException;
import ru.cft.peretyatko.error.exception.ValidationException;
import ru.cft.peretyatko.error.response.ValidationErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ServiceErrorResponse> handleAllExceptions(Exception e) {
        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse(500, e.getMessage());
        return new ResponseEntity<>(serviceErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    private ResponseEntity<ServiceErrorResponse> handleServiceException(ServiceException e) {
        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse(e.getCode(), e.getMessage());
        return new ResponseEntity<>(serviceErrorResponse, HttpStatusCode.valueOf(e.getCode()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleMyExceptions(ValidationException ex) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(ex.getMessage(), ex.getFieldsWithError());
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
