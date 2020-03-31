package com.vsj.material.dao;

import com.vsj.material.mapper.UserMapper;
import com.vsj.material.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {
    @Autowired
    private UserMapper userMapper;

    public int insert(User user){
        return userMapper.insert(user);
    }
}
