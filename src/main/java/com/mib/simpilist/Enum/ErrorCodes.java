package com.mib.simpilist.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodes {

    BAD_REQUEST(1,"Bad Request"),
    VALIDATION_ERROR(2,"Validation Error"),
    UNAUTHORIZED(3,"Unauthorized"),
    FORBIDDEN(4,"Forbidden"),
    NOT_FOUND(5,"Not Found"),
    INTERNAL_ERROR(6,"Internal Error");

    private final Integer id;
    private final String label;
}
