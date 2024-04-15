package com.xfc.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiao xn
 * @date 2022/8/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStructure<T>{
    private Integer code;
    private String status;
    private String message;
    private T data;
    private static final String SUCCESS = "success";

    public static <T> ResponseStructure<T> success() {
        return new ResponseStructure<>(
                ResponseStatusCodeEnum.SUCCESS.getCode(),
                SUCCESS,
                ResponseStatusCodeEnum.SUCCESS.getMessage(),
                null
        );
    }
    public static <T> ResponseStructure<T> success(T data) {
        return new ResponseStructure<>(
                ResponseStatusCodeEnum.SUCCESS.getCode(),
                SUCCESS,
                ResponseStatusCodeEnum.SUCCESS.getMessage(),
                data
        );
    }

    public static <T> ResponseStructure<T> created(String message) {
        return new ResponseStructure<>(
                ResponseStatusCodeEnum.UPDATE_RETURN_201.getCode(),
                SUCCESS,
                message,
                null);
    }

    public static <T> ResponseStructure<T> success(String message, T data) {
        return new ResponseStructure<>(
                ResponseStatusCodeEnum.SUCCESS.getCode(),
                SUCCESS,
                message,
                data);
    }

    public static <T> ResponseStructure<T> success(Integer code, String message) {
        return new ResponseStructure<>(code, SUCCESS, message, null);
    }

    public static <T> ResponseStructure<T> success(Integer code, String message, T data) {
        return new ResponseStructure<>(code, SUCCESS, message, data);
    }

    public static ResponseStructure<Object> failed() {
        return new ResponseStructure<>(
                ResponseStatusCodeEnum.GET_RETURN_500.getCode(),
                "fail",
                ResponseStatusCodeEnum.GET_RETURN_500.getMessage(),
                null);
    }

    public static ResponseStructure<String> failed(String message) {
        return new ResponseStructure<>(
                ResponseStatusCodeEnum.GET_RETURN_500.getCode(),
                "fail",
                message,
                null);
    }

    public static ResponseStructure<Object> failed(IResponseStatusCode errorResult) {
        return new ResponseStructure<>(
                errorResult.getCode(),
                "fail",
                errorResult.getMessage(),
                null);
    }

    public static ResponseStructure<Object> conflict(String message) {
        return new ResponseStructure<>(
                409,
                "fail",
                message,
                null);
    }

    public static <T> ResponseStructure<T> instance(Integer code, String message, T data) {
        ResponseStructure<T> responseStructure = new ResponseStructure<>();
        responseStructure.setCode(code);
        responseStructure.setMessage(message);
        responseStructure.setData(data);

        if (code >= 300) {
            responseStructure.setStatus("fail");
        } else {
            responseStructure.setStatus(SUCCESS);
        }

        return responseStructure;
    }
    public static <T> ResponseStructure<T> instance(Integer code, String message) {
        ResponseStructure<T> responseStructure = new ResponseStructure<>();
        responseStructure.setCode(code);
        responseStructure.setMessage(message);
        responseStructure.setData(null);

        if (code >= 300) {
            responseStructure.setStatus("fail");
        } else {
            responseStructure.setStatus(SUCCESS);
        }

        return responseStructure;
    }

    public static <T> ResponseStructure<T> instance(IResponseStatusCode code) {
        ResponseStructure<T> responseStructure = new ResponseStructure<>();
        responseStructure.setCode(code.getCode());
        responseStructure.setMessage(code.getMessage());
        responseStructure.setData(null);

        if (code.getCode() >= 300) {
            responseStructure.setStatus("fail");
        } else {
            responseStructure.setStatus(SUCCESS);
        }

        return responseStructure;
    }
}
