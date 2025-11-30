package br.com.yomu.gamificacaoDaLeitura.service;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProgressoRegistradoEvent {
    private final UUID usuarioId;
    
    public ProgressoRegistradoEvent(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }
}