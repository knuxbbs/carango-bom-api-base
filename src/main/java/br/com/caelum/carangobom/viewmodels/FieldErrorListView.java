package br.com.caelum.carangobom.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class FieldErrorListView {

  private List<FieldErrorView> errors = new ArrayList<>();

  public List<FieldErrorView> getErrors() {
    return errors;
  }

  public void setErrors(List<FieldErrorView> errors) {
    this.errors = errors;
  }

}
