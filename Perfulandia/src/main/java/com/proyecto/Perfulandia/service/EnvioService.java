package com.proyecto.Perfulandia.service;

import com.proyecto.Perfulandia.model.Envio;
import com.proyecto.Perfulandia.model.Venta;
import com.proyecto.Perfulandia.repository.EnvioRepository;
import com.proyecto.Perfulandia.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final VentaRepository ventaRepository;

    public EnvioService(EnvioRepository envioRepository, VentaRepository ventaRepository) {
        this.envioRepository = envioRepository;
        this.ventaRepository = ventaRepository;
    }

    public List<Envio> listarEnvios() {
        return envioRepository.findAll();
    }

    public Optional<Envio> obtenerEnvioPorId(Long id) {
        return envioRepository.findById(id);
    }

    public Envio crearEnvio(Envio envio) {
        Venta venta = ventaRepository.findById(envio.getVenta().getId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        envio.setFechaEnvio(LocalDate.now());
        envio.setVenta(venta);
        envio.setEstado("Pendiente");

        return envioRepository.save(envio);
    }

    public Envio actualizarEnvio(Long id, Envio actualizado) {
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));

        envio.setDestino(actualizado.getDestino());
        envio.setEstado(actualizado.getEstado());

        return envioRepository.save(envio);
    }

    public void eliminarEnvio(Long id) {
        envioRepository.deleteById(id);
    }

}
