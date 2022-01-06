package com.shine.community.controller;

import com.shine.community.dto.AccessTokenDTO;
import com.shine.community.dto.GithubUser;
import com.shine.community.mapper.userMapper;
import com.shine.community.model.User;
import com.shine.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class authController {

    @Value("${gtihub.oauth.clientId}")
    private String clientId;

    @Value("${github.oauth.clientSecret}")
    private String clientSecret;

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private userMapper mapper;

    @GetMapping("/callback")
    public String GitCallback(HttpServletRequest request,
                              HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        String code = request.getParameter("code");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String tokenParam = githubProvider.getAccessToken(accessTokenDTO);
        String accessToken = tokenParam.split("&")[0].split("=")[1];
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser != null){
            User user = new User();
            user.setName(githubUser.getLogin());
            user.setAccountId(String.valueOf(githubUser.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            mapper.insertUser(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }


}
