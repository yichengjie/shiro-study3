package com.yicj.study.oauth.service;

import com.yicj.study.oauth.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);// 创建用户
    User updateUser(User user);// 更新用户
    void deleteUser(Long userId);// 删除用户
    void changePassword(Long userId, String newPassword); //修改密码
    User findOne(Long userId);// 根据id查找用户
    List<User> findAll();// 得到所有用户
    User findByUsername(String username);// 根据用户名查找用户
}