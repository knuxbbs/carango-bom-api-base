package br.com.caelum.carangobom.webapi;

import org.springframework.http.HttpStatus;

public class Erro {

  private HttpStatus httpStatus;
  private String message;

  public Erro(HttpStatus httpStatus, String message) {
    setHttpStatus(httpStatus);
    setMessage(message);
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
