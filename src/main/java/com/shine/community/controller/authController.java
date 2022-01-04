package com.shine.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.shine.community.dto.AccessTokenDTO;
import com.shine.community.dto.GithubUser;
import com.shine.community.provider.GithubProvider;
import org.omg.CORBA.ServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;

@RestController
public class authController {

    @Value("${gtihub.oauth.clientId}")
    private String clientId;

    @Value("${github.oauth.clientSecret}")
    private String clientSecret;

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String GitCallback(HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        String code = request.getParameter("code");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String tokenParam = githubProvider.getAccessToken(accessTokenDTO);
        String accessToken = tokenParam.split("&")[0].split("=")[1];
        GithubUser user = githubProvider.getUser(accessToken);
        if(user != null){
            request.getSession().setAttribute("user", user);
            return JSONObject.toJSON(user).toString();
        }else {
            return "redirect:/";
        }

    }


}
