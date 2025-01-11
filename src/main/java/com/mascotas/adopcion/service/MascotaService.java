package com.mascotas.adopcion.service;


import com.mascotas.adopcion.model.Mascota;
import com.mascotas.adopcion.model.TipoMascota;
import com.mascotas.adopcion.repository.MascotaRepository;
import com.mascotas.adopcion.repository.TipoMascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private TipoMascotaRepository tipoMascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository, TipoMascotaRepository tipoMascotaRepository) {
        this.mascotaRepository = mascotaRepository;
        this.tipoMascotaRepository = tipoMascotaRepository;
    }

    public Mascota saveMascota(Mascota mascota) {
        // Valida el nombre de la mascota
        validarNombreMascota(mascota.getNombre());

        // Busca el tipo de mascota por nombre, si no existe lo crea
        TipoMascota tipoMascota = tipoMascotaRepository.findByNombre(mascota.getTipoMascota().getNombre());
        if (tipoMascota == null) {
            tipoMascota = new TipoMascota();
            tipoMascota.setNombre(mascota.getTipoMascota().getNombre());
            tipoMascota = tipoMascotaRepository.save(tipoMascota);
        }

        // Asigna el tipo de mascota y establece la disponibilidad a true
        mascota.setTipoMascota(tipoMascota);
        mascota.setDisponible(true);
        return mascotaRepository.save(mascota);
    }

    private void validarNombreMascota(String nombre) {
        if (!nombre.matches("^[a-zA-Z\\s]+$")) {
            throw new IllegalArgumentException("El nombre de la mascota solo puede contener letras y espacios");
        }
    }

    // Retorna todas las mascotas
    public Iterable<Mascota> getMascotas() {
        return mascotaRepository.findAll();
    }

    // Retorna todas las mascotas disponibles
    public List<Mascota> getMascotasDisponibles() {
        return mascotaRepository.findByDisponibleTrue();
    }

    // Retorna una mascota por su ID
    public Mascota getMascotaById(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con el ID: " + id));
    }

    // Elimina una mascota por su ID
    public void deleteMascota(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con el ID: " + id));
        mascotaRepository.delete(mascota);
    }

    // Actualiza una mascota
    @Transactional
    public Mascota updateMascota(Long id, Mascota mascotaActualizada) {
        // Buscar la mascota existente por ID
        Mascota mascotaExistente = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con el ID: " + id));

        // Actualizar solo los campos no nulos
        if (mascotaActualizada.getNombre() != null) {
            mascotaExistente.setNombre(mascotaActualizada.getNombre());
        }

        if (mascotaActualizada.getEdad() != null) {
            mascotaExistente.setEdad(mascotaActualizada.getEdad());
        }

        if (mascotaActualizada.getTipoMascota() != null) {
            mascotaExistente.setTipoMascota(mascotaActualizada.getTipoMascota());
        }

        // Nota: Verificamos explícitamente si 'disponible' es no nulo para no sobrescribir valores existentes.
        if (mascotaActualizada.isDisponible() != null) {
            mascotaExistente.setDisponible(mascotaActualizada.isDisponible());
        }

        // Guardar y retornar la entidad actualizada
        return mascotaRepository.save(mascotaExistente);
    }
}
