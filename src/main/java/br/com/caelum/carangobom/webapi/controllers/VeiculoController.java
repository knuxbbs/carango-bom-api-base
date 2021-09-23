package br.com.caelum.carangobom.webapi.controllers;

import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.caelum.carangobom.services.VeiculoService;
import br.com.caelum.carangobom.viewmodels.VeiculoForm;
import br.com.caelum.carangobom.viewmodels.VeiculoView;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

  private VeiculoService veiculoFacade;

  @Autowired
  public VeiculoController(VeiculoService veiculoFacade) {
    this.veiculoFacade = veiculoFacade;
  }

  @GetMapping
  public List<VeiculoView> listar() {
    return veiculoFacade.listar();
  }

  @GetMapping("/{id}")
  public ResponseEntity<VeiculoView> recuperarPorId(@PathVariable long id) {
    var view = veiculoFacade.recuperar(id);

    return ResponseEntity.of(view);
  }

  @PostMapping
  @Transactional
  public ResponseEntity<VeiculoView> cadastrar(@RequestBody @Valid VeiculoForm form,
      UriComponentsBuilder uriComponentsBuilder) {
    var view = veiculoFacade.cadastrar(form);

    var uri = uriComponentsBuilder.path("/veiculos/{id}").buildAndExpand(view.getId()).toUri();

    return ResponseEntity.created(uri).body(view);
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<VeiculoView> alterar(@PathVariable long id,
      @RequestBody @Valid VeiculoForm form) {
    VeiculoView view = veiculoFacade.alterar(id, form);

    return ResponseEntity.ok(view);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Object> deletar(@PathVariable long id) {
    veiculoFacade.deletar(id);

    return ResponseEntity.ok(new Object());
  }

}
