package com.example.app.service;

import com.example.app.dto.CreateUserDTO;
import com.example.app.entity.UserEntity;
import com.example.app.enums.Authority;
import com.example.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * ユーザ全件検索
   * @return users - ユーザ一覧
   */
  public List<UserEntity> findAll() {
    return userRepository.findAll();
  }

  /**
   * ユーザ登録
   * @param dto - 登録情報
   */
  public void create(CreateUserDTO dto) {
    // パスワードのエンコード
    String encodedPassword = passwordEncoder.encode(dto.password());

    // 登録
    UserEntity entity = new UserEntity(
      dto.username(),
      encodedPassword,
      Authority.USER
    );
    userRepository.create(entity);
  }
}
