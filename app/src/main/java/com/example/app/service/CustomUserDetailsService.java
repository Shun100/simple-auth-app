package com.example.app.service;

import com.example.app.enums.Authority;
import com.example.app.repository.UserRepository;
import com.example.app.security.CustomUserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        toGrantedAuthorities(user.authority())) // Spring Bootでは1ユーザが複数権限を持てるためListにする
      )
      .orElseThrow(
        () -> new UsernameNotFoundException(
          String.format("Given username is not found. (username = '%s')", username))
      );
  }

  /**
   * 権限情報変換
   * @param authority - 権限情報
   * @return grantedAuthorities - Spring Boot用の権限情報
   */
  private List<GrantedAuthority> toGrantedAuthorities(Authority authority) {
    return List.of(new SimpleGrantedAuthority(authority.name()));
  }
}
