package br.com.caelum.carangobom.domain;

import static javax.persistence.GenerationType.IDENTITY;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Veiculo {

  private static final int MAX_YEAR = Calendar.getInstance().get(Calendar.YEAR);

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
  private String modelo;

  @Min(1886)
  // @Max(MAX_YEAR)
  private int ano;

  private BigDecimal valor;

  @ManyToOne
  private Marca marca;

  protected Veiculo() {}

  public Veiculo(String modelo, int ano, Marca marca, BigDecimal valor) {
    this.marca = marca;
    this.modelo = modelo;
    this.ano = ano;
    this.valor = valor;
  }

  public Long getId() {
    if (id == null) {
      return 0L;
    }

    return id;
  }

  public String getModelo() {
    return modelo;
  }

  public int getAno() {
    return ano;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public Marca getMarca() {
    return marca;
  }

  public void atualizar(String modelo, int ano, Marca marca, BigDecimal valor) {
    this.marca = marca;
    this.modelo = modelo;
    this.ano = ano;
    this.valor = valor;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ano, id, marca, modelo, valor);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Veiculo other = (Veiculo) obj;
    return Objects.equals(ano, other.ano) && Objects.equals(id, other.id)
        && Objects.equals(marca, other.marca) && Objects.equals(modelo, other.modelo)
        && Objects.equals(valor, other.valor);
  }

}
