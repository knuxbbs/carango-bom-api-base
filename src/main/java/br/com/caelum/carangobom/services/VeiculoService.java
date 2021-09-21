package br.com.caelum.carangobom.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.caelum.carangobom.domain.Veiculo;
import br.com.caelum.carangobom.repositories.MarcaRepository;
import br.com.caelum.carangobom.repositories.VeiculoRepository;
import br.com.caelum.carangobom.viewmodels.VeiculoForm;
import br.com.caelum.carangobom.viewmodels.VeiculoView;

@Service
public class VeiculoService {

  private VeiculoRepository veiculoRepository;
  private MarcaRepository marcaRepository;

  @Autowired
  public VeiculoService(VeiculoRepository veiculoRepository, MarcaRepository marcaRepository) {
    this.veiculoRepository = veiculoRepository;
    this.marcaRepository = marcaRepository;
  }

  public List<VeiculoView> listar() {
    var veiculos = veiculoRepository.findAll();

    return veiculos.stream().map(VeiculoView::new).collect(Collectors.toList());
  }

  public Optional<VeiculoView> recuperar(Long id) {
    var veiculo = veiculoRepository.findById(id);

    if (veiculo.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(new VeiculoView(veiculo.get()));
  }

  public VeiculoView cadastrar(VeiculoForm form) {
    var marca = marcaRepository.findById(form.getMarcaId());

    if (marca.isEmpty()) {
      throw new EntityNotFoundException("Marca não encontrada: " + form.getMarcaId());
    }

    var veiculo = new Veiculo(form.getModelo(), form.getAno(), marca.get(), form.getValor());

    veiculoRepository.save(veiculo);

    return new VeiculoView(veiculo);
  }

  public VeiculoView alterar(Long id, VeiculoForm form) {
    var veiculoOpt = veiculoRepository.findById(id);

    if (veiculoOpt.isEmpty()) {
      throw new EntityNotFoundException("Veículo não encontrado: " + id);
    }

    var marca = marcaRepository.findById(form.getMarcaId());

    if (marca.isEmpty()) {
      throw new EntityNotFoundException("Marca não encontrada: " + form.getMarcaId());
    }

    var veiculo = veiculoOpt.get();
    veiculo.atualizar(form.getModelo(), form.getAno(), marca.get(), form.getValor());

    return new VeiculoView(veiculo);
  }

  public void deletar(Long id) {
    veiculoRepository.deleteById(id);
  }

}
