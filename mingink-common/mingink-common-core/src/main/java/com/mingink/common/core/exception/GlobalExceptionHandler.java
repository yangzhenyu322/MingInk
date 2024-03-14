package com.mingink.common.core.exception;

import com.mingink.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/3/2 23:36
 * @description 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public R<?> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        if(StringUtils.isNotEmpty(e.getDescription())) return R.fail(e.getCode(), e.getDescription());
        return R.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public R<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return R.fail(ErrorCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
