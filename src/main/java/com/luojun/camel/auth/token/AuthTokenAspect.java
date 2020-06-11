package com.luojun.camel.auth.token;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * 使用AOP 切面，将这个ResponseEntity进行重新组织
 * 生成新的access_token 返回格式
 */
@Component
@Aspect
@Slf4j
public class AuthTokenAspect {
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        // 放行
        Response response = new Response();
        Object proceed = pjp.proceed();
        if (proceed != null) {
            ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>)proceed;
            OAuth2AccessToken body = responseEntity.getBody();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                response.setCode(200);
                response.setMsg("成功");
                response.setData(body);
            } else {
                log.error("error:{}", responseEntity.getStatusCode().toString());
                response.setCode(400);
                response.setMsg("获取授权码失败");
            }
        }
        return ResponseEntity
                .status(200)
                .body(response);
    }

}
