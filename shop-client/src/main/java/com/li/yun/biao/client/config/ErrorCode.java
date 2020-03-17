package com.li.yun.biao.client.config;

public enum ErrorCode {
    /**
     *
     */
    API_ERROR_CODE_INPUT_MSG_ERROR(4001, "信息输入有误，请检查后重新操作"),
    API_ERROR_CODE_NO_PERMISSION_ERROR(4004, "信息输入有误，请检查后重新操作"),
    API_ERROR_CODE_MOBILE_ERROR(40011, "手机号输入有误，请重新输入"),
    API_ERROR_CODE_PWD_ERROR(40012, "密码输入有误过于简单，请重新输入"),
    API_ERROR_CODE_FREQUENT_REQUESTS_ERROR(40013, "请求频繁，请稍后重试"),
    API_ERROR_CODE_AUTH_CODE_ERROR(40014, "验证码有误，请重新输入"),
    API_ERROR_CODE_NO_ACCOUNT_ERROR(40015, "账号不存在，请先注册"),
    API_ERROR_CODE_ACCOUNT_PWD_ERROR(40016, "账号或密码有误，请重新输入"),
    SERVER_UNKNOWN_EXCEPTION(5000, "系统错误(ERROR_CODE:5000)"),
    API_ERROR_CODE_OSS_REQUEST_INVALID(5001, "不是来自阿里云OSS的请求"),
    API_ERROR_CODE_CHANNEL_TRADE_BANK_NO(5002, "您的银行卡验证失败"),

    API_ERROR_CODE_AGENT_CONFIG_EXCEPTION(6000, "代理商分润配置异常"),
    API_ERROR_CODE_AGENT_BALANCE_INSUFFICIENT(6001, "代理商余额不够补贴请联系代理商");


    public final Integer code;
    public final String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
