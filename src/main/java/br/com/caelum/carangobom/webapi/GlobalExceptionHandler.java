package br.com.caelum.carangobom.webapi;

import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.caelum.carangobom.viewmodels.ErrorView;
import br.com.caelum.carangobom.viewmodels.FieldErrorListView;
import br.com.caelum.carangobom.viewmodels.FieldErrorView;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  FieldErrorListView handle(MethodArgumentNotValidException exception) {
    var views = exception.getBindingResult().getFieldErrors().stream()
        .map(x -> new FieldErrorView(x.getField(), x.getDefaultMessage()))
        .collect(Collectors.toList());

    var listView = new FieldErrorListView();
    listView.setErrors(views);

    return listView;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(EntityNotFoundException.class)
  ErrorView handle(EntityNotFoundException exception) {
    return new ErrorView(exception.getMessage());
  }

}
