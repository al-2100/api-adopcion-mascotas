# Documentación del Modelo de Base de Datos - Sistema de Adopción de Mascotas

## Descripción General
Este sistema gestiona un proceso de adopción de mascotas, permitiendo registrar usuarios, mascotas, tipos de mascotas, roles y el proceso de adopción en sí mismo.

## Entidades

### Usuario
Representa a los usuarios del sistema (tanto adoptantes como administradores).

**Atributos:**
- `id`: Long (PK, Auto-incrementable)
- `nombre`: String
- `correo`: String (Único, No nulo)
- `password`: String (No nulo)
- `telefono`: String
- `rol`: Relación ManyToOne con Rol (No nulo, Carga EAGER)

**Relaciones:**
- Tiene un rol asignado (ManyToOne con Rol)
- Puede tener múltiples adopciones (referenciado desde Adopcion)

### Mascota
Representa a las mascotas disponibles para adopción.

**Atributos:**
- `id`: Long (PK, Auto-incrementable)
- `nombre`: String
- `edad`: Integer
- `disponible`: Boolean
- `tipoMascota`: Relación ManyToOne con TipoMascota

**Relaciones:**
- Pertenece a un tipo de mascota (ManyToOne con TipoMascota)
- Puede estar en múltiples adopciones (referenciado desde Adopcion)

### TipoMascota
Catálogo de tipos de mascotas disponibles.

**Atributos:**
- `id`: Long (PK, Auto-incrementable)
- `nombre`: String

**Relaciones:**
- Puede tener múltiples mascotas asociadas (referenciado desde Mascota)

### Rol
Define los roles disponibles en el sistema.

**Atributos:**
- `id`: Long (PK, Auto-incrementable)
- `nombre`: String (No nulo, Único)

**Relaciones:**
- Puede estar asignado a múltiples usuarios (referenciado desde Usuario)

### Adopcion
Registra el proceso de adopción entre un usuario y una mascota.

**Atributos:**
- `id`: Long (PK, Auto-incrementable)
- `fechaAdopcion`: String
- `mascota`: Relación ManyToOne con Mascota
- `usuario`: Relación ManyToOne con Usuario

**Relaciones:**
- Se relaciona con una mascota (ManyToOne con Mascota)
- Se relaciona con un usuario (ManyToOne con Usuario)


## Diagrama de Relaciones

```
Usuario -----> Rol
   |
   |
   v
Adopcion <----- Mascota -----> TipoMascota
```