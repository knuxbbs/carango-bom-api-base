package br.com.caelum.carangobom.security;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private AuthenticationManager authenticationManager;
  private JwtManager jwtManager;

  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,
      JwtManager jwtManager) {
    this.authenticationManager = authenticationManager;
    this.jwtManager = jwtManager;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginView> login(@RequestBody @Valid LoginForm form) {
    var authToken = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());

    try {
      var authentication = authenticationManager.authenticate(authToken);

      var principal = (User) authentication.getPrincipal();
      var token = jwtManager.generateToken(principal);

      return ResponseEntity.ok(new LoginView(token));
    } catch (AuthenticationException e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
