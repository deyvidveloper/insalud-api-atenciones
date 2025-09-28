# 🏥 API REST - Gestión de Atenciones Médicas (INSALUD)

Aplicación backend desarrollada en **Java 17 con Spring Boot**, que permite gestionar las atenciones médicas de pacientes en el sistema de salud INSALUD. El sistema implementa autenticación mediante **JWT**, seguridad con **Spring Security**, y se conecta a una base de datos **PostgreSQL** para el registro y consulta de datos médicos.

⚡ **Importante**: Este proyecto es **solo backend**, diseñado para ser consumido por un frontend (ejemplo: **Angular**, **React**, **Vue**).

---

## ✨ **Características principales**

- 🔑 Autenticación con JWT y control de acceso por roles (ADMIN, MÉDICO, PACIENTE).
- 📋 Registro, actualización y eliminación de atenciones médicas.
- 🔍 Consulta personalizada de atenciones con filtros.
- 📄 Paginación en listados para mejorar el rendimiento.
- 🧩 Manejo de entidades relacionadas: Persona, Usuario, Paciente, Empleado, Especialidad.
- ✅ Validaciones integradas con Spring Validation.

---

## 🧪 **Tecnologías utilizadas**

- Java 17 ☕
- Spring Boot 🌱
- Spring Security 🔐
- JWT (JSON Web Token)
- PostgreSQL 🐘
- JPA / Hibernate ⚙️
- IntelliJ IDEA 💻
- Postman (pruebas de endpoints) 📬

---

## 📂 **Modelo de datos**

El sistema contempla las siguientes entidades principales:

- 👤 **Persona**: datos generales (nombre, email, estado).
- 👨‍⚕️ **Empleado**: enlazado a Persona, con roles (Médico, Admin).
- 🧑‍🦱 **Paciente**: enlazado a Persona con rol PACIENTE.
- 🩺 **Especialidad**: catálogo médico (Dermatología, Cardiología, etc).
- 📌 **Atención**: registro de consultas (fecha, motivo, diagnóstico, estado).

---

## 🌐 **Endpoints disponibles**

- `POST /api/auth/login` → autenticación y generación de token JWT.
- `GET /api/atenciones` → listado de atenciones (ADMIN, con paginación).
- `GET /api/atenciones/mias` → atenciones del usuario autenticado.
- `POST /api/atenciones` → registrar atención (ADMIN/MÉDICO).
- `PUT /api/atenciones/{id}` → actualizar atención (ADMIN/MÉDICO).
- `DELETE /api/atenciones/{id}` → eliminar atención (solo ADMIN).

---

## 🚀 **Pasos para ejecutar el proyecto**

1. Clonar el repositorio desde **GitHub**.
2. Crear la base de datos `bd_gestion_atenciones` en **pgAdmin**.
3. Configurar el archivo `application.properties` con los datos de conexión.
4. Ejecutar el backend desde **IntelliJ IDEA**.
5. Probar los endpoints en **Postman**, enviando el token JWT en los headers.
6. (Opcional) Conectar este backend a un frontend desarrollado en **Angular** o **React**.

---

## 🗂️ **Base de datos incluida**

Por si acaso, también he dejado mi base de datos que usé para hacer pruebas. Esto permitirá que puedan **probar los endpoints en Postman inmediatamente**, sin necesidad de crear registros manualmente.

La BD ya contiene:
- Roles de prueba (ADMIN, MÉDICO, PACIENTE).
- Especialidades (Dermatología, Cardiología).
- Personas de ejemplo.
- Empleados y pacientes vinculados.
- Al menos una atención médica registrada.

---

# 📖 DOCUMENTACIÓN DE PRUEBAS - Postman (completo)

Esta sección contiene **todos los ejemplos de requests y responses** para probar la API en Postman, incluyendo el token JWT completo en los ejemplos.

---

## 🔎 Resumen rápido - qué requiere token

- **No requieren token:** `POST /api/auth/login`, `POST /api/auth/register` (si aplica).  
- **Requieren token (Authorization: Bearer <token>):** `GET /api/atenciones`, `GET /api/atenciones/mias`, `POST /api/atenciones`, `PUT /api/atenciones/{id}`, `DELETE /api/atenciones/{id}`, y consultas filtradas como `GET /api/atenciones?fecha=YYYY-MM-DD`.  
- **Notas de roles:** `GET /api/atenciones` y `DELETE /api/atenciones/{id}` → **ADMIN**; `POST`/`PUT` → **ADMIN** o **MEDICO**; `GET /api/atenciones/mias` → quien esté autenticado (PACIENTE/MEDICO/ADMIN).

---

## 🔐 Flujo de autenticación (resumen)

1. Llamar `POST /api/auth/login` con usuario/contraseña.  
2. Copiar el valor `token` de la respuesta.  
3. En Postman, en la pestaña **Headers** o en **Authorization → Bearer Token**, usar:

```
Authorization: Bearer <TU_TOKEN_AQUI>
```

4. Usar ese header en todas las requests que requieran autenticación.

---

## 🧪 Ejemplos completos (requests + responses)

> **Token de ejemplo** (usar exactamente este string en los headers de ejemplo si quieres replicar las respuestas de ejemplo):  
> `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyNzUwMDAwMCwiZXhwIjoxNzI3NTAzNjAwfQ.KKx9M_3zQzfg9UnyYtAbNlGk7GBjsM2a1oQeqrQnF8k`

---

### 1️⃣ Login (obtener token)

**Request**  
- Método: `POST`  
- URL: `http://localhost:8080/api/auth/login`  
- Body (JSON):  

```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 2️⃣ Listar atenciones (ADMIN - requiere token)

**Request**

- Método: `GET`
- URL: `http://localhost:8080/api/atenciones`
- Headers:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response (ejemplo)**

```json
{
  "content": [
    {
      "id": 1,
      "fecha": "2025-09-20",
      "motivo": "Consulta general",
      "paciente": "Juan Pérez",
      "empleado": "Dr. Ana Torres",
      "estado": "ACTIVO"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

### 3️⃣ Ver mis atenciones (PACIENTE - requiere token)

**Request**

- Método: `GET`
- URL: `http://localhost:8080/api/atenciones/mias`
- Headers:

```
Authorization: Bearer <TOKEN_DEL_PACIENTE>
```

**Response (ejemplo)**

```json
[
  {
    "id": 5,
    "fecha": "2025-09-22",
    "motivo": "Control rutinario",
    "empleado": "Dr. Luis Gómez",
    "estado": "ACTIVO"
  }
]
```

---

### 4️⃣ Crear atención (ADMIN/MEDICO - requiere token)

**Request**

- Método: `POST`
- URL: `http://localhost:8080/api/atenciones`
- Headers:

```
Authorization: Bearer <TOKEN_ADMIN_O_MEDICO>
Content-Type: application/json
```

- Body (JSON):

```json
{
  "fecha": "2025-09-28",
  "motivo": "Dolor de cabeza",
  "pacienteId": 2,
  "empleadoId": 1,
  "estado": "ACTIVO"
}
```

**Response**

```json
{
  "id": 6,
  "fecha": "2025-09-28",
  "motivo": "Dolor de cabeza",
  "paciente": "María López",
  "empleado": "Dr. Ana Torres",
  "estado": "ACTIVO"
}
```

---

### 5️⃣ Actualizar atención (ADMIN/MEDICO - requiere token)

**Request**

- Método: `PUT`
- URL: `http://localhost:8080/api/atenciones/6`
- Headers:

```
Authorization: Bearer <TOKEN_ADMIN_O_MEDICO>
Content-Type: application/json
```

- Body (JSON):

```json
{
  "fecha": "2025-09-28",
  "motivo": "Dolor de cabeza - actualizado",
  "pacienteId": 2,
  "empleadoId": 1,
  "estado": "ATENDIDO"
}
```

**Response**

```json
{
  "id": 6,
  "fecha": "2025-09-28",
  "motivo": "Dolor de cabeza - actualizado",
  "paciente": "María López",
  "empleado": "Dr. Ana Torres",
  "estado": "ATENDIDO"
}
```

---

### 6️⃣ Eliminar atención (solo ADMIN - requiere token)

**Request**

- Método: `DELETE`
- URL: `http://localhost:8080/api/atenciones/6`
- Headers:

```
Authorization: Bearer <TOKEN_ADMIN>
```

**Response**

```json
{
  "message": "Atención eliminada correctamente"
}
```

---

## 📋 Casos adicionales (opcional)

### 7️⃣ Filtrar por fecha

**Request**

- Método: `GET`
- URL: `http://localhost:8080/api/atenciones?fecha=2025-09-20`
- Headers:

```
Authorization: Bearer <TOKEN_ADMIN>
```

**Response**

```json
{
  "content": [
    {
      "id": 1,
      "fecha": "2025-09-20",
      "motivo": "Consulta general",
      "paciente": "Juan Pérez",
      "empleado": "Dr. Ana Torres",
      "estado": "ACTIVO"
    }
  ],
  "totalElements": 1
}
```

### 8️⃣ Error 401 (sin token)

**Request**

- Método: `GET`
- URL: `http://localhost:8080/api/atenciones`
- Headers: (sin Authorization)

**Response**

```json
{
  "error": "Unauthorized",
  "message": "Token JWT requerido"
}
```

### 9️⃣ Error 403 (sin permisos)

**Request**

- Método: `DELETE`
- URL: `http://localhost:8080/api/atenciones/1`
- Headers:

```
Authorization: Bearer <TOKEN_DE_PACIENTE>
```

**Response**

```json
{
  "error": "Forbidden",
  "message": "No tienes permisos para realizar esta acción"
}
```

---

## ✅ Pasos para probar en Postman

1. **Importar collection**: Crear una nueva colección llamada "API Atenciones"
2. **Login**: Crear request POST para `/api/auth/login` y obtener el token
3. **Variables**: En la colección, crear variable `token` con el valor obtenido
4. **Headers globales**: En la colección, agregar header `Authorization: Bearer {{token}}`
5. **Probar endpoints**: Crear requests para cada endpoint usando la variable `{{token}}`

---

## 🚀 Notas finales

- **Base URL**: Cambiar `http://localhost:8080` por tu URL real
- **Token expira**: Los tokens JWT tienen tiempo de vida limitado, reloguear si es necesario
- **Roles**: Asegúrate de usar el token del rol correcto para cada operación
- **Content-Type**: No olvides `application/json` en requests POST/PUT

---

¡Listo para usar y probar! 🎯
