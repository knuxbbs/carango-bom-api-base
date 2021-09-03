package br.com.caelum.carangobom.marca;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.caelum.carangobom.erro.Erro;

@RestControllerAdvice
public class MarcaExceptionHandler {

  @ExceptionHandler(MarcaNaoEncontradaException.class)
  public ResponseEntity<Erro> handle(MarcaNaoEncontradaException e) {
    Erro error = new Erro(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    return new ResponseEntity<>(error, error.getHttpStatus());
  }

  @ExceptionHandler(MarcaCadastradaAnteriormenteException.class)
  public ResponseEntity<Erro> handle(MarcaCadastradaAnteriormenteException e) {
    Erro error = new Erro(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
    return new ResponseEntity<>(error, error.getHttpStatus());
  }

}
