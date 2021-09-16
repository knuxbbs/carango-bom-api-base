package br.com.caelum.carangobom;

public class MarcaCadastradaAnteriormenteException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public MarcaCadastradaAnteriormenteException() {
    super("Marca já cadastrada anteriormente");
  }

}
