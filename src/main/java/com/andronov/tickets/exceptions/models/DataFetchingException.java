package com.andronov.tickets.exceptions.models;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error fetching data")
public class DataFetchingException extends Exception {
    public DataFetchingException(String errorMessage) {
        super(errorMessage);
    }
}
