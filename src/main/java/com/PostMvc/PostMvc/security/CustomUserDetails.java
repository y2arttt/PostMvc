package com.PostMvc.PostMvc.security;

import com.PostMvc.PostMvc.domain.UsersVo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter

public class CustomUserDetails implements UserDetails {


    private final UsersVo usersVo;

    public CustomUserDetails(UsersVo usersVo) {
        this.usersVo = usersVo;
    }

    //권한 목록

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // UsersVo 객체에서 권한을 가져옴 (예: ROLE_USER)
        authorities.add(() -> usersVo.getRole());  // getRole() 메서드를 사용하여 권한을 반환

        return authorities;
    }

//    계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

//    계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
//      비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

//    사용자 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getUsername() {
        return usersVo.getUserId();  // 또는 usersVo.getUsername()
    }

    @Override
    public String getPassword() {
        return usersVo.getPassword();
    }
}
