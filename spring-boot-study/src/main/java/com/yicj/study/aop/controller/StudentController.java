package com.yicj.study.aop.controller;

import com.yicj.study.aop.anno.SystemLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping(path = "/ok")
    @SystemLogger(description = "查询学生")    //注解方法处
    public String select() {
        return "ok";
    }

}
