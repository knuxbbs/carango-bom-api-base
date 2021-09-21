package br.com.caelum.carangobom.viewmodels;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VeiculoForm {

  private Long marcaId;

  @NotBlank
  @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
  private String modelo;

  private int ano;

  private BigDecimal valor;

  public Long getMarcaId() {
    return marcaId;
  }

  public void setMarcaId(Long marcaId) {
    this.marcaId = marcaId;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public int getAno() {
    return ano;
  }

  public void setAno(int ano) {
    this.ano = ano;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

}
