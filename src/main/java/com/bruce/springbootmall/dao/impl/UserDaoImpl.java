package com.bruce.springbootmall.dao.impl;

import com.bruce.springbootmall.dao.ProductDao;
import com.bruce.springbootmall.dao.UserDao;
import com.bruce.springbootmall.dto.UserRegisterRequest;
import com.bruce.springbootmall.model.User;
import com.bruce.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 根據 User ID 查詢使用者資料
    @Override
    public User getUserById(Integer userId) {

        // 修改處 1：把 [users] 改為 [user]，因為你資料庫表名是 [user]
        String sql ="SELECT user_id, email, password,created_date, last_modified_date " +
                "FROM [user] WHERE user_id=:userId";

        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map, new UserRowMapper());

        if(userList.size() > 0){
            return userList.get(0);
        }else {
            return null;
        }
    }

    // 根據 Email 查詢使用者資料
    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id, email, password,created_date, last_modified_date " +
        "FROM [user] WHERE email = :email";

        Map<String,Object> map = new HashMap<>();
        map.put("email",email);

        List<User> userlist = namedParameterJdbcTemplate.query(sql,map, new UserRowMapper());

        if(userlist.size() > 0){
            return userlist.get(0);
        }else  {
            return null;
        }
    }

    // 創建一筆帳號
    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {

        String sql = "INSERT INTO [user](email,password,created_date,last_modified_date) " +
                "VALUES (:email,:password,:createDate,:last_modifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());

        Date now = new Date();
        map.put("createDate", now);
        map.put("last_modifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();


        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
    }
}