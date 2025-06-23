package com.proyecto.Perfulandia.service;

import com.proyecto.Perfulandia.model.Rol;
import com.proyecto.Perfulandia.model.Usuario;
import com.proyecto.Perfulandia.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//aqui se prueba: Guardar usuario, listar usuarios, obtener usuarios por id.

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testGuardarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("123456");
        usuario.setRol(Rol.ADMIN);

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario guardado = usuarioService.guardarUsuario(usuario);
        assertEquals("Juan Pérez", guardado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testListarUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Ana");
        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Luis");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> usuarios = usuarioService.listarUsuarios();
        assertEquals(2, usuarios.size());
        assertEquals("Ana", usuarios.get(0).getNombre());
    }

    @Test
    void testObtenerUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Carlos");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorID(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
    }
}
