package com.bruce.springbootmall.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRegisterRequest {

    // 不能為null、長度不為0且，不能全為空白字元
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
