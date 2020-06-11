package com.luojun.camel.auth.token;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize(using = ResponseSerializer.class)
@Data
public class Response {

    private Integer code;
    private String msg;
    private Object data;
}
