package br.com.caelum.carangobom.marca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.caelum.carangobom.domain.Marca;
import br.com.caelum.carangobom.repositories.MarcaRepository;
import br.com.caelum.carangobom.services.MarcaFacade;
import br.com.caelum.carangobom.webapi.controllers.MarcaController;

class MarcaControllerTest {

  private MarcaController marcaController;
  private UriComponentsBuilder uriBuilder;

  @Mock
  private MarcaFacade marcaFacade;

  @Mock
  private MarcaRepository marcaRepository;

  @BeforeEach
  public void configuraMock() {
    openMocks(this);

    marcaController = new MarcaController(marcaFacade);
    uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
  }

  @Test
  void deveRetornarListaQuandoHouverResultados() {
    List<Marca> marcas = List.of(new Marca("Audi"), new Marca("BMW"), new Marca("Fiat"));

    when(marcaFacade.listarOrdenadoPorNome()).thenReturn(marcas);

    List<Marca> resultado = marcaController.listarOrdenadoPorNome();
    assertEquals(marcas, resultado);
  }

  @Test
  void deveRetornarMarcaPeloId() {
    Marca audi = new Marca("Audi");

    when(marcaFacade.recuperar(1L)).thenReturn(Optional.of(audi));

    ResponseEntity<Marca> resposta = marcaController.recuperarPorId(1L);
    assertEquals(audi, resposta.getBody());
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
  }

  @Test
  void deveRetornarNotFoundQuandoRecuperarMarcaComIdInexistente() {

    ResponseEntity<Marca> resposta = marcaController.recuperarPorId(1L);
    assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
  }

  @Test
  void deveResponderCreatedELocationQuandoCadastrarMarca() {
    Marca nova = new Marca("Ferrari");

    when(marcaFacade.cadastrar(nova)).thenReturn(nova);

    ResponseEntity<Marca> resposta = marcaController.cadastrar(nova, uriBuilder);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertEquals("http://localhost:8080/marcas/0", resposta.getHeaders().getLocation().toString());
  }

  @Test
  void deveAlterarNomeQuandoMarcaExistir() {
    Marca audi = new Marca("Audi");
    Marca novoAudi = new Marca("NOVA Audi");

    when(marcaFacade.alterar(1L, audi)).thenReturn(novoAudi);

    ResponseEntity<Marca> resposta = marcaController.alterar(1L, audi);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());

    Marca marcaAlterada = resposta.getBody();
    assertEquals("NOVA Audi", marcaAlterada.getNome());
  }

  @Test
  void deveDeletarMarcaExistente() {
    Marca audi = new Marca("Audi");

    when(marcaFacade.deletar(1L)).thenReturn(audi);

    ResponseEntity<?> resposta = marcaController.deletar(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    verify(marcaFacade).deletar(1L);
  }
}
