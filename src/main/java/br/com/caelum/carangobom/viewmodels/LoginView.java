package br.com.caelum.carangobom.viewmodels;

public class LoginView {

  private String token;

  public LoginView(String token) {
    this.setToken(token);
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
