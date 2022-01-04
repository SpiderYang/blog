package com.shine.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shine.community.dto.AccessTokenDTO;
import com.shine.community.dto.GithubUser;
import netscape.javascript.JSObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    @Value("${github.accessToken.url}")
    private String accessTokenUrl;

    @Value("${github.access.url}")
    private String accessUrl;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSON(accessTokenDTO).toString());
        Request request = new Request.Builder()
                .url(accessTokenUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return string;
        } catch (IOException e) {

        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(accessUrl)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            GithubUser user =  JSON.parseObject(string, GithubUser.class);
            return user;
        } catch (IOException e) {

        }
        return null;
    }
}
