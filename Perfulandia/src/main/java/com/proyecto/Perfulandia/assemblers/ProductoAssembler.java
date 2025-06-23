package com.proyecto.Perfulandia.assemblers;
import com.proyecto.Perfulandia.controller.ProductoController;
import com.proyecto.Perfulandia.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

        @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
            linkTo(methodOn(ProductoController.class).obtenerProducto(producto.getId())).withSelfRel(),
            linkTo(methodOn(ProductoController.class).listarProductos()).withRel("productos"));
    }

}
