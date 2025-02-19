package com.PostMvc.PostMvc.service;

import com.PostMvc.PostMvc.Dao.UsersDao;
import com.PostMvc.PostMvc.domain.UsersVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersService usersService;


    //    로그인
    public UsersVo login(UsersVo usersVo){
        log.info("로그인 시도: username = " + usersVo.getUsername());
        UsersVo existUSerVo = usersDao.read(usersVo);

        if(existUSerVo != null) {
            String rawPassword = usersVo.getPassword();
            String encryptedPassword = existUSerVo.getPassword();
            log.info("사용자 찾음: " + existUSerVo.getUsername());
            log.info("입력된 비밀번호: {}", rawPassword);
            log.info("저장된 암호화 비밀번호: {}", encryptedPassword);
            boolean matches = passwordEncoder.matches(rawPassword, encryptedPassword);
            log.info("비밀번호 일치 여부: {}", matches);

            if(matches) {
                log.info("비밀번호 일치");
                return existUSerVo;
            } else {
                log.warn("비밀번호 불일치");
            }

        } else {
            log.warn("사용자를 찾을 수 없음: " + usersVo.getUsername());
        }



        return null;
    }
//    비밀번호 초기화
    public String resetPassword(UsersVo usersvo) {
        String rndPassword = UUID.randomUUID().toString().substring(0, 8);

        usersvo.setPassword(rndPassword);
        log.info("유저 정보: {}",usersvo.toString());
        boolean updated = usersService.updatePassword(usersvo);

        if(updated) {
            return rndPassword;
        }
        else {
            return null;
        }
    }

}
