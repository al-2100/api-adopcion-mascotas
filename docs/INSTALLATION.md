## Instrucciones Detalladas de Configuración

### 1. Clonar el Repositorio

Clona el repositorio para comenzar con la instalación:

```bash
git clone https://github.com/yourusername/pet-adoption-api.git
cd pet-adoption-api
```

### 2. Configuración de la Base de Datos

Crea una base de datos PostgreSQL. Puede estar vacía, pues la aplicación generará las tablas automáticamente. La base de datos se creará a partir de las entidades definidas en el proyecto mediante anotaciones de JPA para mapear las entidades a las tablas.

Además, el inicializador de datos creará roles y un usuario administrador por defecto:

    Roles: ADMIN, ATENCION, USER
    Usuario Administrador:
        Email: admin@admin.com
        Contraseña: adminPassword

### 3. Configuración de la Aplicación

Configura o modifica el archivo application.properties en src/main/resources con las siguientes configuraciones:
    
```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

Asegúrate de reemplazar los marcadores de posición con los valores apropiados de las variables de entorno o configurarlas en tu entorno.

### 4. Construcción y Ejecución

1. Instalar las dependencias

```bash
mvn clean install
```
2. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

### 5. Verificación de la Instalación

La aplicación debería estar corriendo en http://localhost:8080. Puedes verificar la instalación de la siguiente manera:

Accede al endpoint de salud de Spring Boot Actuator desde el navegador o mediante una herramienta como Postman: 
```
http://localhost:8080/actuator/health
```

Ingresa los datos de administrador por defecto para acceder a los endpoints protegidos:

```
Email: admin@admin.com
Contraseña: adminPassword
```

Deberias recibir una respuesta similar a esta:

```json
{
    "status": "UP"
}
```

### Problemas Comunes y Soluciones

1. **Conexión a la Base de Datos Fallida**
    - Verifica que PostgreSQL esté en ejecución
    - Revisa las credenciales de la base de datos

2. **El Puerto Ya Está en Uso**
    - Cambia el puerto del servidor en `application.properties`
    - Mata el proceso que esté utilizando el puerto