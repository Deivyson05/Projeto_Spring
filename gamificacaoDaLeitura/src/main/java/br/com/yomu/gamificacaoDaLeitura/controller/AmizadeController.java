package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Amizade;
import br.com.yomu.gamificacaoDaLeitura.service.AmizadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/amizades")
@RequiredArgsConstructor
public class AmizadeController {

    private final AmizadeService amizadeService;

    @PostMapping("/solicitar")
    public ResponseEntity<Amizade> enviarSolicitacao(
            @RequestParam UUID usuarioId1,
            @RequestParam UUID usuarioId2) {
        Amizade amizade = amizadeService.enviarSolicitacao(usuarioId1, usuarioId2);
        return ResponseEntity.status(HttpStatus.CREATED).body(amizade);
    }

    @PatchMapping("/{id}/aceitar")
    public ResponseEntity<Amizade> aceitarSolicitacao(@PathVariable UUID id) {
        Amizade amizade = amizadeService.aceitarSolicitacao(id);
        return ResponseEntity.ok(amizade);
    }

    @PatchMapping("/{id}/bloquear")
    public ResponseEntity<Void> bloquearUsuario(@PathVariable UUID id) {
        amizadeService.bloquearUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}/pendentes")
    public ResponseEntity<List<Amizade>> listarSolicitacoesPendentes(@PathVariable UUID usuarioId) {
        List<Amizade> solicitacoes = amizadeService.listarSolicitacoesPendentes(usuarioId);
        return ResponseEntity.ok(solicitacoes);
    }

    @GetMapping("/usuario/{usuarioId}/amigos")
    public ResponseEntity<List<Amizade>> listarAmigos(@PathVariable UUID usuarioId) {
        List<Amizade> amigos = amizadeService.listarAmigos(usuarioId);
        return ResponseEntity.ok(amigos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAmizade(@PathVariable UUID id) {
        amizadeService.removerAmizade(id);
        return ResponseEntity.noContent().build();
    }
}