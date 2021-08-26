package br.com.caelum.carangobom.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroValidacaoHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ListaDeErrosOutputDto validacao(MethodArgumentNotValidException excecao) {
        List<ErroDeParametroOutputDto> errosParametroOutputDto = new ArrayList<>();
        
        excecao.getBindingResult().getFieldErrors().forEach(e -> {
            ErroDeParametroOutputDto erroParametroOutput = new ErroDeParametroOutputDto();
            erroParametroOutput.setParametro(e.getField());
            erroParametroOutput.setMensagem(e.getDefaultMessage());
            errosParametroOutputDto.add(erroParametroOutput);
        });
        
        ListaDeErrosOutputDto errosOutputDto = new ListaDeErrosOutputDto();
        
        errosOutputDto.setErros(errosParametroOutputDto);
        
        return errosOutputDto;
    }
    
}
