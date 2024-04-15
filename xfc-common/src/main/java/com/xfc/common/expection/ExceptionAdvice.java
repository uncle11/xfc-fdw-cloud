package com.xfc.common.expection;


import com.xfc.common.response.ResponseStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import javax.naming.AuthenticationException;
import javax.xml.bind.ValidationException;
import java.util.Objects;

import static com.xfc.common.response.ResponseStatusCodeEnum.ALL_RETURN_401;


/**
 * @author jiao xn
 * @date 2023/4/4 15:17
 * @description
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BindException.class)
    public Object bindException(BindException bindException) {
        bindException.printStackTrace();
        String message = Objects.requireNonNull(bindException.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseStructure.conflict(message);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({ValidationException.class})
    public ResponseStructure<Object> handleValidationException(ValidationException validationException) {
        validationException.printStackTrace();
        return (ResponseStructure<Object>) ResponseStructure.conflict(validationException.getMessage());
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseStructure<Object> handleMaxUploadSizeException(MaxUploadSizeExceededException maxUploadSizeExceededException) {
        maxUploadSizeExceededException.printStackTrace();
        return (ResponseStructure<Object>) ResponseStructure.conflict("当前文件大小已超过限制大小，请重新上传文件");
    }

    /**
     * 顶级异常捕获，当其他异常无法处理时选择使用
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseStructure<String> handle(Exception exception) {
        exception.printStackTrace();
        return (ResponseStructure<String>) ResponseStructure.failed(exception.getMessage());
    }

    /**
     * 认证异常捕获
     */
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class})
    public ResponseStructure<String> handleUnAhthorized(AuthenticationException exception) {
        exception.printStackTrace();
        return ResponseStructure.instance(ALL_RETURN_401.getCode(), exception.getMessage());
    }
}
