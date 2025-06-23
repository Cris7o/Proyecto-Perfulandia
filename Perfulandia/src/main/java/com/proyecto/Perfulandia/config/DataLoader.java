package com.proyecto.Perfulandia.config;
import net.datafaker.Faker;
import com.proyecto.Perfulandia.model.Usuario;
import com.proyecto.Perfulandia.model.Rol;
import com.proyecto.Perfulandia.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyecto.Perfulandia.model.Envio;
import com.proyecto.Perfulandia.model.Producto;
import com.proyecto.Perfulandia.model.Sucursal;
import com.proyecto.Perfulandia.repository.EnvioRepository;
import com.proyecto.Perfulandia.repository.ProductoRepository;
import com.proyecto.Perfulandia.repository.SucursalRepository;
import com.proyecto.Perfulandia.model.Venta;
import com.proyecto.Perfulandia.repository.VentaRepository;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Random;

@Configuration
public class DataLoader {

        @Bean
CommandLineRunner loadProductos(ProductoRepository productoRepo, SucursalRepository sucursalRepo) {
    return args -> {
        Faker faker = new Faker();


        if (sucursalRepo.count() == 0) {
            Sucursal sucursal = new Sucursal();
            sucursal.setNombre("Sucursal Santiago");
            sucursal.setDireccion("Av. Central 123");
            sucursal.setCiudad("Santiago");
            sucursalRepo.save(sucursal);
        }

        Sucursal sucursal = sucursalRepo.findAll().get(0); // toma la primera

        for (int i = 0; i < 10; i++) {
            Producto perfume = new Producto();
            perfume.setNombre("Perfume " + faker.commerce().productName());
            perfume.setPrecio(Double.parseDouble(faker.commerce().price()));
            perfume.setStock(faker.number().numberBetween(10, 100));
            perfume.setSucursal(sucursal);

            productoRepo.save(perfume);
        }

        System.out.println("✅ Se generaron 10 perfumes aleatorios.");
    };
}

    
    @Bean
CommandLineRunner loadVentas(ProductoRepository productoRepo, VentaRepository ventaRepo) {
    return args -> {
        Faker faker = new Faker();
        List<Producto> productos = productoRepo.findAll();

        if (productos.isEmpty()) {
            System.out.println("No hay productos para generar ventas.");
            return;
        }

        for (int i = 0; i < 10; i++) {
            Producto producto = productos.get(faker.number().numberBetween(0, productos.size()));

            Venta venta = new Venta();
            venta.setCliente(faker.name().fullName());
            venta.setFecha(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30)));
            venta.setProducto(producto);
            int cantidad = faker.number().numberBetween(1, 5);
            venta.setCantidad(cantidad);
            venta.setTotal(cantidad * producto.getPrecio());

            ventaRepo.save(venta);
        }

        System.out.println("✅ Se generaron 10 ventas aleatorias.");
    };
}



    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository) {
        return args -> {
            Faker faker = new Faker();
            Random random = new Random();

            for (int i = 0; i < 10; i++) {
                Usuario usuario = new Usuario();
                usuario.setNombre(faker.name().fullName());
                usuario.setEmail(faker.internet().emailAddress());
                usuario.setPassword("123456"); // para pruebas
                usuario.setRol(Rol.values()[random.nextInt(Rol.values().length)]);

                usuarioRepository.save(usuario);
            }

            System.out.println("✅ Se crearon 10 usuarios falsos");
        };
        
        
    }
        @Bean
CommandLineRunner loadEnvios(EnvioRepository envioRepo, VentaRepository ventaRepo) {
    return args -> {
        Faker faker = new Faker();

        List<Venta> ventas = ventaRepo.findAll();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas para asociar a envíos.");
            return;
        }

        for (int i = 0; i < 10; i++) {
            Envio envio = new Envio();

            // Asocia un envío a una venta aleatoria
            Venta venta = ventas.get(faker.number().numberBetween(0, ventas.size()));
            envio.setVenta(venta);

            // Rellena los campos con datos falsos
            envio.setDestino(faker.address().fullAddress());
            envio.setEstado(faker.options().option("Pendiente", "En camino", "Entregado"));
            envio.setFechaEnvio(faker.date()
                .past(10, java.util.concurrent.TimeUnit.DAYS)
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate());

            envioRepo.save(envio);
        }

        System.out.println("✅ Se generaron 10 envíos aleatorios.");
    };
}
}

