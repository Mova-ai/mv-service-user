# mv-service-user

# Servicio de Usuarios - MOVA

Backend en **Spring Boot** que gestiona perfiles extendidos y funcionalidades sociales. 
La **autenticación se delega completamente a Firebase Auth**.

---

## Tecnologías
- Java 17, Spring Boot 3, Spring Security
- Hibernate / JPA
- Firebase Admin SDK
- Base de datos relacional (PostgreSQL, etc.)

---

## Autenticación
- El cliente obtiene ID Token vía Firebase.
- En cada request se envía en la cabecera:  
  `Authorization: Bearer <idToken>`
- Backend valida token con `FirebaseAuth` y crea `Authentication` en Spring Security.

---

## Endpoints principales:

Dejo un vistazo de lo que debería tener un usuario en versión 1.

### Perfil usuario
- `GET /me` → Devuelve perfil del usuario autenticado.
- `PUT /me` → Actualiza perfil del usuario.
- `GET /usuarios/{id}` → Obtener perfil extendido (más allá de lo que Firebase almacena, p. ej. preferencias, roles, historial de actividades)
- `PUT /usuarios/{id}` → Actualizar perfil extendido (displayName, foto, preferencias, etc.)
- `GET /usuarios/{id}/amigos` → Lista de amigos, si tu app tiene social features.
- `POST /usuarios/{id}/amigos` → Solicitud de amistad.
- `PUT /usuarios/{id}/amigos/{idAmigo}` → Aceptar/rechazar solicitud.
  - `DELETE /usuarios/{id}/amigos/{idAmigo}` → Eliminar amigo.

    ```json
      {
      "uid": "firebase-uid-123",
      "displayName": "Carlos Pérez",
      "email": "carlos@example.com",
      "photoUrl": "https://example.com/foto.jpg",
      "preferences": {
      "interests": ["cultura", "gastronomía"],
      "budget": "medio"
      },
      "friends": ["firebase-uid-456", "firebase-uid-789"],
      "favorites": ["local-123", "local-987"],  // solo ids
      "points": 120
      }
      ```

## Tecnologías

- Java 17+
- Spring Boot 3.x
- Spring Security
- Firebase Admin SDK
- JPA / Hibernate
- PostgreSQL (o cualquier base de datos relacional compatible)
- Maven

---

## Instalación

1. Clonar el repositorio:

```bash
git clone https://github.com/mova-ia/mova-users-service.git
cd mova-users-service
```

2. Arranca el servidor: 

Desde el botón de play como siempre en eclipse.

3. Ya puedes usar el proyecto, posiblemente en el puerto 8080.

