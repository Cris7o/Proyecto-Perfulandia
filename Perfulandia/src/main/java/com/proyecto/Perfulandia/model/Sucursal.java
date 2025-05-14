package com.proyecto.Perfulandia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Sucursal {
    @Id @GeneratedValue
    private Long id;

    private String nombre;
    private String direccion;
}
