package br.com.caelum.carangobom.veiculo;

import java.math.BigDecimal;
import br.com.caelum.carangobom.marca.MarcaView;

public class VeiculoView {
  private Long id;
  private String modelo;
  private String ano;
  private BigDecimal valor;
  private MarcaView marca;

  public VeiculoView(Veiculo veiculo) {
    id = veiculo.getId();
    modelo = veiculo.getModelo();
    ano = veiculo.getAno();
    valor = veiculo.getValor();
    marca = new MarcaView(veiculo.getMarca());
  }

  public Long getId() {
    return id;
  }

  public String getModelo() {
    return modelo;
  }

  public String getAno() {
    return ano;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public MarcaView getMarca() {
    return marca;
  }
}
