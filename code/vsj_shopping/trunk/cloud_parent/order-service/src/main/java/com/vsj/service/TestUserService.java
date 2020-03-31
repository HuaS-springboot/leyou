package com.vsj.service;

import com.vsj.model.User;

public interface TestUserService {
    Object listUser();

    User findById(int id);

    Object getSysAreas();
    
    String test();
}
