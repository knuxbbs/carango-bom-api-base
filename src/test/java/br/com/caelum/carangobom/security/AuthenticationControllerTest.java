package br.com.caelum.carangobom.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class AuthenticationControllerTest {

  private AuthenticationController authenticationController;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtManager jwtManager;

  @BeforeEach
  public void configuraMock() {
    openMocks(this);

    authenticationController = new AuthenticationController(authenticationManager, jwtManager);
  }

  @Test
  void deveGerarTokenQuandoAutenticarComCredenciaisCorretas() {
    var form = new LoginForm();
    form.setEmail("john.doe@email.com");
    form.setPassword("123");

    var user = new User(form.getEmail(), form.getPassword());

    when(authenticationManager.authenticate(any(Authentication.class)))
        .thenReturn(new UsernamePasswordAuthenticationToken(user, form.getPassword()));
    when(jwtManager.generateToken(any(User.class))).thenReturn("FAKE_TOKEN");

    var response = authenticationController.login(form);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    var view = response.getBody();
    assertEquals("FAKE_TOKEN", view.getToken());
  }

  @Test
  void deveRetornarBadRequestAoFalharAutenticacao() {
    var form = new LoginForm();
    form.setEmail("john.doe@email.com");
    form.setPassword("123");

    when(authenticationManager.authenticate(any(Authentication.class)))
        .thenThrow(new BadCredentialsException("Credenciais inválidas."));

    var response = authenticationController.login(form);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    verifyNoInteractions(jwtManager);
  }

}
