package com.aloha.shop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.aloha.shop.dto.users.UserJoinDto;

// PasswordMatches 검증 로직
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserJoinDto> {

  @Override
  public boolean isValid(UserJoinDto dto, ConstraintValidatorContext context) {
    // 비밀번호 또는 확인 값이 비어있으면 @NotBlank 에서 처리하도록 통과
    if (dto.getPassword() == null || dto.getPasswordConfirm() == null) {
      return true;
    }

    boolean matches = dto.getPassword().equals(dto.getPasswordConfirm());
    if (!matches) {
      // 기본 에러 경로 대신 passwordConfirm 필드에 에러 표시
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
             .addPropertyNode("passwordConfirm")
             .addConstraintViolation();
    }
    return matches;
  }
  
}
