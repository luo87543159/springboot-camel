package com.luojun.camel.auth.Configuration;

import com.luojun.camel.admin.domain.SysPermission;
import com.luojun.camel.admin.mapper.SysPermissionMapper;
import com.luojun.camel.auth.exception.CustomAccessDeineHandler;
import com.luojun.camel.auth.exception.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeineHandler customAccessDeineHandler;
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                //此处是关键，默认stateless=true，只支持access_token形式，
                // OAuth2客户端连接需要使用session，所以需要设置成false以支持session授权
                //.stateless(false)
                .accessDeniedHandler(customAccessDeineHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint);

    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling() // 配置被拦截时的处理
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 调整为让 Spring Security 不创建和使用 session

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.authorizeRequests();
        List<SysPermission> permissionList = sysPermissionMapper.selectAll();
//        authorizeRequests
//                // 下边的路径放行,不需要经过认证
//                .antMatchers("/user/logout").permitAll()
//                //OPTIONS请求不需要鉴权
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        permissionList.forEach(permission -> {
            //请求权限配置
            authorizeRequests.antMatchers(permission.getPath()).hasAuthority(permission.getName());
            System.out.println(permission);
        });
        authorizeRequests.anyRequest().authenticated();

    }
}
