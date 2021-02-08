package com.yicj.study;

import org.junit.Test;

public class LoginLogoutTest extends BaseTest{

    @Test
    public void testHelloworld() {
        login("classpath:shiro.ini", "zhang", "123");
        //6、退出
        subject().logout();
    }
}
