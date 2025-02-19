package com.PostMvc.PostMvc.Dao;

import com.PostMvc.PostMvc.domain.PostVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostsDao {


    private final JdbcTemplate jdbcTemplate;

    private final SqlSession sqlSession; // SqlSession이란 RDB에 인증을 거친 논리적인 연결 상태를 말하는 것이다.


    private  static final String namespae = "postMapper";

//    게시글 등록
    public int create(PostVo postVo){
        return sqlSession.insert(namespae + ".create",postVo);
    }
/*    public int create(PostVo postVo) {
        String sql = "INSERT INTO POSTS (TITLE, CONTENT, USERNAME, PASSWORD) VALUES (?,?,?,?)";
        int result = -1;

        try {
            result = jdbcTemplate.update(sql, postVo.getTitle(),postVo.getContent(),postVo.getUsername(),postVo.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/

//    게시글 목록
    public List<PostVo> list(int offset, int pageSize, String searchType, String searchKeyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        params.put("searchType", searchType);
        params.put("searchKeyword", searchKeyword);
        return sqlSession.selectList(namespae + ".list",params);
    }

    /*
    public List<PostVo> list(){
        String sql = "SELECT * FROM POSTS";
        List<PostVo> postVoList = null;

        try {
            postVoList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(PostVo.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postVoList;
    }
*/

//    게시글 목록

    public List<PostVo> list(int offset, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset",offset);
        params.put("pageSize",pageSize);
        return sqlSession.selectList(namespae + ".list",params);
    }

//    게시글 보기

    public PostVo read(int id){
        return  sqlSession.selectOne(namespae + ".read",id);//id 인자 넘기기
    }

    /*public PostVo read(int id) {
        String sql = "SELECT * FROM POSTS WHERE ID = ?";
        PostVo postVo = null;

        try {
            postVo = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(PostVo.class),id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postVo;
    }*/

//    게시글 수정

    public int update(PostVo postVo){
        return sqlSession.update(namespae + ".update",postVo);//MyBatis에서 자동으로 postVo의 getter를 호출하여 #{}에 반환
    }

    /* public int update(PostVo postVo) {
        String sql = "UPDATE POSTS SET TITLE = ?, CONTENT = ?, USERNAME = ?, PASSWORD = ? WHERE ID = ?";
        int result = -1;

        try {
            result = jdbcTemplate.update(sql,postVo.getTitle(),postVo.getContent(),postVo.getUsername(),postVo.getPassword(),postVo.getId());

        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/

//    게시글 삭제
    public int delete(int id){
        return sqlSession.delete(namespae + ".delete",id);
    }

    /*    public int delete(int id) {
        String sql = "DELETE FROM POSTS WHERE ID = ?";
        int result = -1;
        try {
            result = jdbcTemplate.update(sql,id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/

//    전체 게시글 수
    public int getTotalCount(String searchType, String searchKeyword){
        Map<String, Object> params = new HashMap<>();
        params.put("searchType", searchType);
        params.put("searchKeyword", searchKeyword);
        return sqlSession.selectOne(namespae + ".getTotalCount",params);
    }
}
