package br.com.caelum.carangobom.veiculo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.caelum.carangobom.marca.MarcaNaoEncontradaException;
import br.com.caelum.carangobom.marca.MarcaRepository;

@Service
public class VeiculoFacade {
  private VeiculoRepository veiculoRepository;
  private MarcaRepository marcaRepository;

  @Autowired
  public VeiculoFacade(VeiculoRepository veiculoRepository, MarcaRepository marcaRepository) {
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
      throw new MarcaNaoEncontradaException();
    }

    var veiculo = new Veiculo(form.getModelo(), form.getAno(), marca.get(), form.getValor());

    veiculoRepository.save(veiculo);

    return new VeiculoView(veiculo);
  }

  public VeiculoView alterar(Long id, VeiculoForm form) {
    var veiculoOpt = veiculoRepository.findById(id);

    if (veiculoOpt.isEmpty()) {
      throw new EntityNotFoundException("Veículo não encontrado");
    }

    var marca = marcaRepository.findById(form.getMarcaId());

    if (marca.isEmpty()) {
      throw new MarcaNaoEncontradaException();
    }

    var veiculo = veiculoOpt.get();
    veiculo.atualizar(form.getModelo(), form.getAno(), marca.get(), form.getValor());

    return new VeiculoView(veiculo);
  }

  public void deletar(Long id) {
    veiculoRepository.deleteById(id);
  }
}
