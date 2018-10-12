package com.gwg.demo.config.log;

/**
 * 日志类型
 */
public enum LogType {

    LOGIN("登录"), ADD("新增"), DELETE("删除"), UPDATE("修改"), QUERY("查询") , EXCEPTION("异常");

    String message;

    LogType(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
