package com.mascotas.adopcion.controller;

import com.mascotas.adopcion.model.Adopcion;
import com.mascotas.adopcion.service.AdopcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// Controlador REST para las adopciones
@RestController
@RequestMapping("/api/adopciones")
public class AdopcionController {
    private final AdopcionService adopcionService;

    public AdopcionController(AdopcionService adopcionService) {
        this.adopcionService = adopcionService;
    }

    // Endpoint para guardar una adopción
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENCION')")
    @PostMapping
    public ResponseEntity<Adopcion> saveAdopcion(@RequestBody Adopcion adopcion) {
        try {
            Adopcion savedAdopcion = adopcionService.saveAdopcion(adopcion);
            return new ResponseEntity<>(savedAdopcion, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para obtener todas las adopciones
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENCION')")
    @GetMapping
    public ResponseEntity<Iterable<Adopcion>> getAdopciones() {
        try {
            Iterable<Adopcion> adopciones = adopcionService.getAdopciones();
            return new ResponseEntity<>(adopciones, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}