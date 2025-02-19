package com.PostMvc.PostMvc.controller;

import com.PostMvc.PostMvc.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/")
    public ResponseEntity<String> listGet(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword

    ) {
        Map<String, Object> result = usersService.list(page, searchType, searchKeyword);
        return ResponseEntity.ok(result.toString());
    }
}
