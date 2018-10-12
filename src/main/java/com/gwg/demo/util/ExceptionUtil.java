package com.gwg.demo.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    //异常消息限制长度
    private static final int EXCEPTION_MSG_LENGTH = 10000;

    /**
     * 获取异常的具体信息
     */
    public static String convertExceptionMsg(Exception e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
            String msg = sw.getBuffer().toString().replaceAll("\\$", "T");
            if(msg.length() > EXCEPTION_MSG_LENGTH){
                msg = msg.substring(0, EXCEPTION_MSG_LENGTH);
            }
            return msg;
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
