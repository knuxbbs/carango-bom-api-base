package br.com.caelum.carangobom.marca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import br.com.caelum.carangobom.domain.Marca;
import br.com.caelum.carangobom.domain.MarcaCadastradaAnteriormenteException;
import br.com.caelum.carangobom.repositories.MarcaRepository;
import br.com.caelum.carangobom.services.MarcaFacade;
import br.com.caelum.carangobom.viewmodels.MarcaForm;

class MarcaFacadeTest {

  private MarcaFacade marcaFacade;

  @Mock
  private MarcaRepository marcaRepository;

  private static final String NOME_DEFAULT = "Ferrari";

  @BeforeEach
  public void configuraMock() {
    openMocks(this);

    marcaFacade = new MarcaFacade(marcaRepository);
  }

  @Test
  void deveRetornarMarcaPeloId() {
    Marca audi = new Marca(NOME_DEFAULT);

    when(marcaRepository.findById(1L)).thenReturn(Optional.of(audi));

    Optional<Marca> marcaRecuperada = marcaFacade.recuperar(1L);

    assertEquals(audi, marcaRecuperada.get());
    verify(marcaRepository).findById(1L);
  }

  @Test
  void deveCadastrarMarca() {
    var form = new MarcaForm();
    form.setNome(NOME_DEFAULT);

    var novaMarca = new Marca(form.getNome());

    when(marcaRepository.save(novaMarca)).thenReturn(novaMarca);

    var marcaCadastrada = marcaFacade.cadastrar(form);

    assertEquals(novaMarca.getNome(), marcaCadastrada.getNome());
    verify(marcaRepository).save(novaMarca);
  }

  @Test
  void naoDeveCadastrarMarcaDuplicada() {
    var form = new MarcaForm();
    form.setNome(NOME_DEFAULT);

    var novaMarca = new Marca(form.getNome());

    when(marcaRepository.findByNome(NOME_DEFAULT)).thenReturn(Optional.of(novaMarca));

    assertThrows(MarcaCadastradaAnteriormenteException.class, () -> marcaFacade.cadastrar(form));
  }

  @Test
  void naoDeveAlterarMarcaInexistente() {
    var form = new MarcaForm();
    form.setNome(NOME_DEFAULT);

    when(marcaRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> marcaFacade.alterar(1L, form));
  }

  @Test
  void naoDeveDeletarMarcaInexistente() {
    when(marcaRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> marcaFacade.deletar(1L));
  }

}
