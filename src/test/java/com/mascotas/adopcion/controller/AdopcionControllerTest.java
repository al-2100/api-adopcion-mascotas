package com.mascotas.adopcion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.adopcion.model.Adopcion;
import com.mascotas.adopcion.service.AdopcionService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdopcionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdopcionService adopcionService;

    @InjectMocks
    private AdopcionController adopcionController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(adopcionController).build();
    }

    @Test
    @WithMockUser(roles = "ATENCION")
    void testSaveAdopcion() throws Exception {
        Adopcion adopcion = new Adopcion();
        adopcion.setId(1L);

        when(adopcionService.saveAdopcion(any(Adopcion.class))).thenReturn(adopcion);

        mockMvc.perform(post("/api/adopciones")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(adopcion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(adopcionService, times(1)).saveAdopcion(any(Adopcion.class));
    }

    @Test
    @WithMockUser(roles = "ATENCION")
    void testGetAdopciones() throws Exception {
        Adopcion adopcion = new Adopcion();
        adopcion.setId(1L);

        when(adopcionService.getAdopciones()).thenReturn(Arrays.asList(adopcion));

        mockMvc.perform(get("/api/adopciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(adopcionService, times(1)).getAdopciones();
    }
}
