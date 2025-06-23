package com.proyecto.Perfulandia.controller;

import com.proyecto.Perfulandia.assemblers.ProductoAssembler;
import com.proyecto.Perfulandia.model.Producto;
import com.proyecto.Perfulandia.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "PRODUCTOS")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoAssembler assembler;

    public ProductoController(ProductoService productoService, ProductoAssembler assembler) {
        this.productoService = productoService;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar productos", description = "Retorna todos los productos disponibles en inventario")
    public CollectionModel<EntityModel<Producto>> listarProductos() {
        List<EntityModel<Producto>> productos = productoService.listarProductos()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listarProductos()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto", description = "Retorna un producto por su ID")
    public EntityModel<Producto> obtenerProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return assembler.toModel(producto);
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Agrega un nuevo producto al inventario")
    @PreAuthorize("hasRole('GERENTE')")
    public EntityModel<Producto> crearProducto(@RequestBody Producto producto) {
        Producto guardado = productoService.guardarProducto(producto);
        return assembler.toModel(guardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<EntityModel<Producto>> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return productoService.obtenerProductoPorId(id)
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setPrecio(productoActualizado.getPrecio());
                    producto.setStock(productoActualizado.getStock());
                    producto.setSucursal(productoActualizado.getSucursal());
                    Producto actualizado = productoService.guardarProducto(producto);
                    return ResponseEntity.ok(assembler.toModel(actualizado));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del inventario por su ID")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
