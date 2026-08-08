package com.example.app.dto;

import com.example.app.annotation.StrongPassword;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
  @NotBlank(message = "ユーザ名は必須です")
  String username,

  @NotBlank(message = "パスワードは必須です")
  @StrongPassword
  String password
) {}
