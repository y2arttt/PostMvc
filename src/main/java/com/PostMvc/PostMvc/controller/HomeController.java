package com.PostMvc.PostMvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String index(){

        log.debug("HomeController: 홈페이지 요청 처리");
        return "home";
    }
}
