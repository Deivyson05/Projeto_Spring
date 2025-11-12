package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Livro;
import br.com.yomu.gamificacaoDaLeitura.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<Livro> criar(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody Livro livro) {
        Livro livroCriado = livroService.criar(usuarioId, livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable UUID id) {
        Livro livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Livro>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<Livro> livros = livroService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/usuario/{usuarioId}/finalizado/{finalizado}")
    public ResponseEntity<List<Livro>> listarPorStatus(
            @PathVariable UUID usuarioId,
            @PathVariable Boolean finalizado) {
        List<Livro> livros = livroService.listarPorUsuarioEStatus(usuarioId, finalizado);
        return ResponseEntity.ok(livros);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(
            @PathVariable UUID id,
            @RequestBody Livro livro) {
        Livro livroAtualizado = livroService.atualizar(id, livro);
        return ResponseEntity.ok(livroAtualizado);
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Void> marcarComoFinalizado(@PathVariable UUID id) {
        livroService.marcarComoFinalizado(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}