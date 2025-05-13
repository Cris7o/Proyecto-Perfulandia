package com.Perfulandia.microservicios_base.Repository;

import com.Perfulandia.microservicios_base.model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

}
