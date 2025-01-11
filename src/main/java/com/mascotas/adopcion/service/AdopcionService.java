package com.mascotas.adopcion.service;

import com.mascotas.adopcion.model.Adopcion;
import com.mascotas.adopcion.model.Mascota;
import com.mascotas.adopcion.model.Usuario;
import com.mascotas.adopcion.repository.AdopcionRepository;
import com.mascotas.adopcion.repository.MascotaRepository;
import com.mascotas.adopcion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdopcionService {

    @Autowired
    private AdopcionRepository adopcionRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public AdopcionService(AdopcionRepository adopcionRepository, MascotaRepository mascotaRepository, UsuarioRepository usuarioRepository) {
        this.adopcionRepository = adopcionRepository;
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Guarda una adopción en la base de datos y actualiza el estado de la mascota a no disponible
    public Adopcion saveAdopcion(Adopcion adopcion) {
        // Establece la fecha de adopción a la fecha actual
        adopcion.setFechaAdopcion(LocalDate.now().toString());

        // Busca el usuario por ID y si no lo encuentra lanza una excepción
        Mascota mascota = mascotaRepository.findById(adopcion.getMascota().getId()).orElseThrow(() -> new RuntimeException("Mascota not found"));

        // Actualiza el estado de la mascota a no disponible
        mascota.setDisponible(false);
        mascotaRepository.save(mascota);

        // Asigna la mascota y el usuario por referencia
        Mascota mascotaRef = new Mascota();
        mascotaRef.setId(adopcion.getMascota().getId());
        adopcion.setMascota(mascotaRef);

        Usuario usuarioRef = new Usuario();
        usuarioRef.setId(adopcion.getUsuario().getId());
        adopcion.setUsuario(usuarioRef);

        // Guarda la adopción en el repositorio y la retorna
        return adopcionRepository.save(adopcion);
    }

    // Retorna todas las adopciones
    public Iterable<Adopcion> getAdopciones() {
        return adopcionRepository.findAll();
    }
}