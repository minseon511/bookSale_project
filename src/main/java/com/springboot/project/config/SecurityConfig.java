package com.springboot.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.springboot.project.service.CustomOAuth2UserService; // import 추가
import org.springframework.beans.factory.annotation.Autowired; // import 추가

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// CustomOAuth2UserService를 주입받습니다.
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/insertBook.do", "/add-to-cart", "/cart").authenticated() // 장바구니 관련 주소 추가
                .anyRequest().permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/getBookList.do"))
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/getBookList.do")
                // [수정] 로그인 성공 후 사용자 정보를 처리할 서비스를 지정
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
            );
        
        return http.build();
    }
}