package com.compass.demo_park_api.web.controller;

import java.util.List;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compass.demo_park_api.entity.Usuario;
import com.compass.demo_park_api.service.UsuarioService;
import com.compass.demo_park_api.web.dto.UsuarioCreateDto;
import com.compass.demo_park_api.web.dto.UsuarioResponseDto;
import com.compass.demo_park_api.web.dto.UsuarioSenhaDto;
import com.compass.demo_park_api.web.dto.mapper.UsuarioMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usuarios", description = "Contém todas as operações de CRUD de usuários")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Recurso para criar um novo usuário", description = "Recurso para criar um novo usuário",
    responses = {
        @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
        @ApiResponse(responseCode = "409", description = "Usuário e senha ja cadastrado no sistema",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entradas inválidos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Recuperar um usuário pelo Id", description = "Requisição exige um Bearer Token. Acesso Restrido a ADMIN|CLIENTE",
    security = @SecurityRequirement(name = "security "),
    responses = {
        @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
        @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENTE') AND #id == authentication.principal.id)")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Recurso para atualizar senha", description = "Requisição exige um Bearer Token. Acesso Restrido a ADMIN|CLIENTE",
    security = @SecurityRequirement(name = "security "),
    responses = {
        @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "400", description = "Senha não confere",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recurso para listar todos os usuários", description = "Requisição exige um Bearer Token. Acesso Restrido a ADMIN",
    security = @SecurityRequirement(name = "security "),
    responses = {
        @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados", 
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class)))),
        @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List <Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
