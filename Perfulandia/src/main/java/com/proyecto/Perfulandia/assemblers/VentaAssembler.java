package com.proyecto.Perfulandia.assemblers;

import com.proyecto.Perfulandia.controller.VentaController;
import com.proyecto.Perfulandia.model.Venta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VentaAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {

    @Override
    public EntityModel<Venta> toModel(Venta venta) {
        return EntityModel.of(venta,
            linkTo(methodOn(VentaController.class).obtenerVenta(venta.getId())).withSelfRel(),
            linkTo(methodOn(VentaController.class).listarVentas()).withRel("ventas"));
    }
}
