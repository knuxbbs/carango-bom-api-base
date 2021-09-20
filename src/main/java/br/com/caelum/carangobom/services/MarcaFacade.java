package br.com.caelum.carangobom.services;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.caelum.carangobom.domain.Marca;
import br.com.caelum.carangobom.domain.MarcaCadastradaAnteriormenteException;
import br.com.caelum.carangobom.repositories.MarcaRepository;
import br.com.caelum.carangobom.viewmodels.MarcaForm;

@Service
public class MarcaFacade {

  private MarcaRepository repository;

  @Autowired
  public MarcaFacade(MarcaRepository repository) {
    this.repository = repository;
  }

  public List<Marca> listarOrdenadoPorNome() {
    return repository.findAllByOrderByNome();
  }

  public Optional<Marca> recuperar(Long id) {
    return repository.findById(id);
  }

  public Marca cadastrar(MarcaForm form) {
    var marcaOpt = repository.findByNome(form.getNome());

    if (marcaOpt.isPresent()) {
      throw new MarcaCadastradaAnteriormenteException();
    }

    var marca = new Marca(form.getNome());

    return repository.save(marca);
  }

  public Marca alterar(Long id, MarcaForm form) {
    var marcaOpt = repository.findById(id);

    if (marcaOpt.isEmpty()) {
      throw new EntityNotFoundException("Marca não encontrada: " + id);
    }

    var marca = marcaOpt.get();
    marca.setNome(form.getNome());

    return repository.save(marca);
  }

  public Marca deletar(Long id) {
    var marcaOpt = repository.findById(id);

    if (!marcaOpt.isPresent()) {
      throw new EntityNotFoundException("Marca não encontrada: " + id);
    }

    repository.deleteById(id);

    return marcaOpt.get();
  }

}
