package br.com.caelum.carangobom.webapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.caelum.carangobom.domain.MarcaCadastradaAnteriormenteException;

@RestControllerAdvice
public class MarcaExceptionHandler {

  @ExceptionHandler(MarcaCadastradaAnteriormenteException.class)
  public ResponseEntity<Erro> handle(MarcaCadastradaAnteriormenteException e) {
    Erro error = new Erro(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
    return new ResponseEntity<>(error, error.getHttpStatus());
  }

}
