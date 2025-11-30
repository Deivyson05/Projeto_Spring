package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.dto.ProgressoCreateDTO;
import br.com.yomu.gamificacaoDaLeitura.model.Livro;
import br.com.yomu.gamificacaoDaLeitura.model.Progresso;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.repository.ProgressoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProgressoService {

    private final ProgressoRepository progressoRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;
    private final MetaService metaService;
    private final RankingAsyncService rankingAsyncService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Progresso registrar(UUID usuarioId, UUID livroId, ProgressoCreateDTO dto) { // ✅ Recebe DTO
        log.info("📖 Registrando progresso para usuário {} no livro {}", usuarioId, livroId);
        
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Livro livro = livroService.buscarPorId(livroId);
    
        // Criar entidade Progresso a partir do DTO
        Progresso progresso = new Progresso();
        progresso.setUsuario(usuario);
        progresso.setLivro(livro);
        progresso.setQuantidade(dto.getQuantidade());
        progresso.setTipoProgresso(dto.getTipoProgresso());
    
        // XP será calculado automaticamente pelo @PrePersist
        Progresso progressoSalvo = progressoRepository.save(progresso);
        log.info("✅ Progresso registrado: {} XP gerado", progressoSalvo.getXpGerado());
    
        usuarioService.adicionarXp(usuarioId, progressoSalvo.getXpGerado());
        metaService.atualizarMetasComProgresso(usuarioId, progressoSalvo);

        eventPublisher.publishEvent(new ProgressoRegistradoEvent(usuarioId));
    
        return progressoSalvo;
    }

    public List<Progresso> listarPorUsuario(UUID usuarioId) {
        return progressoRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId);
    }

    public List<Progresso> listarPorLivro(UUID livroId) {
        return progressoRepository.findByLivroId(livroId);
    }

    public List<Progresso> listarPorPeriodo(UUID usuarioId, LocalDateTime inicio, LocalDateTime fim) {
        return progressoRepository.findByUsuarioIdAndPeriodo(usuarioId, inicio, fim);
    }

    public Long calcularXpTotal(UUID usuarioId) {
        Long xp = progressoRepository.calcularXpTotalUsuario(usuarioId);
        return xp != null ? xp : 0L;
    }
}