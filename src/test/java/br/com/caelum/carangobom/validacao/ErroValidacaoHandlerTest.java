package br.com.caelum.carangobom.validacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import br.com.caelum.carangobom.marca.Marca;

class ErroValidacaoHandlerTest {

  @Mock
  BindingResult bindingResult;

  @BeforeEach
  public void configuraMock() {
    openMocks(this);
  }

  @Test
  void deveExtrairErrosDeValidacaoDaException() {
    var fieldErrors =
        List.of(new FieldError(Marca.class.getSimpleName(), "nome", "Deve ser preenchido."),
            new FieldError(Marca.class.getSimpleName(), "nome", "Deve ter 2 ou mais caracteres."));

    when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

    var exception = new MethodArgumentNotValidException(null, bindingResult);

    var handler = new ErroValidacaoHandler();
    var listaDto = handler.validacao(exception);

    assertEquals(2, listaDto.getQuantidadeDeErros());
  }

}
