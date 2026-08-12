package com.example.app.dto;

import com.example.app.annotation.StrongPassword;
import com.example.app.enums.Authority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
  @NotBlank(message = "ユーザ名は必須です")
  String username,

  @NotBlank(message = "パスワードは必須です")
  @StrongPassword
  String password,

  @NotNull(message = "権限を選択してください")
  Authority authority
) {}
