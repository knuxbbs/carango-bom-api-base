package br.com.caelum.carangobom.veiculo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.caelum.carangobom.marca.Marca;
import br.com.caelum.carangobom.marca.MarcaRepository;

class VeiculoControllerTest {

  private VeiculoController veiculoController;
  private UriComponentsBuilder uriBuilder;

  @Mock
  private VeiculoFacade veiculoFacade;

  @Mock
  private VeiculoRepository veiculoRepository;

  @Mock
  private MarcaRepository marcaRepository;

  @BeforeEach
  public void configuraMock() {
    openMocks(this);

    veiculoController = new VeiculoController(veiculoFacade);
    uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
  }

  @Test
  void deveRetornarVeiculoPeloId() {
    var marca = new Marca("Volkswagen");
    var veiculo = new Veiculo(1L, "Gol", "2021", marca, new BigDecimal("70000"));

    when(veiculoFacade.recuperar(veiculo.getId()))
        .thenReturn(Optional.of(new VeiculoView(veiculo)));

    var resposta = veiculoController.recuperarPorId(veiculo.getId());
    assertEquals(HttpStatus.OK, resposta.getStatusCode());

    var view = resposta.getBody();
    assertEquals(veiculo.getId(), view.getId());
    assertEquals(veiculo.getModelo(), view.getModelo());
    assertEquals(veiculo.getValor(), view.getValor());
  }

  @Test
  void deveResponderCreatedELocationQuandoCadastrarVeiculo() {
    var form = new VeiculoForm();
    form.setModelo("Gol");
    form.setAno("2021");
    form.setValor(new BigDecimal("70000"));

    var marca = new Marca("Volkswagen");
    var veiculo = new Veiculo(1L, form.getModelo(), form.getAno(), marca, form.getValor());

    when(veiculoFacade.cadastrar(form)).thenReturn(new VeiculoView(veiculo));
    when(marcaRepository.findById(marca.getId())).thenReturn(Optional.of(marca));

    var resposta = veiculoController.cadastrar(form, uriBuilder);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertEquals(String.format("http://localhost:8080/veiculos/%s", veiculo.getId()),
        resposta.getHeaders().getLocation().toString());

    var view = resposta.getBody();
    assertEquals(veiculo.getModelo(), view.getModelo());
    assertEquals(veiculo.getValor(), view.getValor());
  }

  @Test
  void deveAlterarVeiculoExistente() {
    var marca = new Marca("Volkswagen");
    var veiculo = new Veiculo(1L, "Gol", "2021", marca, new BigDecimal("70000"));

    var form = new VeiculoForm();
    form.setModelo("Golf");
    form.setAno("2010");
    form.setValor(new BigDecimal("75000"));

    when(veiculoRepository.findById(veiculo.getId())).thenReturn(Optional.of(veiculo));
    when(marcaRepository.findById(marca.getId())).thenReturn(Optional.of(marca));
    when(veiculoFacade.alterar(veiculo.getId(), form)).thenReturn(new VeiculoView(veiculo));

    var resposta = veiculoController.alterar(1L, form);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());

    var view = resposta.getBody();
    assertEquals(veiculo.getId(), view.getId());
    assertEquals(veiculo.getModelo(), view.getModelo());
    assertEquals(veiculo.getValor(), view.getValor());
  }

  @Test
  void deveDeletarVeiculoExistente() {
    var resposta = veiculoController.deletar(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());

    verify(veiculoFacade).deletar(1L);
  }

}
