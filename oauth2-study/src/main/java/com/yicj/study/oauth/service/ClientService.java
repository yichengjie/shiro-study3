package com.yicj.study.oauth.service;

import com.yicj.study.oauth.entity.Client;

import java.util.List;

public interface ClientService {
    Client createClient(Client client);// 创建客户端
    Client updateClient(Client client);// 更新客户端
    void deleteClient(Long clientId);// 删除客户端
    Client findOne(Long clientId);// 根据id查找客户端
    List<Client> findAll();// 查找所有
    Client findByClientId(String clientId);// 根据客户端id查找客户端
    Client findByClientSecret(String clientSecret);//根据客户端安全KEY查找客户端
}