package com.proyecto.Perfulandia.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Envio {
    @Id @GeneratedValue
    private Long id;

    private String direccionEntrega;
    private LocalDate fechaEnvio;

    @OneToOne
    private Venta Venta;
}
