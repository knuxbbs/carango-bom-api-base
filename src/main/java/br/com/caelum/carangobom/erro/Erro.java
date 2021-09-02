package br.com.caelum.carangobom.erro;

import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Erro {
    private final HttpStatus httpStatus;
    private final String message;
}
