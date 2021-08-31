package br.com.caelum.carangobom.marca;

public class MarcaView {
  private Long id;
  private String nome;

  public MarcaView(Marca marca) {
    id = marca.getId();
    nome = marca.getNome();
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }
}
