package com.example.app.controller.validator;

import com.example.app.annotation.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

  /**
   * パスワード検証
   * @param password - パスワード
   * @param context - エラーメッセージを細かく制御するためのクラス (今回はそこまで細かい制御は行わないので未使用)
   * @return isValid
   */
  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    // null, 空文字チェックはこのValidatorの責務ではないのでtrueを返す
    if (password == null || password.isBlank()) { return true; }

    return password.length() >= 8
      && password.matches(".*[A-Z].*")
      && password.matches(".*[0-9].*");
  }
}
