package br.com.caelum.carangobom.domain;

import java.time.Year;
import javax.persistence.AttributeConverter;

// Reference: https://vladmihalcea.com/java-time-year-month-jpa-hibernate/
public class YearAttributeConverter implements AttributeConverter<Year, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Year attribute) {
    if (attribute != null) {
      return attribute.getValue();
    }

    return null;
  }

  @Override
  public Year convertToEntityAttribute(Integer dbData) {
    if (dbData != null) {
      return Year.of(dbData);
    }

    return null;
  }

}
