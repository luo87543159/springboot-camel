package com.luojun.camel.auth.token;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.IOException;

public class ResponseSerializer extends StdSerializer<Response> {

    public ResponseSerializer() {
        super(Response.class);
    }
    @Override
    public void serialize(Response value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken) value.getData();

        gen.writeStartObject();
        gen.writeNumberField("code", value.getCode());
        gen.writeStringField("msg", value.getMsg());
        gen.writeObjectFieldStart("data");
        gen.writeStringField("access_token", oAuth2AccessToken.getValue());
        gen.writeStringField("token_type", oAuth2AccessToken.getTokenType());

        if(oAuth2AccessToken.getRefreshToken() != null){
            gen.writeStringField("refresh_token", oAuth2AccessToken.getRefreshToken().getValue());
        }
        gen.writeNumberField("expires_in",oAuth2AccessToken.getExpiresIn());
        gen.writeStringField("scope",oAuth2AccessToken.getScope().toString());
        gen.writeEndObject();

        gen.writeEndObject();

    }
}
