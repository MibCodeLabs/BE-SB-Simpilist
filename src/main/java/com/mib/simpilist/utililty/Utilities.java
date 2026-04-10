package com.mib.simpilist.utililty;

import com.mib.simpilist.Enum.ErrorCodes;
import com.mib.simpilist.dto.Utility.PageResponse;
import com.mib.simpilist.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

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

    public static <T> Page<T> getPagedList(List<T> list, Integer pageNumber) {
        return new PageImpl<>(
                list,
                PageRequest.of(pageNumber, list.size()),
                list.size()
        );
    }

    public static <T, R> Page<R> mapPage(Page<T> page, Function<T, R> mapper) {
        return new PageImpl<>(
                page.getContent()
                        .stream()
                        .map(mapper)
                        .toList(),
                page.getPageable(),
                page.getTotalElements()
        );
    }

    public static <T> PageResponse<T> toPageResponse(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    public static <T, R> PageResponse<R> mapPageToPageResponse(Page<T> page, Function<T, R> mapper){
        return toPageResponse(mapPage(page,mapper));
    }

}
