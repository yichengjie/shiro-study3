package com.yicj.study;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

@Slf4j
public class HelloWorldTest {

    @Test
    public void testHelloworld() {
        Subject subject = login("classpath:shiro.ini", "zhang", "123");
        //6、退出
        subject.logout();
    }

    @Test
    public void testHasRole() {
        Subject subject = login("classpath:shiro-role.ini", "zhang", "123");
        //判断拥有角色：role1
        Assert.assertTrue(subject.hasRole("role1"));
        //判断拥有角色：role1 and role2
        Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));
        //判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertEquals(true, result[0]);
        Assert.assertEquals(true, result[1]);
        Assert.assertEquals(false, result[2]);
    }


    private Subject login(String location, String username, String password){
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(location);
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        return subject ;
    }

    /**
     * 手动调用realm认证
     */
    @Test
    public void manualRealm(){
        IniRealm realm = new IniRealm("classpath:shiro-role.ini");
        realm.setName("iniRealm");
        // 这里init 需要手动调用
        realm.init();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        AuthenticationInfo info = realm.getAuthenticationInfo(token);
        log.info("info : {}", info);
    }

}
