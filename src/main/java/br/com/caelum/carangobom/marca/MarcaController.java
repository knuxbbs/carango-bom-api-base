package br.com.caelum.carangobom.marca;

import java.net.URI;
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

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private MarcaFacade marcaFacade;

    @Autowired
    public MarcaController(MarcaFacade marcaFacade) {
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
    public ResponseEntity<Marca> cadastrar(@Valid @RequestBody Marca novaMarca,
            UriComponentsBuilder uriBuilder) {
        try {
            var marcaCadastrada = marcaFacade.cadastrar(novaMarca);
            URI h = uriBuilder.path("/marcas/{id}").buildAndExpand(marcaCadastrada.getId()).toUri();
            return ResponseEntity.created(h).body(marcaCadastrada);

        } catch (MarcaNaoEncontradaException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Marca> alterar(@PathVariable Long id,
            @Valid @RequestBody Marca dadosAltercaoMarca) {

        try {
            var marcaAlterada = marcaFacade.alterar(id, dadosAltercaoMarca);
            return ResponseEntity.ok(marcaAlterada);
        } catch (MarcaNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Marca> deletar(@PathVariable Long id) {
        try {
            var marca = marcaFacade.deletar(id);

            return ResponseEntity.ok(marca);

        } catch (MarcaNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
