package br.com.caelum.carangobom.marca;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import br.com.caelum.carangobom.erro.Erro;

public class MarcaExceptionHandler {

    @ExceptionHandler(MarcaNaoEncontradaException.class)
    public ResponseEntity<Erro> handleMarcaNaoEncontradaExcpecption(MarcaNaoEncontradaException e) {
        Erro error = new Erro(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

}
