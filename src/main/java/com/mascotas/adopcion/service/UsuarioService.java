package com.mascotas.adopcion.service;

import com.mascotas.adopcion.model.Rol;
import com.mascotas.adopcion.model.Usuario;
import com.mascotas.adopcion.repository.RolRepository;
import com.mascotas.adopcion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario saveUsuario(Usuario usuario) {
        // Validar el correo
        validateEmail(usuario.getCorreo());

        // Verificar si el correo ya existe
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Cifrar la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar el rol de usuario común por defecto
        Rol rol = rolRepository.findByNombre("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario saveUsuarioConRol(Usuario usuario, String rolNombre) {
        // Validar el correo
        validateEmail(usuario.getCorreo());

        // Verificar si el correo ya existe
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Cifrar la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar el rol especificado
        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol " + rolNombre + " no encontrado"));

        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    // Validar el correo electrónico
    private void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            throw new RuntimeException("Correo electrónico no válido");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Buscar el usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo));

        // Retorna un objeto UserDetails con el correo, contraseña y rol del usuario
        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getPassword())
                .roles(usuario.getRol().getNombre())
                .build();
    }

    // Retorna todos los usuarios
    @Transactional(readOnly = true)
    public Iterable<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    // Actualiza un usuario
    @Transactional
    public Usuario updateUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar solo los campos no nulos
        if (usuarioActualizado.getNombre() != null) {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
        }

        if (usuarioActualizado.getCorreo() != null) {
            if (!usuarioExistente.getCorreo().equals(usuarioActualizado.getCorreo())) {
                if (usuarioRepository.findByCorreo(usuarioActualizado.getCorreo()).isPresent()) {
                    throw new RuntimeException("El correo ya está registrado");
                }
                usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
            }
        }

        if (usuarioActualizado.getTelefono() != null) {
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    // Elimina un usuario por su ID
    @Transactional
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el ID: " + id));
        usuarioRepository.delete(usuario);
    }
}