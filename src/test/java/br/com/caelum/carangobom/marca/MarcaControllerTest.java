package br.com.caelum.carangobom.marca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
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
        List<Marca> marcas =
                List.of(new Marca(1L, "Audi"), new Marca(2L, "BMW"), new Marca(3L, "Fiat"));

        when(marcaFacade.listarOrdenadoPorNome()).thenReturn(marcas);

        List<Marca> resultado = marcaController.listarOrdenadoPorNome();
        assertEquals(marcas, resultado);
    }

    @Test
    void deveRetornarMarcaPeloId() {
        Marca audi = new Marca(1L, "Audi");

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

        when(marcaFacade.cadastrar(nova)).then(invocation -> {
            Marca marcaSalva = invocation.getArgument(0, Marca.class);
            marcaSalva.setId(1L);

            return marcaSalva;
        });

        ResponseEntity<Marca> resposta = marcaController.cadastrar(nova, uriBuilder);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals("http://localhost:8080/marcas/1",
                resposta.getHeaders().getLocation().toString());
    }

    @Test
    void deveAlterarNomeQuandoMarcaExistir() {
        Marca audi = new Marca(1L, "Audi");
        Marca novoAudi = new Marca(1L, "NOVA Audi");

        when(marcaFacade.alterar(1L, audi)).thenReturn(novoAudi);

        ResponseEntity<Marca> resposta = marcaController.alterar(1L, audi);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        Marca marcaAlterada = resposta.getBody();
        assertEquals("NOVA Audi", marcaAlterada.getNome());
    }

    @Test
    void naoDeveAlterarMarcaInexistente() {
        when(marcaFacade.alterar(anyLong(), any())).thenThrow(MarcaNaoEncontradaException.class);

        ResponseEntity<Marca> resposta = marcaController.alterar(1L, new Marca(1L, "NOVA Audi"));
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveDeletarMarcaExistente() {
        Marca audi = new Marca(1l, "Audi");

        when(marcaFacade.deletar(1L)).thenReturn(audi);

        ResponseEntity<?> resposta = marcaController.deletar(1L);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        verify(marcaFacade).deletar(1L);
    }

    @Test
    void naoDeveDeletarMarcaInexistente() {
        when(marcaFacade.deletar(anyLong())).thenThrow(MarcaNaoEncontradaException.class);

        ResponseEntity<?> resposta = marcaController.deletar(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());

        verify(marcaRepository, never()).deleteById(any());
    }

}
