package com.mascotas.adopcion.controller;

import com.mascotas.adopcion.model.Mascota;
import com.mascotas.adopcion.service.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para las mascotas
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // Endpoint para obtener todas las mascotas disponibles
    @GetMapping
    public ResponseEntity<List<Mascota>> getMascotas() {
        try {
            List<Mascota> mascotas = mascotaService.getMascotasDisponibles();
            return new ResponseEntity<>(mascotas, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para guardar una mascota
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENCION')")
    public ResponseEntity<Mascota> saveMascota(@RequestBody Mascota mascota) {
        try {
            Mascota savedMascota = mascotaService.saveMascota(mascota);
            return new ResponseEntity<>(savedMascota, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para actualizar una mascota por su ID
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENCION')")
    public ResponseEntity<Mascota> updateMascota(@PathVariable Long id, @RequestBody Mascota mascota) {
        try {
            Mascota updatedMascota = mascotaService.updateMascota(id, mascota);
            return new ResponseEntity<>(updatedMascota, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para obtener una mascota por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENCION')")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        try {
            Mascota mascota = mascotaService.getMascotaById(id);
            return new ResponseEntity<>(mascota, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para eliminar una mascota por su ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        try {
            mascotaService.deleteMascota(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}