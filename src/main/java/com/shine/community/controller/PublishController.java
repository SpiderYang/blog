package com.shine.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Hikari
 * @Date: 2022/1/8 1:05
 */

@Controller
public class PublishController {

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
}
