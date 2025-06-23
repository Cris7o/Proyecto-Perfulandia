package com.proyecto.Perfulandia.controller;

import com.proyecto.Perfulandia.assemblers.UsuarioAssembler;
import com.proyecto.Perfulandia.model.Usuario;
import com.proyecto.Perfulandia.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "USUARIOS")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioAssembler usuarioAssembler;

    public UsuarioController(UsuarioService usuarioService, UsuarioAssembler usuarioAssembler) {
        this.usuarioService = usuarioService;
        this.usuarioAssembler = usuarioAssembler;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna todos los usuarios registrados")
    public CollectionModel<EntityModel<Usuario>> listarUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.listarUsuarios().stream()
                .map(usuarioAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).listarUsuarios()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista usuarios por id", description = "Retorna los usuarios en base al id ingresado")
    public ResponseEntity<EntityModel<Usuario>> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorID(id)
                .map(usuarioAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario con su rol")
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(usuarioAssembler.toModel(nuevo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Modifica los datos de un usuario existente")
    public ResponseEntity<EntityModel<Usuario>> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioService.obtenerUsuarioPorID(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioActualizado.getNombre());
                    usuario.setEmail(usuarioActualizado.getEmail());
                    usuario.setPassword(usuarioActualizado.getPassword());
                    usuario.setRol(usuarioActualizado.getRol());
                    Usuario actualizado = usuarioService.guardarUsuario(usuario);
                    return ResponseEntity.ok(usuarioAssembler.toModel(actualizado));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
