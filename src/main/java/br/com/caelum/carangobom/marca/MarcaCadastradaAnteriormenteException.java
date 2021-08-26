package br.com.caelum.carangobom.marca;

public class MarcaCadastradaAnteriormenteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MarcaCadastradaAnteriormenteException() {
        super("Marca já cadastrada anteriormente");
    }
}
