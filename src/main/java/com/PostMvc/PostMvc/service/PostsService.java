package com.PostMvc.PostMvc.service;

import com.PostMvc.PostMvc.domain.PostVo;
import com.PostMvc.PostMvc.Dao.PostsDao;
import com.PostMvc.PostMvc.libs.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostsService {

    @Autowired
    PostsDao postDao;

    @Autowired
    private  PasswordEncoder passwordEncoder;

//    게시글 등록
    public boolean create(PostVo postVo){
        int result = postDao.create(postVo);
        return result > 0;
    }

//    게시글 목록
    public Map<String, Object>list(int page, int pageSize, String searchType, String searchKeyword){

//        전체 게시글 수 조회
        int totalCount = postDao.getTotalCount(searchType, searchKeyword);

//        페이지네이션 정보 생성
        Pagination pagination = new Pagination(page, pageSize, totalCount);

//        페이징된 게시글 목록 조회
        List<PostVo> postVoList = postDao.list(pagination.getOffset(),pageSize,searchType,searchKeyword);

//        결과 맵 생성
        Map<String, Object> result = new HashMap<>();
        result.put("postVoList", postVoList);
        result.put("pagination", pagination);
        result.put("searchType", searchType);
        result.put("searchKeyword", searchKeyword);

        return result;


    }



//    게시글 보기
    public PostVo read(int id){
        return postDao.read(id);
    }

//    게시글 수정
    public boolean update(PostVo postVo){
        int result = postDao.update(postVo);
        return result > 0;
    }
//    게시글 삭제
    public boolean delete(PostVo postVo){


        int result = postDao.delete(postVo.getId());
        return result > 0;
    }
}
