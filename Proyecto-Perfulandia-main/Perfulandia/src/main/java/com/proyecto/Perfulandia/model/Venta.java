package com.proyecto.Perfulandia.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Venta {
    @Id @GeneratedValue

    private Long id;

    private LocalDate fecha;

    @ManyToOne
    private Usuario vendedor;

    @ManyToMany
    private List<Producto> productos;

    private double total;

}
