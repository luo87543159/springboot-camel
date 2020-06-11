package com.luojun.camel.auth.service;

/**
 * token 接口
 */
public interface OauthService {

    /**
     * 根据accesstoken 获取用户名
     * @param accessToken
     * @return
     */
    String selectAccountByAccessToken(String accessToken);
}
