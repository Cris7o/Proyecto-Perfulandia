package com.proyecto.Perfulandia.service;

import com.proyecto.Perfulandia.model.Sucursal;
import com.proyecto.Perfulandia.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public List<Sucursal> listarSucursales() {
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> obtenerSucursalPorId(Long id) {
        return sucursalRepository.findById(id);
    }

    public Sucursal guardarSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public void eliminarSucursal(Long id) {
        sucursalRepository.deleteById(id);
    }

}
