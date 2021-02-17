package com.yicj.study.oauth.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id; //编号
    private String username; //用户名
    private String password; //密码
    private String salt; //加密密码的盐

    public String getCredentialsSalt() {
        return username + salt;
    }

}