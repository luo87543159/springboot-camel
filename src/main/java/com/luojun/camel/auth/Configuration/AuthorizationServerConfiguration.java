package com.luojun.camel.auth.Configuration;

import com.luojun.camel.auth.exception.CustomWebResponseExceptionTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 *  服务端配置认证服务器
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

    /**
     * 权限验证控制器  密码模式必须要,(认证方法的入口)
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        // 配置数据源（注意，我使用的是 HikariCP 连接池），以上注解是指定数据源，否则会有冲突
        return DataSourceBuilder.create().build();
    }

    @Bean
    public TokenStore tokenStore() {
        // 基于 JDBC 实现，令牌保存到数据
        return new JdbcTokenStore(dataSource());
    }

    @Bean
    public ClientDetailsService jdbcClientDetails() {
        // 基于 JDBC 实现，需要事先在数据库配置客户端信息
        return new JdbcClientDetailsService(dataSource());
    }

    /******************配置区域**********************/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        log.info("===============配置授权服务器开始...=========");
        //设置客户端的配置从数据库中读取，存储在oauth_client_details表
        clients.withClientDetails(jdbcClientDetails());
        log.info("===============配置授权服务器完成=========");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients() // 允许表单登录
                //允许所有客户端发送请求，避免Spring Security拦截。默认是denyAll()
                .checkTokenAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");

    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），
     * 客户端详情信息在这里进行初始化，  数据库在进行client_id 与 client_secret验证时   会使用这个service进行验证
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST) // 设置客户端可以使用get和post方式提交
                .authenticationManager(authenticationManager)//开启密码授权类型
                .tokenStore(tokenStore())
                .exceptionTranslator(customWebResponseExceptionTranslator);

    }

}
