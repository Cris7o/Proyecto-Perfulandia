package com.proyecto.Perfulandia.model;

import java.time.LocalDate;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destino;
    private String estado; // Ejemplo: "Pendiente", "En camino", "Entregado"
    private LocalDate fechaEnvio;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta; // Referencia a la venta relacionada
}
