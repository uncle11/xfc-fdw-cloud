package com.xfc.workflow.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xfc.common.response.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  认证失败处理器，响应失败的结果
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        ResponseStructure responseStructure = ResponseStructure.instance(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        String json = objectMapper.writeValueAsString(responseStructure);

        response.getWriter().write(json);
    }

}
