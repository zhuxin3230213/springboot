package com.example.testshiro.service;

import com.example.testshiro.entity.UserInfo;

public interface UserInfoService {
    /**
     * 通过username查找用户信息;
     */
    public UserInfo findByUsername(String username);
}