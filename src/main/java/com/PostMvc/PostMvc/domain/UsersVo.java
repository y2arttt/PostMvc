package com.PostMvc.PostMvc.domain;

import lombok.Data;

@Data
public class UsersVo {
    private String userId;
    private String password;
    private String username;
    private String tel;
    private String email;
    private String createdAt;
    private String updatedAt;
    private String role;
}
