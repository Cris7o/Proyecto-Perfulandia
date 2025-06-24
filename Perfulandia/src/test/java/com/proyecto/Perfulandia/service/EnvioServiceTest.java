package com.proyecto.Perfulandia.service;

import com.proyecto.Perfulandia.model.Envio;
import com.proyecto.Perfulandia.model.Venta;
import com.proyecto.Perfulandia.repository.EnvioRepository;
import com.proyecto.Perfulandia.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnvioServiceTest {

    private EnvioRepository envioRepository;
    private VentaRepository ventaRepository;
    private EnvioService envioService;

    @BeforeEach
    void setUp() {
        envioRepository = mock(EnvioRepository.class);
        ventaRepository = mock(VentaRepository.class);
        envioService = new EnvioService(envioRepository, ventaRepository);
    }

    @Test
    void testListarEnvios() {
        Envio envio = new Envio();
        envio.setDestino("Santiago");
        envio.setEstado("En camino");
        envio.setFechaEnvio(LocalDate.now());

        when(envioRepository.findAll()).thenReturn(List.of(envio));

        List<Envio> envios = envioService.listarEnvios();

        assertFalse(envios.isEmpty());
        assertEquals("Santiago", envios.get(0).getDestino());
        verify(envioRepository, times(1)).findAll();
    }

    @Test
    void testObtenerEnvioPorId() {
        Long id = 1L;
        Envio envio = new Envio();
        envio.setId(id);
        envio.setDestino("Valparaíso");

        when(envioRepository.findById(id)).thenReturn(Optional.of(envio));

        Optional<Envio> result = envioService.obtenerEnvioPorId(id);

        assertTrue(result.isPresent());
        assertEquals("Valparaíso", result.get().getDestino());
        verify(envioRepository, times(1)).findById(id);
    }

    @Test
    void testCrearEnvio() {
        Long ventaId = 1L;
        Venta venta = new Venta();
        venta.setId(ventaId);

        Envio envio = new Envio();
        envio.setDestino("Concepción");
        envio.setEstado("Pendiente");
        envio.setFechaEnvio(LocalDate.now());
        envio.setVenta(venta);

        when(ventaRepository.findById(ventaId)).thenReturn(Optional.of(venta));
        when(envioRepository.save(envio)).thenReturn(envio);

        Envio result = envioService.crearEnvio(envio);

        assertNotNull(result);
        assertEquals("Concepción", result.getDestino());
        assertEquals("Pendiente", result.getEstado());
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void testEliminarEnvio() {
        Long id = 1L;
        envioService.eliminarEnvio(id);
        verify(envioRepository, times(1)).deleteById(id);
    }
}
