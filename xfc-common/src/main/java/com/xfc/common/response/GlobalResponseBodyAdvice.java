package com.xfc.common.response;

import com.alibaba.fastjson2.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.jetbrains.annotations.NotNull;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author jiao xn
 * @date 2023/4/4 15:32
 * @description 统一响应处理，拦截 Response 并返回
 */
@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NotNull MethodParameter returnType,
                            @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        GlobalResponse globalResponse = returnType.getMethodAnnotation(GlobalResponse.class);
        return globalResponse == null || globalResponse.format();
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NotNull MethodParameter returnType,
                                  @NotNull MediaType selectedContentType,
                                  @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NotNull ServerHttpRequest request,
                                  @NotNull ServerHttpResponse response) {
        // 如果是 actuator 请求，直接返回
        if (isActuatorRequest(request)) {
            return body;
        }

        /* 如果是 Feign 请求，直接返回
         */
        if (Objects.requireNonNull(request.getHeaders().get("user-agent")).get(0).startsWith("Java")) {
            return body;
        }

        // 以下代码主要解决和 Swagger 的冲突
        if (body instanceof ResponseStructure || body instanceof Json || body instanceof UiConfiguration ||
                (body instanceof ArrayList && !((ArrayList<?>) body).isEmpty() &&
                        ((ArrayList<?>) body).get(0) instanceof SwaggerResource)) {
            return body;
        }

        if (body instanceof InputStreamResource) {
            return body;
        }

        ResponseStructure<Object> responseStructure;

        // 如果是 POST 请求，业务状态码统一设置为 201
        if ("POST".equals(((ServletServerHttpRequest) request).getServletRequest().getMethod())) {
            responseStructure = ResponseStructure.created("OK");
        } else {
            responseStructure = ResponseStructure.success(null);
        }

        // 如果返回值是字符串类型，则用其替换 message
        if (body instanceof String) {
            responseStructure.setData(body);
            return JSON.toJSONString(responseStructure);
        }

        if (body instanceof byte[]) {
            return body;
        }

        responseStructure.setData(body);

        return responseStructure;
    }

    private boolean isActuatorRequest(ServerHttpRequest request) {
        return ((ServletServerHttpRequest) request).getServletRequest().getRequestURI().endsWith("/actuator");
    }
}
