package br.com.yomu.gamificacaoDaLeitura.swagger;

import io.swagger.v3.oas.annotations.media.Schema;




public class UsuarioSwagger {


// =====================================
// MODELOS DE LOGIN
// Utilizados no endpoint: POST /api/usuarios/login
// =====================================


@Schema(name = "LoginBody", description = "Credenciais para login")
public record LoginBody(
@Schema(example = "joao@email.com", required = true) String email,
@Schema(example = "senha123", required = true) String senha
) {}


@Schema(name = "LoginResult", description = "Resposta após login bem-sucedido")
public record LoginResult(
@Schema(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000") java.util.UUID id,
@Schema(example = "joao_silva") String nomeUsuario,
@Schema(example = "joao@email.com") String email,
@Schema(example = "https://example.com/foto.jpg") String fotoPerfil,
@Schema(example = "Login efetuado com sucesso") String message
) {}


// =====================================
// MODELOS DE CRIAÇÃO DE USUÁRIO
// Utilizados no endpoint: POST /api/usuarios
// =====================================


@Schema(name = "UsuarioCreate", description = "Dados enviados para criação do usuário")
public record UsuarioCreate(
@Schema(example = "joao_silva") String nomeUsuario,
@Schema(example = "data:image/jpeg;base64,/9j/4AAQSk...") String fotoPerfil,
@Schema(example = "João Silva") String nome,
@Schema(example = "masculino") String genero,
@Schema(example = "joao@email.com") String email,
@Schema(example = "senha123") String senha
) {}


@Schema(name = "UsuarioResponse", description = "Estrutura completa retornada ao criar/buscar usuário")
public record UsuarioResponse(
@Schema(description = "ID do usuário") java.util.UUID id,
@Schema(example = "joao_silva") String nomeUsuario,
@Schema(example = "João Silva") String nome,
@Schema(example = "masculino") String genero,
@Schema(example = "joao@email.com") String email,
@Schema(example = "https://example.com/foto.jpg") String fotoPerfil,
@Schema(example = "ABCD1234") String codigoConvite
) {}


// =====================================
// MODELOS PARA LISTAGEM DE USUÁRIOS
// Utilizados no endpoint: GET /api/usuarios
// =====================================


@Schema(name = "UsuarioSummary", description = "Resumo do usuário para listagem")
public record UsuarioSummary(
@Schema(description = "ID do usuário") java.util.UUID id,
@Schema(example = "joao_silva") String nomeUsuario,
@Schema(example = "https://example.com/foto.jpg") String fotoPerfil
) {}


// =====================================
// MODELOS PARA ATUALIZAÇÃO
// Utilizados no endpoint: PUT /api/usuarios/{id}
// =====================================


@Schema(name = "UsuarioUpdate", description = "Campos aceitos para atualização do usuário")
public record UsuarioUpdate(
@Schema(example = "João Silva") String nome,
@Schema(example = "masculino") String genero,
@Schema(example = "data:image/jpeg;base64,/9j/4AAQSk...") String fotoPerfil
) {}
}