package com.mascotas.adopcion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.adopcion.model.Mascota;
import com.mascotas.adopcion.service.MascotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MascotaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MascotaService mascotaService;

    @InjectMocks
    private MascotaController mascotaController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(mascotaController).build();
    }

    @Test
    @WithMockUser
    void testGetMascotas() throws Exception {
        Mascota mascota1 = new Mascota();
        mascota1.setId(1L);
        mascota1.setNombre("Firulais");

        when(mascotaService.getMascotasDisponibles()).thenReturn(Arrays.asList(mascota1));

        mockMvc.perform(get("/api/mascotas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Firulais"));

        verify(mascotaService, times(1)).getMascotasDisponibles();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteMascota() throws Exception {
        doNothing().when(mascotaService).deleteMascota(1L);

        mockMvc.perform(delete("/api/mascotas/1"))
                .andExpect(status().isNoContent());

        verify(mascotaService, times(1)).deleteMascota(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testSaveMascota() throws Exception {
        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setNombre("Firulais");

        when(mascotaService.saveMascota(any(Mascota.class))).thenReturn(mascota);

        mockMvc.perform(post("/api/mascotas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mascota)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Firulais"));

        verify(mascotaService, times(1)).saveMascota(any(Mascota.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateMascota() throws Exception {
        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setNombre("Firulais");

        when(mascotaService.updateMascota(eq(1L), any(Mascota.class))).thenReturn(mascota);

        mockMvc.perform(put("/api/mascotas/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mascota)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Firulais"));

        verify(mascotaService, times(1)).updateMascota(eq(1L), any(Mascota.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetMascotaById() throws Exception {
        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setNombre("Firulais");

        when(mascotaService.getMascotaById(1L)).thenReturn(mascota);

        mockMvc.perform(get("/api/mascotas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Firulais"));

        verify(mascotaService, times(1)).getMascotaById(1L);
    }
}
