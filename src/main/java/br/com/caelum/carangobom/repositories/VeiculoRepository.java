package br.com.caelum.carangobom.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import br.com.caelum.carangobom.domain.Veiculo;

public interface VeiculoRepository extends Repository<Veiculo, Long> {

  List<Veiculo> findAll();

  Optional<Veiculo> findById(long id);

  Veiculo save(Veiculo veiculo);

  @Modifying
  @Query(nativeQuery = true, value = "DELETE FROM Veiculo WHERE ID = ?1")
  void deleteById(long id);

}
