package br.com.caelum.carangobom.marca;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    public Optional<Marca> findById(Long id);

    public List<Marca> findAllByOrderByNome();

}
