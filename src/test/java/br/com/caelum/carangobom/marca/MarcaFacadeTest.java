package br.com.caelum.carangobom.marca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class MarcaFacadeTest {

    private MarcaFacade marcaFacade;

    @Mock
    private MarcaRepository marcaRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);

        marcaFacade = new MarcaFacade(marcaRepository);
    }

    @Test
    void deveRetornarMarcaPeloId() {
        Marca audi = new Marca(1L, "Audi");

        when(marcaRepository.findById(1L)).thenReturn(Optional.of(audi));

        Optional<Marca> marcaRecuperada = marcaFacade.recuperar(1L);

        assertEquals(audi, marcaRecuperada.get());
        verify(marcaRepository).findById(1L);
    }

    @Test
    void deveCadastrarMarca() {
        Marca novaMarca = new Marca("Ferrari");

        when(marcaRepository.save(novaMarca)).thenReturn(new Marca(1L, "Ferrari"));

        var marcaCadastrada = marcaFacade.cadastrar(novaMarca);

        assertEquals(novaMarca.getNome(), marcaCadastrada.getNome());
        verify(marcaRepository).save(novaMarca);
    }

    @Test
    void naoDeveCadastrarMarcaDuplicada() {
        Marca novaMarca = new Marca("Ferrari");

        when(marcaRepository.findByNome("Ferrari")).thenReturn(Optional.of(novaMarca));

        assertThrows(MarcaCadastradaAnteriormenteException.class,
                () -> marcaFacade.cadastrar(novaMarca));
    }

    @Test
    void naoDeveAlterarMarcaInexistente() {
        Marca nova = new Marca("Ferrari");

        when(marcaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MarcaNaoEncontradaException.class, () -> marcaFacade.alterar(1L, nova));
    }

    @Test
    void naoDeveDeletarMarcaInexistente() {
        when(marcaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MarcaNaoEncontradaException.class, () -> marcaFacade.deletar(1L));
    }
}
