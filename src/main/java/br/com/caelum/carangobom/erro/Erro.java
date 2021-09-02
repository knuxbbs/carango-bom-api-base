package br.com.caelum.carangobom.erro;

import org.springframework.http.HttpStatus;

public class Erro {

  private HttpStatus httpStatus;
  private String message;

  public Erro(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
