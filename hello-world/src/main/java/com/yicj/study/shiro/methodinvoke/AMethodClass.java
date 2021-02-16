package com.yicj.study.shiro.methodinvoke;

import org.springframework.stereotype.Component;

@Component
public class AMethodClass {
    public static String execute(String str) {
        return str.concat("-result");
    }
    public String execute2(String str) {
        return str.concat("-result2");
    }
}
