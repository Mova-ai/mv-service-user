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
  "uid": "2JlUhiFTg0YcrKLPqqxGgiib3fU2",
  "email": "test@back.com",
  "displayName": "Test User",
  "photo": "https://example.com/photo.jpg",
  "preferences": {
    "language": "es",
    "darkMode": true,
    "currency": "EUR"
  },
  "roles": [
    { "name": "USER" }
  ],
  "amigos": [
    { "uid": "A1B2C3D4", "displayName": "Amigo 1", "email": "amigo1@example.com" },
    { "uid": "E5F6G7H8", "displayName": "Amigo 2", "email": "amigo2@example.com" }
  ],
  "favoritos": [
    { "id": "plan123", "tipo": "plan", "nombre": "Tour Roma" },
    { "id": "local456", "tipo": "local", "nombre": "Restaurante Italiano" }
  ],
  "planes": [
    {
      "id": "plan123",
      "nombre": "Tour Roma",
      "fecha": "2025-08-20T10:00:00Z",
      "ubicacion": "Roma, Italia",
      "participantes": ["2JlUhiFTg0YcrKLPqqxGgiib3fU2", "A1B2C3D4"]
    }
  ],
  "locales": [
    {
      "id": "local456",
      "nombre": "Restaurante Italiano",
      "direccion": "Via Roma 10, Roma",
      "categoria": "Comida",
      "rating": 4.5
    }
  ],
  "resenas": [
    {
      "id": "resena789",
      "localId": "local456",
      "autorUid": "2JlUhiFTg0YcrKLPqqxGgiib3fU2",
      "comentario": "Excelente comida y ambiente",
      "rating": 5,
      "fecha": "2025-08-17T15:00:00Z"
    }
  ],
  "createdAt": "2025-08-17T16:00:00Z",
  "updatedAt": "2025-08-17T16:10:00Z"
}

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

