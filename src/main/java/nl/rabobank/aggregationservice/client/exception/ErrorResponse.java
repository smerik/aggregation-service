package nl.rabobank.aggregationservice.client.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private final int statusCode;
    private final String errorCode;
    private final String errorMessage;
}
