package br.com.caelum.carangobom;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Marca {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
  private String nome;

  protected Marca() {}

  public Marca(String nome) {
    setNome(nome);
  }

  public Long getId() {
    if (id == null) {
      return 0L;
    }

    return id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Marca other = (Marca) obj;
    return Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
  }

}
