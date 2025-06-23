package com.proyecto.Perfulandia.service;

import com.proyecto.Perfulandia.model.Producto;
import com.proyecto.Perfulandia.model.Venta;
import com.proyecto.Perfulandia.repository.ProductoRepository;
import com.proyecto.Perfulandia.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VentaServiceTest {
    
    private ProductoRepository productoRepository;
    private VentaRepository ventaRepository;
    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        ventaRepository = mock(VentaRepository.class);
        productoRepository = mock(ProductoRepository.class);
        ventaService = new VentaService(ventaRepository, productoRepository);
    }

    @Test
    void testListarVentas() {
        Venta venta = new Venta();
        venta.setCliente("Juan Pérez");
        venta.setFecha(LocalDateTime.now());
        venta.setCantidad(2);
        venta.setTotal(39980);

        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        List<Venta> ventas = ventaService.listarVentas();

        assertFalse(ventas.isEmpty());
        assertEquals("Juan Pérez", ventas.get(0).getCliente());
        verify(ventaRepository, times(1)).findAll();
    }

    @Test
    void testCrearVenta() {
        Producto producto = new Producto();
        producto.setId(1L); // ✅ Necesario para que lo pueda buscar el servicio
        producto.setNombre("Perfume A");
        producto.setPrecio(19990);

        Venta venta = new Venta();
        venta.setCliente("Ana López");
        venta.setCantidad(3);
        venta.setProducto(producto); // contiene solo el ID, el servicio lo vuelve a buscar

        // Mock del productoRepository
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // Mock del guardado
        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Venta result = ventaService.crearVenta(venta);

        assertNotNull(result);
        assertEquals("Ana López", result.getCliente());
        assertEquals(3, result.getCantidad());
        assertEquals(3 * producto.getPrecio(), result.getTotal());
        verify(productoRepository, times(1)).findById(1L);
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }



    @Test
    void testObtenerVentaPorId() {
        Long id = 1L;
        Venta venta = new Venta();
        venta.setId(id);
        venta.setCliente("Carlos Díaz");

        when(ventaRepository.findById(id)).thenReturn(Optional.of(venta));

        Optional<Venta> result = ventaService.obtenerVentaPorId(id);

        assertTrue(result.isPresent());
        assertEquals("Carlos Díaz", result.get().getCliente());
        verify(ventaRepository, times(1)).findById(id);
    }
}
