package com.yicj.study.common.service.impl;

import com.yicj.study.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// //要创建代理的目标Bean
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
	public void print(){
		log.info("hello busi");
	}
}