package com.skz.back.exception;

import com.skz.back.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BackendException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDto<String> handleCustomRuntimeException(BackendException ex) {
        var ret = ResponseDto.<String>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .payload(ex.getMessage())
                .build();
        LOGGER.warn(ret.toString());
        return ret;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDto<String> handleGenericException(Exception ex) {
        var ret =  ResponseDto.<String>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .payload("Upsss Internal Server Error, Contact your admin")
                .build();
        LOGGER.error(ret.toString());
        return ret;
    }
}

