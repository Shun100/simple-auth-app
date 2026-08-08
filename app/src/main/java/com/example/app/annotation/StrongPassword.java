package com.example.app.annotation;

import com.example.app.controller.validator.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
  String message() default "パスワードは8文字以上で、大文字英字と数字を1文字以上含めてください";

  // Validationのお作法なので今は理解しなくて良い
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
