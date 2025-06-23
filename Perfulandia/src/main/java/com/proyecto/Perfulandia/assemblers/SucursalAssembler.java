package com.proyecto.Perfulandia.assemblers;

import com.proyecto.Perfulandia.controller.SucursalController;
import com.proyecto.Perfulandia.model.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SucursalAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalController.class).obtenerSucursal(sucursal.getId())).withSelfRel(),
                linkTo(methodOn(SucursalController.class).listarSucursales()).withRel("sucursales"));
    }
}
