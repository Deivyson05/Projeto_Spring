package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Meta;
import br.com.yomu.gamificacaoDaLeitura.service.MetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
public class MetaController {

    private final MetaService metaService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<Meta> criar(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody Meta meta) {
        Meta metaCriada = metaService.criar(usuarioId, meta);
        return ResponseEntity.status(HttpStatus.CREATED).body(metaCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meta> buscarPorId(@PathVariable UUID id) {
        Meta meta = metaService.buscarPorId(id);
        return ResponseEntity.ok(meta);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Meta>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<Meta> metas = metaService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/usuario/{usuarioId}/ativas")
    public ResponseEntity<List<Meta>> listarMetasAtivas(@PathVariable UUID usuarioId) {
        List<Meta> metas = metaService.listarMetasAtivas(usuarioId);
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/usuario/{usuarioId}/concluidas")
    public ResponseEntity<List<Meta>> listarMetasConcluidas(@PathVariable UUID usuarioId) {
        List<Meta> metas = metaService.listarMetasConcluidas(usuarioId);
        return ResponseEntity.ok(metas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        metaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
