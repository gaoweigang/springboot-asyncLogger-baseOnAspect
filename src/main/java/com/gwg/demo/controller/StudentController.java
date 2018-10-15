package com.gwg.demo.controller;

import com.gwg.demo.config.log.BussinessLog;
import com.gwg.demo.config.log.LogObjectHolder;
import com.gwg.demo.config.log.LogType;
import com.gwg.demo.vo.StudentVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @RequestMapping(value = "/queryStudentInfo", method = RequestMethod.GET)
    @BussinessLog(logName = "查询学生信息")
    public String queryStudentInfo(){
        LogObjectHolder.me().setObject("test");
        return "hello";
    }

    @RequestMapping(value = "/queryStudent", method = RequestMethod.POST)
    @BussinessLog(logName = "查询学生信息")
    public String queryStudentInfo(@RequestBody StudentVo vo){
        LogObjectHolder.me().setObject("test");
        return "hello";
    }
}
