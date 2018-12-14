package com.example.springbootsecuritydemo.service;

import com.example.springbootsecuritydemo.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends JpaRepository<SysUser, Integer> {
    public SysUser findByName(String userName);
}
