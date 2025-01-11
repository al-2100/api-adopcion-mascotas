# Documentación de la API

## Autenticación

La API utiliza autenticación HTTP Básica. Todos los endpoints protegidos requieren la autorización adecuada basada en roles.

## Roles

- `ADMIN`: Acceso completo a todos los puntos finales.
- `ATENCION`: Acceso a la gestión de mascotas y adopciones.
- `USER`: Acceso básico (ver mascotas).

## Endpoints

### Gestión de Usuarios (`/api/usuarios`)

#### Registrar Usuario
```http
POST /api/usuarios/registro
```
**Acceso:** Público  
**Descripción:** Registrar un nuevo usuario con el rol por defecto.

**Cuerpo de la Solicitud:**
```json
{
    "nombre": "string",
    "correo": "string",
    "password": "string",
    "telefono": "string"
}
```

#### Registrar Usuario con Rol
```http
POST /api/usuarios/registro/admin?rol=ADMIN
```
**Acceso:** ADMIN  
**Descripción:** Registrar un nuevo usuario con el rol especificado.

**Cuerpo de la Solicitud:**
```json
{
    "nombre": "string",
    "correo": "string",
    "password": "string"
}
```

**Parámetros de la URL:**
- `rol`: Rol a asignar al usuario (ADMIN o ATENCION).

#### Registrar Usuario Atención
```http
POST /api/usuarios/registro/admin?rol=ATENCION
```
**Acceso:** ADMIN  
**Descripción:** Registrar un nuevo usuario con el rol de Atención.

**Cuerpo de la Solicitud:**
```json
{
    "nombre": "string",
    "correo": "string",
    "password": "string",
    "telefono": "string"
}
```

#### Obtener Todos los Usuarios
```http
GET /api/usuarios
```
**Acceso:** ADMIN  
**Descripción:** Obtener todos los usuarios registrados.

#### Editar Usuario
```http
PUT /api/usuarios/{id}
```
**Acceso:** ADMIN  
**Descripción:** Editar la información de un usuario.

**Parámetros de la URL:**
- `id`: ID del usuario.

**Cuerpo de la Solicitud:**
```json
{
    "correo": "stringModificado"
}
```

#### Eliminar Usuario
```http
DELETE /api/usuarios/{id}
```
**Acceso:** ADMIN  
**Descripción:** Eliminar una cuenta de usuario.

**Parámetros de la URL:**
- `id`: ID del usuario.

### Gestión de Mascotas (`/api/mascotas`)

#### Registrar Mascota
```http
POST /api/mascotas
```
**Acceso:** ADMIN, ATENCION  
**Descripción:** Registrar una nueva mascota.

**Cuerpo de la Solicitud:**
```json
{
    "nombre": "string",
    "edad": number,
    "tipoMascota": {
        "nombre": "string"
    },
    "disponible": boolean
}
```

#### Obtener Mascotas Disponibles
```http
GET /api/mascotas
```
**Acceso:** Público  
**Descripción:** Obtener todas las mascotas disponibles.

#### Buscar Mascota por ID
```http
GET /api/mascotas/{id}
```
**Acceso:** ADMIN, ATENCION  
**Descripción:** Obtener los detalles de una mascota específica.

**Parámetros de la URL:**
- `id`: ID de la mascota.

#### Editar Mascota
```http
PUT /api/mascotas/{id}
```
**Acceso:** ADMIN, ATENCION  
**Descripción:** Editar la información de una mascota.

**Parámetros de la URL:**
- `id`: ID de la mascota.

**Cuerpo de la Solicitud:**
```json
{
    "nombre": "string",
    "edad": number,
    "disponible": boolean,
    "tipoMascota": {
        "nombre": "string"
    }
}
```
Solo los campos especificados se actualizarán.

#### Eliminar Mascota
```http
DELETE /api/mascotas/{id}
```
**Acceso:** ADMIN  
**Descripción:** Eliminar una mascota del sistema.

**Parámetros de la URL:**
- `id`: ID de la mascota.

### Gestión de Adopciones (`/api/adopciones`)

#### Registrar Adopción
```http
POST /api/adopciones
```
**Acceso:** ADMIN, ATENCION  
**Descripción:** Registrar una nueva adopción.

**Cuerpo de la Solicitud:**
```json
{
    "mascota": {
        "id": 12
    },
    "usuario": {
        "id": 15
    }
}
```

#### Obtener Adopciones
```http
GET /api/adopciones
```
**Acceso:** ADMIN, ATENCION  
**Descripción:** Obtener todos los registros de adopciones.

## Respuestas

### Respuestas de Éxito
- `200 OK`: Solicitud exitosa.
- `201 Created`: Recurso creado con éxito.
- `204 No Content`: Recurso eliminado con éxito.

### Respuestas de Error
- `400 Bad Request`: Datos de entrada inválidos.
- `401 Unauthorized`: Se requiere autenticación.
- `403 Forbidden`: Permisos insuficientes.
- `404 Not Found`: Recurso no encontrado.
- `500 Internal Server Error`: Error en el servidor.

**Formato de Respuesta de Error:**
```json
{
    "timestamp": "2024-01-11T10:00:00Z",
    "status": 400,
    "error": "Bad Request",
    "message": "Descripción del error",
    "path": "/api/endpoint"
}
```

### Actuator endpoints

Se utiliza Spring Boot Actuator para monitorear la aplicación. Algunos de los endpoints disponibles son:

- `/actuator/health`: Información sobre la salud de la aplicación.
- `/actuator/metrics`: Métricas de la aplicación.
- `/actuator/info`: Información general de la aplicación.

## Notas Adicionales

1. Todos los endpoints, excepto los siguientes, requieren autenticación:
    - `POST /api/usuarios/registro`
    - `GET /api/mascotas`

2. Requisitos de la Contraseña:
    - Las contraseñas se cifran usando BCrypt antes de almacenarlas.
    - Las contraseñas originales nunca se devuelven en las respuestas.

3. Gestión de Sesiones:
    - La API es stateless.
    - Cada solicitud debe incluir credenciales de autenticación.

4. Protección CSRF:
    - La protección CSRF está deshabilitada para esta API.
