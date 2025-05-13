package com.Perfulandia.microservicios_base.controller;


import com.Perfulandia.microservicios_base.model.Usuario;
import com.Perfulandia.microservicios_base.repository.UsuarioRepository;
import org.springframework.beans.factory.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios") 

public class UsuarioController {

    @AutoWired
    private UsuarioRepository repo;
    


    @GetMapping
    public List<Usuario> listar(){
        return repo.findAll();
    }

    @PostMapping
    public Usuario crear(@RequestBody usuario u){
        return repo.save(u);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id,@RequestBody Usuario u){
        Usuario actual = repo.findById(id).orElseThrow();
        actual.setNombre(u.getNombre());
        actual.setCorreo(u.getCorreo());
        return repo.save(actual);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        repo.deletedById(id);
    }


}
