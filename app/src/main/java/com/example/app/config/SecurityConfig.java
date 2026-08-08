package com.example.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  /**
   * 認証設定
   * @return provider
   */
  @Bean
  DaoAuthenticationProvider authenticationProvider() {
    // 自作したUserDetailsServiceを登録
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

    // パスワード比較用のエンコーダ
    provider.setPasswordEncoder(passwordEncoder);

    return provider;
  }

  /**
   * SecurityFilterChain
   * @param http
   * @return securityFilterChain
   * @throws Exception
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // 認可ルールの設定
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login/**").permitAll() // /login/配下は認証なしでアクセス可能 ログイン自体は含まない
        .requestMatchers("/users/**").hasAuthority("ADMIN") // ユーザ一覧はADMINしかアクセスできない
        .anyRequest().authenticated() // その他は認証が必要
      )
      // フォームログイン機能の設定
      .formLogin(form -> form
        .loginPage("/login") // /loginをログイページに設定
        .defaultSuccessUrl("/home", true) // ログイン後は/homeに遷移するようにする
        .permitAll() // ログインに必要なURLは認証なしでアクセス可能にする
      )
      // ログアウト設定
      .logout(logout -> logout
        .logoutSuccessUrl("/login") // デフォルトでは/login?logoutに遷移するが、クエリパラメータが不要なので消す
        .permitAll()
      );

    return http.build();
  }
}
