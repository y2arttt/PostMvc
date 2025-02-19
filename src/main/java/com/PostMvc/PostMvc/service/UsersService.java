package com.PostMvc.PostMvc.service;

import com.PostMvc.PostMvc.Dao.UsersDao;
import com.PostMvc.PostMvc.libs.Pagination;
import com.PostMvc.PostMvc.domain.UsersVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersService {

    private static final Logger log = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    UsersDao usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 사용자 등록

    public boolean create(UsersVo usersVo) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(usersVo.getPassword());
        usersVo.setPassword(encodedPassword);

        int result = usersDao.create(usersVo);
        return result > 0;
    }

//    사용자 보기

    public UsersVo read(UsersVo usersVo) {
        return usersDao.read(usersVo);
    }
//     사용자 수정

    public boolean update(UsersVo usersVo) {
        int result = usersDao.update(usersVo);
        return result > 0;
    }

//    비밀번호 수정

    public boolean updatePassword(UsersVo usersVo) {
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(usersVo.getPassword());
        usersVo.setPassword(encodedPassword);
        int result = usersDao.update(usersVo);
        return result > 0;
    }
//    사용자 삭제

    public boolean delete(UsersVo usersVo, String password) {
        if(!passwordEncoder.matches(password, read(usersVo).getPassword())) {
            return false;
        }

        int result = usersDao.delete(usersVo);
        return result > 0;
    }

//    사용자 목록
    public Map<String, Object> list(int page, String searchType, String searchKeyword) {
        int pageSize = 10;

        int totalCount = usersDao.getTotalCount(searchType,searchKeyword);

        Pagination pagination = new Pagination(page, pageSize, totalCount);

        List<UsersVo> userVoList = usersDao.list(pagination.getOffset(), pageSize, searchType, searchKeyword);

        // 결과 맵 생성
        Map<String, Object> result = new HashMap<>();
        result.put("userVoList", userVoList);
        result.put("pagination", pagination);
        result.put("searchType", searchType);
        result.put("searchKeyword", searchKeyword);

        return result;
    }

}
