package com.example.app.service;

import com.example.app.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // WIP: 仮実装
    if (username.equals("Alice")) {
      return new CustomUserDetails("Alice", "password", Collections.emptyList());
    } else {
      throw new UsernameNotFoundException(
        String.format("Given username is not found. (username = '%s')", username)
      );
    }
  }
}
