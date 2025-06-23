package com.proyecto.Perfulandia.assemblers;

import com.proyecto.Perfulandia.controller.EnvioController;
import com.proyecto.Perfulandia.model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EnvioAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
            linkTo(methodOn(EnvioController.class).obtenerEnvio(envio.getId())).withSelfRel(),
            linkTo(methodOn(EnvioController.class).listarEnvios()).withRel("envios")
        );
    }
}
