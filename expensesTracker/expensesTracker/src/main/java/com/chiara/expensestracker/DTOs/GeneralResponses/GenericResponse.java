package com.chiara.expensestracker.DTOs.GeneralResponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GenericResponse<T> {

    @Nullable
    @JsonProperty(value = "statusCode")
    private Integer statusCode;
    @Nullable
    @JsonProperty(value = "httpStatus")
    private HttpStatus httpStatus;
    @Nullable
    @JsonProperty(value = "message")
    private String message;
    @Nullable
    @JsonProperty(value = "data")
    private T data;

    public GenericResponse(Integer statusCode, HttpStatus httpStatus, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public GenericResponse(T data, Integer statusCode) {
        this.data = data;
        this.statusCode = statusCode;
    }

    public GenericResponse(String message) {
        this.message = message;
    }

}
