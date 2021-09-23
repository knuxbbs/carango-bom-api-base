package br.com.caelum.carangobom.webapi.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.caelum.carangobom.domain.Marca;
import br.com.caelum.carangobom.services.MarcaService;
import br.com.caelum.carangobom.viewmodels.MarcaForm;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

  private MarcaService marcaFacade;

  @Autowired
  public MarcaController(MarcaService marcaFacade) {
    this.marcaFacade = marcaFacade;
  }

  @GetMapping
  public List<Marca> listarOrdenadoPorNome() {
    return marcaFacade.listarOrdenadoPorNome();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Marca> recuperarPorId(@PathVariable Long id) {
    var marca = marcaFacade.recuperar(id);

    return ResponseEntity.of(marca);
  }

  @PostMapping
  @Transactional
  public ResponseEntity<Marca> cadastrar(@Valid @RequestBody MarcaForm form,
      UriComponentsBuilder uriBuilder) {
    var marcaCadastrada = marcaFacade.cadastrar(form);
    var h = uriBuilder.path("/marcas/{id}").buildAndExpand(marcaCadastrada.getId()).toUri();

    return ResponseEntity.created(h).body(marcaCadastrada);
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Marca> alterar(@PathVariable long id, @Valid @RequestBody MarcaForm form) {
    var marcaAlterada = marcaFacade.alterar(id, form);

    return ResponseEntity.ok(marcaAlterada);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Marca> deletar(@PathVariable long id) {
    var marca = marcaFacade.deletar(id);

    return ResponseEntity.ok(marca);
  }

}
