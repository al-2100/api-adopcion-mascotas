package com.mascotas.adopcion.initializer;

import com.mascotas.adopcion.repository.RolRepository;
import com.mascotas.adopcion.repository.UsuarioRepository;
import com.mascotas.adopcion.model.Rol;
import com.mascotas.adopcion.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Crear los roles si no existen
            if (rolRepository.count() == 0) {
                logger.info("Creating roles...");
                rolRepository.save(new Rol("ATENCION"));
                rolRepository.save(new Rol("ADMIN"));
                rolRepository.save(new Rol("USER"));
            }

            // Crear el usuario ADMIN si no existe
            if (usuarioRepository.count() == 0) {
                logger.info("Creating admin user...");
                Optional<Rol> adminRole = rolRepository.findByNombre("ADMIN");
                if (adminRole.isPresent()) {
                    Usuario admin = new Usuario("admin", "admin@admin.com", passwordEncoder.encode("adminPassword"), adminRole.get());
                    usuarioRepository.save(admin);
                } else {
                    logger.error("Admin role not found!");
                }
            }
        } catch (Exception e) {
            logger.error("Error initializing data", e);
        }
    }
}