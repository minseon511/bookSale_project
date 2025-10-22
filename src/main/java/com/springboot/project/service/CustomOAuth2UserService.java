package com.springboot.project.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.springboot.project.model.User;
import com.springboot.project.model.UserDao;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 구글에서 받아온 정보로 User 객체 생성
        User user = new User();
        user.setName((String) attributes.get("name"));
        user.setEmail((String) attributes.get("email"));
        user.setPicture((String) attributes.get("picture"));
        
        // 우리 DB에 사용자 정보 저장 또는 업데이트
        UserDao userDao = new UserDao();
        user = userDao.save(user);

        // 스프링 시큐리티가 사용할 수 있도록 OAuth2User 객체로 변환하여 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                attributes,
                "email");
    }
}