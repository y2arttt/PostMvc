package com.PostMvc.PostMvc.service;

import com.PostMvc.PostMvc.security.CustomUserDetails;
import com.PostMvc.PostMvc.Dao.UsersDao;
import com.PostMvc.PostMvc.domain.UsersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersDao usersDao;



    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UsersVo existUserVo = new UsersVo();
        existUserVo.setUserId(userId);
        UsersVo usersVo = usersDao.read(existUserVo);
        if(usersVo != null){
            return new CustomUserDetails(usersVo);
        }

        return null;
    }
}
