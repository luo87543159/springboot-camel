package com.luojun.camel.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 错误信息中文配置加载classpath:messages/messages的配置，
 */
@Configuration
public class I18nConfig {
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages_zh_CN");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }
}
