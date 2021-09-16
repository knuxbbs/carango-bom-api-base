package br.com.caelum.carangobom.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import br.com.caelum.carangobom.Veiculo;

public interface VeiculoRepository extends Repository<Veiculo, Long> {

  List<Veiculo> findAll();

  Optional<Veiculo> findById(Long id);

  Veiculo save(Veiculo veiculo);

  void deleteById(Long id);

}
