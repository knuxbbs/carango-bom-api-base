package br.com.caelum.carangobom.domain;

import javax.persistence.EntityNotFoundException;

public class MarcaNaoEncontradaException extends EntityNotFoundException {

  private static final long serialVersionUID = 2402240702720294789L;

  public MarcaNaoEncontradaException(long id) {
    super("Marca não encontrada: " + id);
  }

}
