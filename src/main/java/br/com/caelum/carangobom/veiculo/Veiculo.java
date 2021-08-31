package br.com.caelum.carangobom.veiculo;

import static javax.persistence.GenerationType.IDENTITY;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import br.com.caelum.carangobom.marca.Marca;

@Entity
public class Veiculo {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
  private String modelo;

  @NotBlank
  @Size(min = 4, max = 4, message = "Deve ter {min} ou mais caracteres.")
  private String ano;

  private BigDecimal valor;

  @ManyToOne
  private Marca marca;

  protected Veiculo() {}

  protected Veiculo(Long id, String modelo, String ano, Marca marca, BigDecimal valor) {
    this.id = id;
    this.marca = marca;
    this.modelo = modelo;
    this.ano = ano;
    this.valor = valor;
  }

  public Veiculo(String modelo, String ano, Marca marca, BigDecimal valor) {
    this(null, modelo, ano, marca, valor);
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

  public Marca getMarca() {
    return marca;
  }

  public void atualizar(String modelo, String ano, Marca marca, BigDecimal valor) {
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
