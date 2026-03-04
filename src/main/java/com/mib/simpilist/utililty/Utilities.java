package com.mib.simpilist.utililty;

import com.mib.simpilist.Enum.ErrorCodes;
import com.mib.simpilist.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.util.Collection;

@Slf4j(topic ="Utilities" )
public class Utilities {

    public static <T> Boolean isNull(T t) {
        return t == null;
    }

    public static <T> boolean isNullOrEmpty(T object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return ((String) object).trim().isEmpty(); // Treats empty or whitespace strings as empty
        }
        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        }
        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }
        return false;
    }

    public static <T> Boolean isNotNull(T t){
        return !isNull(t);
    }

    public static <T> Boolean isNotNullOrEmpty(T t){
        return !isNullOrEmpty(t);
    }


    public static ResponseEntity<Object> generateErrorResponse(Exception exception,ErrorCodes errorCodes,HttpStatus status){
        return new ResponseEntity<> (
                new ErrorResponse(
                        exception.getMessage(),
                        errorCodes,
                        status
                ),
                status);
    }

    public static ResponseEntity<Object> generateErrorResponse(String message,ErrorCodes errorCodes,HttpStatus status){
        return new ResponseEntity<> (
                new ErrorResponse(
                        message,
                        errorCodes,
                        status
                ),
                status);
    }
}
