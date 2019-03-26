package com.project.commons;

/**
 * describe:
 *
 * @author
 */
public class ResData {
    private String code;
    private String data;
    private String message;

    public ResData() {
    }

    public ResData(String code, String data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public ResData(STATUS status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
