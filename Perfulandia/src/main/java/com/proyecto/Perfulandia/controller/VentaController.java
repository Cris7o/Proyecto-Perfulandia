package com.proyecto.Perfulandia.controller;

import com.proyecto.Perfulandia.assemblers.VentaAssembler;
import com.proyecto.Perfulandia.model.Venta;
import com.proyecto.Perfulandia.service.VentaService;
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
@RequestMapping("/api/ventas")
@Tag(name = "VENTAS")
public class VentaController {

    private final VentaService ventaService;
    private final VentaAssembler ventaAssembler;

    public VentaController(VentaService ventaService, VentaAssembler ventaAssembler) {
        this.ventaService = ventaService;
        this.ventaAssembler = ventaAssembler;
    }

    @GetMapping
    @Operation(summary = "Listar ventas", description = "Obtiene todas las ventas registradas")
    public CollectionModel<EntityModel<Venta>> listarVentas() {
        List<EntityModel<Venta>> ventas = ventaService.listarVentas().stream()
                .map(ventaAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaController.class).listarVentas()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar ventas por id", description = "Obtiene las ventas registradas en base al id ingresado")
    public ResponseEntity<EntityModel<Venta>> obtenerVenta(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id)
                .map(ventaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear venta", description = "Registra una nueva venta para un cliente")
    public ResponseEntity<EntityModel<Venta>> crearVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.crearVenta(venta);
        return ResponseEntity.ok(ventaAssembler.toModel(nuevaVenta));
    }
}
