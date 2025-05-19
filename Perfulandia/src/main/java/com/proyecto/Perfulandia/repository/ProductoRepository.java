package com.proyecto.Perfulandia.repository;

import com.proyecto.Perfulandia.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
 // Puedes agregar consultas personalizadas si las necesitas
}
