package com.bruce.springbootmall.dao;

import com.bruce.springbootmall.dto.UserRegisterRequest;
import com.bruce.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
