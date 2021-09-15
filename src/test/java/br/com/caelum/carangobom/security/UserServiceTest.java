package br.com.caelum.carangobom.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserServiceTest {

  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @BeforeEach
  public void configuraMock() {
    openMocks(this);

    userService = new UserService(userRepository);
  }

  @Test
  void deveRetornarUsuarioPeloNome() {
    var user = new User("john.doe@email.com", "123");

    when(userRepository.findByEmail(user.getUsername())).thenReturn(Optional.of(user));

    var userDetails = userService.loadUserByUsername(user.getUsername());

    assertEquals(user.getUsername(), userDetails.getUsername());
  }

  @Test
  void deveLancarExcecaoSeNaoEncontrarUsuarioPeloNome() {
    String email = "fulano@email.com";

    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
  }

}
