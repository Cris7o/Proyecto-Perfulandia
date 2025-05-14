package com.proyecto.Perfulandia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Producto {
    @Id @GeneratedValue
    private Long id;

    private String nombre;
    private int stock;
    private double precio;

    @ManyToOne
    private Sucursal sucursal;

}
