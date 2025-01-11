# API de Adopción de Mascotas

Una robusta API RESTful construida con Spring Boot que gestiona la adopción de mascotas a través de un sistema seguro de autenticación de usuarios, control de acceso basado en roles y características completas de gestión de mascotas y adopciones.

[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15.0-blue.svg)](https://www.postgresql.org/)

## Características Principales

- **Autenticación Segura**: Control de acceso basado en roles con contraseñas encriptadas.
- **Gestión de Mascotas**: Operaciones CRUD completas para mascotas y adopciones.
- **Roles de Usuario**: Diferentes niveles de permisos (Administrador, Personal, Usuario).
- **Documentación de la API**: Documentación completa de la API con ejemplos.
- **Monitoreo**: Integración con Spring Boot Actuator.
- **Pruebas**: Pruebas unitarias con MockMvc para operaciones CRUD.

## 🛠️ Tecnologías

- **Backend**: Java 21, Spring Boot 3.4.1
- **Base de Datos**: PostgreSQL (Producción), H2 (Desarrollo)
- **Seguridad**: Spring Security
- **Pruebas**: JUnit 5, MockMvc
- **Monitoreo**: Spring Boot Actuator

## 📝 Documentación

- [Guía de Instalación](./docs/INSTALLATION.md)
- [Documentación de la API](./docs/API.md)
- [Esquema de la Base de Datos](./docs/DATABASE.md)

## Instalación

1. **Requisitos previos**
   - Java 21
   - Maven
   - PostgreSQL

2. **Clonar el repositorio**
   ```bash
   git clone https://github.com/yourusername/pet-adoption-api.git
   cd pet-adoption-api
   ```

3. **Construir el proyecto**
   ```bash
   mvn clean install
   ```

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

La API estará disponible en `http://localhost:8080`

## 🔐 Credenciales por defecto de Administrador

Utiliza las siguientes credenciales para acceder a los endpoints protegidos de la API por primera vez:
```
Email: admin@admin.com
Password: adminPassword
```

## 📦 Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/mascotas/adopcion
│   │       ├── config/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── initializer/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/mascotas/adopcion
            └── controller/
```

Sigue la arquitectura MVC (Modelo-Vista-Controlador), utilizando las siguientes capas:

- **Controladores (Controllers)**: Definen los puntos finales (endpoints) de la API y gestionan las solicitudes HTTP.
- **Servicios (Services)**: Contienen la lógica de negocio. Los servicios gestionan la creación, actualización y eliminación de objetos.
- **Repositorios (Repositories)**: Interactúan con la base de datos para realizar operaciones CRUD sobre las entidades.
- **Entidades (Entities)**: Representan los modelos de datos de la aplicación, como `Adopcion`, `Mascota`, y `Usuario`.

## Endpoints

- **Roles y permisos**: El acceso a ciertos endpoints está restringido por roles, tales como `ADMIN` y `ATENCION`.
- **Cifrado de contraseñas**: Las contraseñas de los usuarios se cifran utilizando un `PasswordEncoder` de Spring Security.

### Roles

- **ADMIN**: Puede gestionar usuarios, mascotas y adopciones.
- **ATENCION**: Puede gestionar adopciones y mascotas disponibles.
- **USER**: Rol asignado por defecto a los usuarios registrados.

### Adopciones

- **POST /api/adopciones**: Guarda una nueva adopción. Requiere el rol `ATENCION` o `ADMIN`.
- **GET /api/adopciones**: Obtiene todas las adopciones. Requiere el rol `ATENCION` o `ADMIN`.

### Mascotas

- **GET /api/mascotas**: Obtiene todas las mascotas disponibles para adopción.
- **POST /api/mascotas**: Guarda una nueva mascota. Requiere los roles `ADMIN` o `ATENCION`.
- **PUT /api/mascotas/{id}**: Actualiza una mascota por su ID. Requiere los roles `ADMIN` o `ATENCION`.
- **GET /api/mascotas/{id}**: Obtiene una mascota por su ID. Requiere los roles `ADMIN` o `ATENCION`.
- **DELETE /api/mascotas/{id}**: Elimina una mascota por su ID. Requiere el rol `ADMIN`.

### Usuarios

- **POST /api/usuarios/registro**: Registra un nuevo usuario.
- **POST /api/usuarios/registro/admin**: Registra un usuario con un rol específico. Requiere el rol `ADMIN`.
- **GET /api/usuarios**: Obtiene todos los usuarios. Requiere el rol `ADMIN`.
- **PUT /api/usuarios/{id}**: Actualiza un usuario. Requiere el rol `ADMIN`.
- **DELETE /api/usuarios/{id}**: Elimina un usuario por su ID. Requiere el rol `ADMIN`.
