package com.mascotas.adopcion.repository;

import com.mascotas.adopcion.model.TipoMascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMascotaRepository extends JpaRepository<TipoMascota, Long> {
    TipoMascota findByNombre(String nombreTipoMascota);
}
