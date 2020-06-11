package com.luojun.camel.auth.exception;

import com.luojun.camel.common.result.JsonResult;
import com.luojun.camel.common.result.ResultCode;
import com.luojun.camel.common.result.ResultTool;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        Throwable cause = authenticationException.getCause();
        //防止中文乱码的json
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();
        if(cause instanceof InvalidTokenException){
            log.error("InvalidTokenException : {}",cause.getMessage());
            //TOKEN 无效
            response.getWriter().write(mapper.writeValueAsString(ResultTool.fail(ResultCode.PARAM_ACCESS_TOKEN_ERROT)));
        }else {
            log.error("AuthenticationException : NoAuthentication");
            //资源未授权
            response.getWriter().write(mapper.writeValueAsString(ResultTool.fail(ResultCode.PARAM_NOT_ACCESS_TOKEN)));
        }
    }
}
