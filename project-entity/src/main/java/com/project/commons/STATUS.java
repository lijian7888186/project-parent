package com.project.commons;

public enum STATUS {
    SUCCESS("000", "成功"),ERROR("999", "错误"),PARAM_ERROR("900", "参数错误"),NO_USER("700", "账号或密码不正确");
    private String code;
    private String message;

    STATUS() {
    }

    STATUS(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
