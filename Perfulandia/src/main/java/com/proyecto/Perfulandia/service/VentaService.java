package com.proyecto.Perfulandia.service;

import com.proyecto.Perfulandia.model.Producto;
import com.proyecto.Perfulandia.model.Venta;
import com.proyecto.Perfulandia.repository.ProductoRepository;
import com.proyecto.Perfulandia.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta crearVenta(Venta venta) {
        Producto producto = productoRepository.findById(venta.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        venta.setFecha(LocalDateTime.now());
        venta.setTotal(producto.getPrecio() * venta.getCantidad());
        venta.setProducto(producto);

        return ventaRepository.save(venta);
    }

}
