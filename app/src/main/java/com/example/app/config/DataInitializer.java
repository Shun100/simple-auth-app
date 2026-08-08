package com.example.app.config;

import com.example.app.enums.Authority;
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

      String sql1 = """
        INSERT INTO users (username, password, authority)
        VALUES (?, ?, ?::authority)
        ON CONFLICT (username)
        DO UPDATE SET
          password = EXCLUDED.password,
          authority = EXCLUDED.authority
      """;
      jdbcTemplate.update(sql1, "Alice", encodedPassword, Authority.ADMIN.name());

      String sql2 = """
        INSERT INTO users (username, password, authority)
        VALUES (?, ?, ?::authority)
        ON CONFLICT (username)
        DO UPDATE SET
          password = EXCLUDED.password,
          authority = EXCLUDED.authority
      """;
      jdbcTemplate.update(sql2, "Bob", encodedPassword, Authority.USER.name());
    };
  }
}
