package com.wdt.gateway.exception;

import com.wdt.common.model.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultExceptionAdvice {
    // todo 自定义异常


    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(HttpServletRequest request, Exception ex) {
        // 异常处理逻辑
        return Result.succeed(400, "未知异常");
    }



}
