package br.com.caelum.carangobom.viewmodels;

import br.com.caelum.carangobom.domain.Marca;

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
