package com.yicj.study.oauth.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Client implements Serializable {
    private Long id;
    private String clientName;
    private String clientId;
    private String clientSecret;
}