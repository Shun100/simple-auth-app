package com.example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // 認可ルールの設定
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login/*+").permitAll() // /login/配下は認証なしでアクセス可能 ログイン自体は含まない
        .anyRequest().authenticated() // その他は認証が必要
      )
      // フォームログイン機能の設定
      .formLogin(form -> form
        .loginPage("/login") // /loginをログイページに設定
        .permitAll() // ログインに必要なURLは認証なしでアクセス可能にする
      );

    return http.build();
  }
}
