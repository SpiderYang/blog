package com.shine.community.controller;

import com.shine.community.mapper.QuestionMapper;
import com.shine.community.mapper.userMapper;
import com.shine.community.model.Question;
import com.shine.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Hikari
 * @Date: 2022/1/8 1:05
 */

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private userMapper mapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return "publish";
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                user = mapper.findByToken(token);
                request.getSession().setAttribute("user", user);
                break;
            }
        }

        if (user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title == null || title.equals("")){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }if (description == null || description.equals("")){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }if (tag == null || tag.equals("")){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }


        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setCreator(user.getId());
        questionMapper.create(question);
        return "redirect:/";
    }
}
