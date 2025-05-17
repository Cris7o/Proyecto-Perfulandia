package com.proyecto.Perfulandia.controller;

import com.proyecto.Perfulandia.model.Sucursal;
import com.proyecto.Perfulandia.service.SucursalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

     private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    public List<Sucursal> listarSucursales() {
        return sucursalService.listarSucursales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerSucursal(@PathVariable Long id) {
        return sucursalService.obtenerSucursalPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sucursal crearSucursal(@RequestBody Sucursal sucursal) {
        return sucursalService.guardarSucursal(sucursal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable Long id, @RequestBody Sucursal actualizada) {
        return sucursalService.obtenerSucursalPorId(id)
                .map(sucursal -> {
                    sucursal.setNombre(actualizada.getNombre());
                    sucursal.setDireccion(actualizada.getDireccion());
                    sucursal.setCiudad(actualizada.getCiudad());
                    return ResponseEntity.ok(sucursalService.guardarSucursal(sucursal));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }

}
