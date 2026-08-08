package com.example.app.repository;

import com.example.app.enums.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.app.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
  private final JdbcTemplate jdbcTemplate;

  /**
   * ユーザ検索 (ユーザ名)
   * @param username - ユーザ名
   * @return optionalUser - ユーザ情報
   */
  public Optional<UserEntity> findByUsername(String username) {
    String sql = "SELECT * FROM users WHERE username = ?";

    List<UserEntity> users = jdbcTemplate.query(
      sql,
      (rs, rowNum) -> new UserEntity(
        rs.getString("username"),
        rs.getString("password"),
        Authority.valueOf(rs.getString("authority"))
      ),
      username
    );

    return users.stream().findFirst();
  }

  /**
   * ユーザ全件取得
   * @return userEntities - ユーザ一覧情報
   */
  public List<UserEntity> findAll() {
    String sql = "SELECT * FROM users";

    return jdbcTemplate.query(
      sql,
      (rs, rowNum) -> new UserEntity(
        rs.getString("userName"),
        rs.getString("password"),
        Authority.valueOf(rs.getString("authority"))
      )
    );
  }

  /**
   * ユーザ登録
   * @param entity - 登録情報
   * @throws RuntimeException - 登録失敗
   */
  public void create(UserEntity entity) {
    String sql = """
      INSERT INTO
        users (username, password, authority)
      VALUES
        (?, ?, ?::authority)
    """;

    try {
      jdbcTemplate.update(sql, entity.username(), entity.password(), entity.authority().name());
    } catch (DuplicateKeyException e) {
      throw new RuntimeException("そのユーザ名は既に存在します", e);
    } catch (Exception e) {
      System.err.println(e.toString());
      throw new RuntimeException("ユーザ登録に失敗しました", e);
    }
  }
}
