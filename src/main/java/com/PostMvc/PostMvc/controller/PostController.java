package com.PostMvc.PostMvc.controller;

import com.PostMvc.PostMvc.domain.PostVo;
import com.PostMvc.PostMvc.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor

@RequestMapping("/posts")
public class PostController {

    private final PostsService postsService;

//    게시글 등록
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<String> createPost(PostVo postVo, HttpServletRequest request) {

        //        로그인 사용자 ID
        String userId = (String) request.getSession().getAttribute("userId");

        postVo.setCreatedBy(userId);

        boolean created = postsService.create(postVo);
        if (created) {
            return ResponseEntity.ok("Post created successfully");
        } else {
            return ResponseEntity.badRequest().body("Post creation failed");
        }


    }

//    게시글 목록
    @GetMapping("")
    @ResponseBody
    public Map<String, Object> listGet(
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword
    ) {

        int pageSize = 10;
        Map<String, Object> result = postsService.list(page,pageSize,searchType,searchKeyword);
        return result;
    }

//    게시글 보기
    @GetMapping("/{id}")
    @ResponseBody
    public PostVo readGet(@PathVariable("id") int id) {
        PostVo postVo = postsService.read(id);
        return  postVo;
    }


//    게시글 수정
    @PostMapping("{id}/update")
    @ResponseBody
    public String updatePost(@PathVariable("id") int id, PostVo postVo){
        postVo.setId(id);
        boolean updated = postsService.update(postVo);
        if (updated) {
            return "성공";
        }
        else{
            return "실패";
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public String deletePost(@PathVariable("id") int id,@RequestBody PostVo postVo){
        boolean deleted = postsService.delete(postVo);
        if (deleted) {
            return "성공";
        } else {
            return "실패";
        }
    }

}
