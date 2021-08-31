package br.com.caelum.carangobom.marca;

public class MarcaNaoEncontradaException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public MarcaNaoEncontradaException() {
    super("Marca não encontrada");
  }
}
