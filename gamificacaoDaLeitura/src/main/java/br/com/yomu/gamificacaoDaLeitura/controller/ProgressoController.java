package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Progresso;
import br.com.yomu.gamificacaoDaLeitura.service.ProgressoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/progressos")
@RequiredArgsConstructor
public class ProgressoController {

    private final ProgressoService progressoService;

    @PostMapping("/usuario/{usuarioId}/livro/{livroId}")
    public ResponseEntity<Progresso> registrar(
            @PathVariable UUID usuarioId,
            @PathVariable UUID livroId,
            @Valid @RequestBody Progresso progresso) {
        Progresso progressoRegistrado = progressoService.registrar(usuarioId, livroId, progresso);
        return ResponseEntity.status(HttpStatus.CREATED).body(progressoRegistrado);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Progresso>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<Progresso> progressos = progressoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(progressos);
    }

    @GetMapping("/livro/{livroId}")
    public ResponseEntity<List<Progresso>> listarPorLivro(@PathVariable UUID livroId) {
        List<Progresso> progressos = progressoService.listarPorLivro(livroId);
        return ResponseEntity.ok(progressos);
    }

    @GetMapping("/usuario/{usuarioId}/periodo")
    public ResponseEntity<List<Progresso>> listarPorPeriodo(
            @PathVariable UUID usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<Progresso> progressos = progressoService.listarPorPeriodo(usuarioId, inicio, fim);
        return ResponseEntity.ok(progressos);
    }

    @GetMapping("/usuario/{usuarioId}/xp-total")
    public ResponseEntity<Long> calcularXpTotal(@PathVariable UUID usuarioId) {
        Long xpTotal = progressoService.calcularXpTotal(usuarioId);
        return ResponseEntity.ok(xpTotal);
    }
}