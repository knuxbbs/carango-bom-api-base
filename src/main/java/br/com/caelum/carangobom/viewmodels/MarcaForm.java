package br.com.caelum.carangobom.viewmodels;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MarcaForm {

  @NotBlank
  @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
  private String nome;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

}
