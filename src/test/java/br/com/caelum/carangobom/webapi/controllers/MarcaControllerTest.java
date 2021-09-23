package br.com.caelum.carangobom.webapi.controllers;

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
import br.com.caelum.carangobom.services.MarcaService;
import br.com.caelum.carangobom.viewmodels.MarcaForm;

class MarcaControllerTest {

  private MarcaController marcaController;
  private UriComponentsBuilder uriBuilder;

  @Mock
  private MarcaService marcaFacade;

  @Mock
  private MarcaRepository marcaRepository;

  private static final String NOME_DEFAULT = "Audi";

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
    Marca audi = new Marca(NOME_DEFAULT);

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
    var form = new MarcaForm();
    form.setNome(NOME_DEFAULT);

    Marca nova = new Marca(form.getNome());

    when(marcaFacade.cadastrar(form)).thenReturn(nova);

    ResponseEntity<Marca> resposta = marcaController.cadastrar(form, uriBuilder);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertEquals("http://localhost:8080/marcas/0", resposta.getHeaders().getLocation().toString());
  }

  @Test
  void deveAlterarNomeQuandoMarcaExistir() {
    var form = new MarcaForm();
    form.setNome(NOME_DEFAULT);

    Marca marca = new Marca(form.getNome());

    when(marcaFacade.alterar(1L, form)).thenReturn(marca);

    ResponseEntity<Marca> resposta = marcaController.alterar(1L, form);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());

    Marca marcaAlterada = resposta.getBody();
    assertEquals(NOME_DEFAULT, marcaAlterada.getNome());
  }

  @Test
  void deveDeletarMarcaExistente() {
    marcaController.deletar(1L);

    verify(marcaFacade).deletar(1L);
  }
}
