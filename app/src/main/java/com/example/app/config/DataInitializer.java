package com.example.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

  private final JdbcTemplate jdbcTemplate;
  private final PasswordEncoder passwordEncoder;

  /**
   * アプリケーション起動時処理
   * 以下の条件を満たすメソッドは、アプリケーション起動時にSpringが自動的に実行してくれる
   * - @Beanが付与されていること
   * - 戻り値がApplicationRunner / CommandLineRunnerであること
   *
   * @return applicationRunner
   */
  @Bean
  ApplicationRunner initUsers() {
    return args -> {
      String encodedPassword = passwordEncoder.encode("SamplePassword123");

      jdbcTemplate.update("""
                INSERT INTO users (username, password)
                VALUES (?, ?)
                ON CONFLICT (username)
                DO UPDATE SET password = EXCLUDED.password
                """,
        "Alice",
        encodedPassword
      );

      jdbcTemplate.update("""
                INSERT INTO users (username, password)
                VALUES (?, ?)
                ON CONFLICT (username)
                DO UPDATE SET password = EXCLUDED.password
                """,
        "Bob",
        encodedPassword
      );
    };
  }
}
