package com.proyecto.Perfulandia.service;

import com.proyecto.Perfulandia.model.Producto;
import com.proyecto.Perfulandia.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    private ProductoRepository productoRepository;
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
        productoService = new ProductoService(productoRepository);
    }

    @Test
    void testListarProductos() {
        Producto producto = new Producto();
        producto.setNombre("Perfume Prueba");
        producto.setPrecio(19990);
        producto.setStock(5);

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> productos = productoService.listarProductos();

        assertFalse(productos.isEmpty());
        assertEquals("Perfume Prueba", productos.get(0).getNombre());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
void testEliminarProducto() {
    Long id = 1L;
    doNothing().when(productoRepository).deleteById(id);

    productoService.eliminarProducto(id);

    verify(productoRepository, times(1)).deleteById(id);
}

@Test
void testObtenerProductoPorId() {
    Long id = 1L;
    Producto producto = new Producto();
    producto.setId(id);
    producto.setNombre("Perfume X");

    when(productoRepository.findById(id)).thenReturn(java.util.Optional.of(producto));

    var resultado = productoService.obtenerProductoPorId(id);

    assertTrue(resultado.isPresent());
    assertEquals("Perfume X", resultado.get().getNombre());
    verify(productoRepository, times(1)).findById(id);
}



}
