package com.mib.simpilist.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mib.simpilist.Enum.ErrorCodes;
import com.mib.simpilist.utililty.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String message;
    private String error;
    private Integer status;
    private LocalDateTime timeStamp;
    private Exception exception;

    public ErrorResponse(String message, String error, HttpStatus status){
        this.message=message;
        this.error=error;
        this.status=status.value();
        this.timeStamp=LocalDateTime.now();
    }

    public ErrorResponse(String message, ErrorCodes error, HttpStatus status){
        this.message=message;
        this.error=error.name();
        this.status=status.value();
        this.timeStamp=LocalDateTime.now();
    }

    public ErrorResponse(ErrorCodes error, HttpStatus status,
                         Exception exception){
        this.exception=exception;
        this.error=error.name();
        this.status=status.value();
        this.timeStamp=LocalDateTime.now();
    }


    public ErrorResponse(){
        this.message= Constants.GENERIC_ERROR_MESSAGE;
        this.error=Constants.GENERIC_ERROR_PARAMETER;
        this.status=Constants.GENERIC_ERROR_STATUS;
        this.timeStamp=LocalDateTime.now();
    }
}
