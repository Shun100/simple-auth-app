package com.example.app.entity;

import com.example.app.enums.Authority;

public record UserEntity(
  String username,
  String password,
  Authority authority
) {}
