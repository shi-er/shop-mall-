package com.li.yun.biao.client.common;

import com.li.yun.biao.client.config.ErrorCode;
import lombok.Data;

import java.io.Serializable;


@Data
public class Response<T> implements Serializable {
    private Integer code = 0;
    private String msg;
    private T data;

    public Response() {
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Response<T> resp() {
        Response<T> resp = new Response<>();
        resp.setCode(0);
        resp.setMsg("OK");
        return resp;
    }

    public static <T> Response<T> resp(T data) {
        Response<T> resp = new Response<>();
        resp.setData(data);
        return resp;
    }

    public static <T> Response<T> respErrorMsg(String msg) {
        Response<T> resp = new Response<>();
        resp.setCode(ErrorCode.API_ERROR_CODE_INPUT_MSG_ERROR.code);
        resp.setMsg(msg);
        return resp;
    }

    public static <T> Response<T> resp(ErrorCode code) {
        Response<T> resp = new Response<>();
        resp.setCode(code.code);
        resp.setMsg(code.msg);
        return resp;
    }

    public static <T> Response<T> resp(T data, ErrorCode code) {
        Response<T> resp = new Response<>();
        resp.setCode(code.code);
        resp.setMsg(code.msg);
        resp.setData(data);
        return resp;
    }
}
