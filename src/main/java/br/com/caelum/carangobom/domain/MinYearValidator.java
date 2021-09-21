package br.com.caelum.carangobom.domain;

import java.time.Year;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinYearValidator implements ConstraintValidator<MinYearConstraint, Year> {

  private int minValue;

  @Override
  public void initialize(MinYearConstraint constraintAnnotation) {
    minValue = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(Year value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    if (value.getValue() >= minValue) {
      return true;
    }

    return false;
  }

}
