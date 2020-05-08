package nl.rabobank.aggregationservice.controller;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.aggregationservice.client.exception.ErrorResponse;
import nl.rabobank.aggregationservice.client.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(final Exception e) {
        final ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            ResourceAccessException.class,
            UnsupportedOperationException.class
    })
    public ResponseEntity<ErrorResponse> internalServerError(final Exception e) {
        LOG.error("An internal server error occurred.", e);
        final ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "An internal server occurred.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
