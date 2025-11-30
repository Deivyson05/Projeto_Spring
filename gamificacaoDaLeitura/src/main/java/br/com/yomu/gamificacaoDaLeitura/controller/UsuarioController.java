package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.yomu.gamificacaoDaLeitura.swagger.UsuarioSwagger.UsuarioCreate;
import br.com.yomu.gamificacaoDaLeitura.swagger.UsuarioSwagger.UsuarioResponse;
import br.com.yomu.gamificacaoDaLeitura.swagger.UsuarioSwagger.UsuarioSummary;
import br.com.yomu.gamificacaoDaLeitura.swagger.UsuarioSwagger.UsuarioUpdate;
import br.com.yomu.gamificacaoDaLeitura.swagger.UsuarioSwagger.LoginBody;
import br.com.yomu.gamificacaoDaLeitura.swagger.UsuarioSwagger.LoginResult;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários e perfis")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // -------------------- LOGIN --------------------
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    @Operation(
        summary = "Login por e-mail e senha",
        description = "Autentica o usuário pelo e-mail e senha e retorna dados básicos do perfil.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login OK",
                content = @Content(schema = @Schema(implementation = LoginResult.class))),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas",
                content = @Content(schema = @Schema(implementation = String.class)))
        }
    )
    public ResponseEntity<?> login(@Valid @RequestBody LoginBody body) {
        try {
            var u = usuarioService.login(body.email(), body.senha());
            return ResponseEntity.ok(new LoginResult(
                    u.getId(),
                    u.getNomeUsuario(),
                    u.getEmail(),
                    u.getFotoPerfil(),
                    "Login efetuado com sucesso"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Email ou senha inválidos");
        }
    }

    // -------------------- CRIAR --------------------
    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Criar novo usuário", description = "Cadastra um novo usuário no sistema com geração automática de código de convite")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou email/username já cadastrado",
            content = @Content)
    })
    public ResponseEntity<Usuario> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário a ser criado",
            required = true,
            content = @Content(schema = @Schema(implementation = UsuarioCreate.class))
        )
        @Valid @RequestBody Usuario usuario) {
        Usuario usuarioCriado = usuarioService.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    // -------------------- BUSCAR POR ID --------------------    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados completos de um usuário pelo seu UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
            content = @Content)
    })
    public ResponseEntity<Usuario> buscarPorId(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // -------------------- BUSCAR POR EMAIL --------------------
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por email", description = "Retorna um usuário pelo seu endereço de email")
    public ResponseEntity<Usuario> buscarPorEmail(
            @Parameter(description = "Email do usuário", example = "joao@email.com")
            @PathVariable String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/convite/{codigo}")
    @Operation(summary = "Buscar usuário por código de convite", 
               description = "Usado para adicionar amigos através do código único de convite")
    public ResponseEntity<Usuario> buscarPorCodigoConvite(
            @Parameter(description = "Código de convite único (8 caracteres)", example = "ABC12345")
            @PathVariable String codigo) {
        Usuario usuario = usuarioService.buscarPorCodigoConvite(codigo);
        return ResponseEntity.ok(usuario);
    }

    // -------------------- LISTAR --------------------
    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna lista completa de usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários",
        content = @Content(schema = @Schema(implementation = UsuarioSummary.class)))
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // -------------------- ATUALIZAR --------------------
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza dados do perfil do usuário")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Campos para atualização do usuário",
        required = true,
        content = @Content(schema = @Schema(implementation = UsuarioUpdate.class))
    )
    public ResponseEntity<Usuario> atualizar(
            @Parameter(description = "UUID do usuário") @PathVariable UUID id,
            @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // -------------------- DELETAR --------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove permanentemente um usuário do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID do usuário") @PathVariable UUID id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}