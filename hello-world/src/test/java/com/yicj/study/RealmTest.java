package com.yicj.study;

import org.junit.Test;

public class RealmTest extends BaseTest {

    @Test
    public void testHelloworld() {
        login("classpath:shiro-realm.ini", "zhang", "123");
        //6、退出
        subject().logout();
    }
}
