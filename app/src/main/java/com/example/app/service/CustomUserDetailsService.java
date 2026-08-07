package com.example.app.service;

import com.example.app.repository.UserRepository;
import com.example.app.security.CustomUserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  /**
   * ユーザ認証
   * @param username - ユーザ名
   * @return userDetails - 認証情報
   * @throws UsernameNotFoundException - ユーザが見つからない
   */
  @Override
  @NonNull // 戻り値はnullではない
  public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
      .map(user -> new CustomUserDetails(
        user.username(),
        user.password(),
        Collections.emptyList()) // Authority / Role 一覧
      )
      .orElseThrow(
        () -> new UsernameNotFoundException(
          String.format("Given username is not found. (username = '%s')", username))
      );
  }
}
