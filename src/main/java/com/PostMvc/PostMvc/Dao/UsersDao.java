package com.PostMvc.PostMvc.Dao;

import com.PostMvc.PostMvc.domain.UsersVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UsersDao {

    @Autowired
    private SqlSession sqlSession;

    private static final String namespace = "com.PostMvc.PostMvc.user.usersMapper";

//    사용자 등록
    public int create(UsersVo usersVo){
        return sqlSession.insert(namespace + ".create", usersVo);
    }

//    사용자 보기
    public UsersVo read(UsersVo usersVo){
        return sqlSession.selectOne(namespace + ".read", usersVo);
    }

//    사용자 수정
    public int update(UsersVo usersVo){
        return sqlSession.update(namespace + ".update", usersVo);
    }

//    사용자 삭제
    public int delete(UsersVo usersVo){
        return sqlSession.delete(namespace + ".delete", usersVo);
    }

//    사용자 목록
    public List<UsersVo> list(int offset, int pageSize, String searchType, String searchKeyword){
        Map<String,Object> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("searchType", searchType);
        params.put("searchKeyword", searchKeyword);
        return sqlSession.selectList("userMapper.list", params);

    }
    // 사용자 전체 수
    public int getTotalCount(String searchType, String searchKeyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("searchType", searchType);
        params.put("searchKeyword", searchKeyword);
        return sqlSession.selectOne("userMapper.getTotalCount", params);
    }
    // 사용자 삭제
    public int delete(String userId) {
        return sqlSession.delete("userMapper.delete", userId);
    }

}
