package com.proyecto.Perfulandia.controller;

import com.proyecto.Perfulandia.model.Sucursal;
import com.proyecto.Perfulandia.service.SucursalService;
import com.proyecto.Perfulandia.assemblers.SucursalAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/sucursales")
@Tag(name = "SUCURSALES")
public class SucursalController {

    private final SucursalService sucursalService;
    private final SucursalAssembler sucursalAssembler;

    public SucursalController(SucursalService sucursalService, SucursalAssembler sucursalAssembler) {
        this.sucursalService = sucursalService;
        this.sucursalAssembler = sucursalAssembler;
    }

    @GetMapping
    @Operation(summary = "Listar sucursales", description = "Obtiene una lista de todas las sucursales registradas")
    public CollectionModel<EntityModel<Sucursal>> listarSucursales() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.listarSucursales()
            .stream()
            .map(sucursalAssembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(sucursales,
            linkTo(methodOn(SucursalController.class).listarSucursales()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por ID", description = "Obtiene una sucursal específica por su ID")
    public ResponseEntity<EntityModel<Sucursal>> obtenerSucursal(@PathVariable Long id) {
        return sucursalService.obtenerSucursalPorId(id)
            .map(sucursalAssembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear sucursal", description = "Crea una nueva sucursal con sus datos básicos")
    public EntityModel<Sucursal> crearSucursal(@RequestBody Sucursal sucursal) {
        Sucursal guardada = sucursalService.guardarSucursal(sucursal);
        return sucursalAssembler.toModel(guardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sucursal", description = "Actualiza los datos de una sucursal existente")
    public ResponseEntity<EntityModel<Sucursal>> actualizarSucursal(@PathVariable Long id, @RequestBody Sucursal actualizada) {
        return sucursalService.obtenerSucursalPorId(id)
            .map(sucursal -> {
                sucursal.setNombre(actualizada.getNombre());
                sucursal.setDireccion(actualizada.getDireccion());
                sucursal.setCiudad(actualizada.getCiudad());
                Sucursal actualizadaGuardada = sucursalService.guardarSucursal(sucursal);
                return ResponseEntity.ok(sucursalAssembler.toModel(actualizadaGuardada));
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal específica por su ID")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
