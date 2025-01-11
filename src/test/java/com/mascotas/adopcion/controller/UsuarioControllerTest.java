package com.mascotas.adopcion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.adopcion.model.Usuario;
import com.mascotas.adopcion.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testRegistroConRol() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCorreo("admin@example.com");

        when(usuarioService.saveUsuarioConRol(any(Usuario.class), eq("ADMIN"))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios/registro/admin")
                        .param("rol", "ADMIN")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.correo").value("admin@example.com"));

        verify(usuarioService, times(1)).saveUsuarioConRol(any(Usuario.class), eq("ADMIN"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCorreo("updated@example.com");

        when(usuarioService.updateUsuario(eq(1L), any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.correo").value("updated@example.com"));

        verify(usuarioService, times(1)).updateUsuario(eq(1L), any(Usuario.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUsuario() throws Exception {
        doNothing().when(usuarioService).deleteUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).deleteUsuario(1L);
    }
}