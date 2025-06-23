package com.proyecto.Perfulandia.controller;

import com.proyecto.Perfulandia.model.Envio;
import com.proyecto.Perfulandia.service.EnvioService;
import com.proyecto.Perfulandia.assemblers.EnvioAssembler;

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
@RequestMapping("/api/envios")
@Tag(name = "ENVIOS")
public class EnvioController {

    private final EnvioService envioService;
    private final EnvioAssembler envioAssembler;

    public EnvioController(EnvioService envioService, EnvioAssembler envioAssembler) {
        this.envioService = envioService;
        this.envioAssembler = envioAssembler;
    }

    @GetMapping
    @Operation(summary = "Listar envíos", description = "Muestra todos los envíos registrados")
    public CollectionModel<EntityModel<Envio>> listarEnvios() {
        List<EntityModel<Envio>> envios = envioService.listarEnvios().stream()
                .map(envioAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(envios,
                linkTo(methodOn(EnvioController.class).listarEnvios()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar envíos por id", description = "Muestra los envíos registrados en base al id ingresado")
    public ResponseEntity<EntityModel<Envio>> obtenerEnvio(@PathVariable Long id) {
        return envioService.obtenerEnvioPorId(id)
                .map(envioAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear envío", description = "Registra un nuevo envío asociado a una venta")
    public EntityModel<Envio> crearEnvio(@RequestBody Envio envio) {
        Envio nuevo = envioService.crearEnvio(envio);
        return envioAssembler.toModel(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar envío", description = "Modifica la información de un envío")
    public ResponseEntity<EntityModel<Envio>> actualizarEnvio(@PathVariable Long id, @RequestBody Envio actualizado) {
        return ResponseEntity.ok(
                envioAssembler.toModel(
                        envioService.actualizarEnvio(id, actualizado)
                )
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envío", description = "Elimina un envío del sistema")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        envioService.eliminarEnvio(id);
        return ResponseEntity.noContent().build();
    }
}
