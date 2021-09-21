package br.com.caelum.carangobom.webapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.openMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import br.com.caelum.carangobom.domain.MarcaCadastradaAnteriormenteException;
import br.com.caelum.carangobom.domain.MarcaNaoEncontradaException;

class MarcaExceptionHandlerTest {

  @Mock
  BindingResult bindingResult;

  @BeforeEach
  public void configuraMock() {
    openMocks(this);
  }

  @Test
  void marcaNaoEncontradaDeveRetornarNotFound() {
    var exception = new MarcaNaoEncontradaException(1l);

    var handler = new MarcaExceptionHandler();
    var response = handler.handle(exception);

    assertEquals(HttpStatus.NOT_FOUND, response.getBody().getHttpStatus());
    assertEquals(exception.getMessage(), response.getBody().getMessage());
  }

  @Test
  void marcaCadastradaAnteriormenteDeveRetornarBadRequest() {
    var exception = new MarcaCadastradaAnteriormenteException();

    var handler = new MarcaExceptionHandler();
    var response = handler.handle(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getHttpStatus());
    assertEquals(exception.getMessage(), response.getBody().getMessage());
  }

}
