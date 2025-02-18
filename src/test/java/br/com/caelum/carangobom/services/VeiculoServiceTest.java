package br.com.caelum.carangobom.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import br.com.caelum.carangobom.domain.Marca;
import br.com.caelum.carangobom.domain.Veiculo;
import br.com.caelum.carangobom.repositories.MarcaRepository;
import br.com.caelum.carangobom.repositories.VeiculoRepository;
import br.com.caelum.carangobom.viewmodels.VeiculoForm;

class VeiculoServiceTest {

  private VeiculoService veiculoFacade;

  @Mock
  private VeiculoRepository veiculoRepository;

  @Mock
  private MarcaRepository marcaRepository;

  private static final Long DEFAULT_ID = 0L;
  private static final Marca MARCA_DEFAULT = new Marca("Audi");
  private static final Veiculo VEICULO_DEFAULT =
      new Veiculo("A4", Year.of(2000), MARCA_DEFAULT, new BigDecimal("20000"));

  @BeforeEach
  public void configuraMock() {
    openMocks(this);

    veiculoFacade = new VeiculoService(veiculoRepository, marcaRepository);
  }

  @Test
  void deveRetornarVeiculoPeloId() {
    when(veiculoRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(VEICULO_DEFAULT));

    var veiculoView = veiculoFacade.recuperar(DEFAULT_ID).get();

    assertEquals(VEICULO_DEFAULT.getId(), veiculoView.getId());
    assertEquals(VEICULO_DEFAULT.getModelo(), veiculoView.getModelo());
    assertEquals(VEICULO_DEFAULT.getValor(), veiculoView.getValor());

    var marca = VEICULO_DEFAULT.getMarca();
    var marcaView = veiculoView.getMarca();

    assertEquals(marca.getId(), marcaView.getId());
    assertEquals(marca.getNome(), marcaView.getNome());

    verify(veiculoRepository).findById(DEFAULT_ID);
  }

  @Test
  void deveCadastrarVeiculo() {
    var marca = new Marca("Fiat");
    var veiculo = new Veiculo("Gol", Year.of(2021), marca, new BigDecimal("70000"));

    when(marcaRepository.findById(marca.getId())).thenReturn(Optional.of(marca));
    when(veiculoRepository.save(veiculo)).thenReturn(veiculo);

    var form = new VeiculoForm();
    form.setMarcaId(marca.getId());
    form.setModelo(veiculo.getModelo());
    form.setAno(veiculo.getAno());
    form.setValor(veiculo.getValor());

    var veiculoView = veiculoFacade.cadastrar(form);

    assertEquals(veiculo.getModelo(), veiculoView.getModelo());
    assertEquals(veiculo.getValor(), veiculoView.getValor());

    verify(veiculoRepository).save(veiculo);
  }

  @Test
  void naoDeveAlterarVeiculoInexistente() {
    var marca = new Marca("Volkswagen");
    var veiculo = new Veiculo("Gol", Year.of(2021), marca, new BigDecimal("70000"));

    when(veiculoRepository.findById(anyLong())).thenReturn(Optional.empty());

    var form = new VeiculoForm();
    form.setMarcaId(marca.getId());
    form.setModelo(veiculo.getModelo());
    form.setAno(veiculo.getAno());
    form.setValor(veiculo.getValor());

    assertThrows(EntityNotFoundException.class, () -> veiculoFacade.alterar(1L, form));
  }

}
