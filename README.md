# Security with JWT

### Descripción
Este proyecto demuestra cómo implementar seguridad utilizando JSON Web Tokens (JWT) en una aplicación Spring Boot. Incluye características de autenticación y autorización de usuarios.

#### Instalación
1. Clona el repositorio:
    ```sh
    git clone https://github.com/exequielraineri/JWT_Service.git
    cd JWT_Service
    ```

2. Configura la base de datos en :
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/database_name
    spring.datasource.username=root
    spring.datasource.password=root
    ```

3. Construye el proyecto:
    ```sh
    mvn clean install
    ```

4. Ejecuta la aplicación:
    ```sh
    mvn spring-boot:run
    ```

### Endpoints de API

#### Autenticación
- `POST /auth/login`: Autenticar un usuario.
- `POST /auth/register`: Registrar un nuevo usuario.

#### Ejemplo
- `GET /api/v1/hello`: Devuelve un mensaje de "Hello World".
