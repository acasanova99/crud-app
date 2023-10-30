package com.skz.back.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public final class ResponseDto<T> {
    private Integer code;
    private T payload;

    public static <T> ResponseDto<T> successfulResponseFrom(T payload) {
        return ResponseDto.<T>builder()
                .code(HttpStatus.OK.value())
                .payload(payload)
                .build();
    }
}
