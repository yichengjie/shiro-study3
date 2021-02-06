package com.yicj.study;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.text.IniRealm;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

@Slf4j
public class ShiroRoleTest extends BaseTest{
    @Test
    public void testHelloworld() {
        login("classpath:shiro.ini", "zhang", "123");
        //6、退出
        subject().logout();
    }

    @Test
    public void testHasRole() {
        login("classpath:shiro-role.ini", "zhang", "123");
        //判断拥有角色：role1
        Assert.assertTrue(subject().hasRole("role1"));
        //判断拥有角色：role1 and role2
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
        //判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertEquals(true, result[0]);
        Assert.assertEquals(true, result[1]);
        Assert.assertEquals(false, result[2]);
        subject().logout();
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckRole() {
        login("classpath:shiro-role.ini", "zhang", "123");
        //断言拥有角色：role1
        subject().checkRole("role1");
        //断言拥有角色：role1 and role3 失败抛出异常
        subject().checkRoles("role1", "role3");
        subject().logout();
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
