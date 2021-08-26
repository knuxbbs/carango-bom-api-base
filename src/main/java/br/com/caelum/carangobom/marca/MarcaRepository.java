package br.com.caelum.carangobom.marca;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MarcaRepository extends Repository<Marca, Long> {

    Marca save(Marca marca);

    void deleteById(Long id);

    public Optional<Marca> findById(Long id);

    public List<Marca> findAllByOrderByNome();

    public Optional<Marca> findByNome(String nome);

}
