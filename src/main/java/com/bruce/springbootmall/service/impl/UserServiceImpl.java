package com.bruce.springbootmall.service.impl;

import com.bruce.springbootmall.dao.UserDao;
import com.bruce.springbootmall.dto.UserLoginRequest;
import com.bruce.springbootmall.dto.UserRegisterRequest;
import com.bruce.springbootmall.model.User;
import com.bruce.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    // 註冊
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        //檢查註冊email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if(user != null){
            log.warn("該email {} 已經被註冊",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    // 登入
    @Override
    public User login(UserLoginRequest userLoginRequest) {

        // 根據Email向資料庫查詢使用者資料
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 檢查帳號是否存在
        if(user == null){
            log.warn("該email {}尚未註冊",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 檢查密碼是否正確
        if(user.getPassword().equals(userLoginRequest.getPassword())){
            return user;
        }else {
            // 密碼錯誤
            log.warn("email {} 密碼不正確",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
