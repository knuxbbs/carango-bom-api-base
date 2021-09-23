package br.com.caelum.carangobom.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import br.com.caelum.carangobom.domain.Marca;

public interface MarcaRepository extends Repository<Marca, Long> {

  public List<Marca> findAllByOrderByNome();

  public Optional<Marca> findById(long id);

  public Optional<Marca> findByNome(String nome);

  Marca save(Marca marca);

  @Modifying
  @Query(nativeQuery = true, value = "DELETE FROM Marca WHERE ID = ?1")
  void deleteById(long id);

}
