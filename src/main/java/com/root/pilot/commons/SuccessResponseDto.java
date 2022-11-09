package com.root.pilot.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponseDto<T> {

    private T data;

    public static <T> SuccessResponseDto<T> getResponse(T data) {
        return (SuccessResponseDto<T>) SuccessResponseDto.builder().data(data).build();
    }

}
