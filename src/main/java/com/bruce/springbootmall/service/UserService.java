package com.bruce.springbootmall.service;

import com.bruce.springbootmall.dto.UserLoginRequest;
import com.bruce.springbootmall.dto.UserRegisterRequest;
import com.bruce.springbootmall.model.User;


public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);

}
