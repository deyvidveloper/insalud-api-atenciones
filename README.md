# 🏥 API REST - Gestión de Atenciones Médicas (INSALUD)

Aplicación backend desarrollada en **Java 17 con Spring Boot**, que permite 
gestionar las atenciones médicas de pacientes en el sistema de salud INSALUD.  

El sistema implementa autenticación mediante **JWT**, seguridad con 
**Spring Security**, y se conecta a una base de datos **PostgreSQL** para 
el registro y consulta de datos médicos.  

⚡ **Importante**: Este proyecto es **solo backend**, diseñado para ser 
consumido por un frontend (ejemplo: **Angular**, **React**, **Vue**).  

---

✨ **Características principales**
- 🔑 Autenticación con JWT y control de acceso por roles (ADMIN, MÉDICO, PACIENTE).  
- 📋 Registro, actualización y eliminación de atenciones médicas.  
- 🔍 Consulta personalizada de atenciones con filtros.  
- 📄 Paginación en listados para mejorar el rendimiento.  
- 🧩 Manejo de entidades relacionadas: Persona, Usuario, Paciente, Empleado, Especialidad.  
- ✅ Validaciones integradas con Spring Validation.  

---

🧪 **Tecnologías utilizadas**
- Java 17 ☕  
- Spring Boot 🌱  
- Spring Security 🔐  
- JWT (JSON Web Token)  
- PostgreSQL 🐘  
- JPA / Hibernate ⚙️  
- IntelliJ IDEA 💻  
- Postman (pruebas de endpoints) 📬  

---

📂 **Modelo de datos**
El sistema contempla las siguientes entidades principales:  

- 👤 **Persona**: datos generales (nombre, email, estado).  
- 👨‍⚕️ **Empleado**: enlazado a Persona, con roles (Médico, Admin).  
- 🧑‍🦱 **Paciente**: enlazado a Persona con rol PACIENTE.  
- 🩺 **Especialidad**: catálogo médico (Dermatología, Cardiología, etc).  
- 📌 **Atención**: registro de consultas (fecha, motivo, diagnóstico, estado).  

---

🌐 **Endpoints disponibles**
- `POST /api/auth/login` → autenticación y generación de token JWT.  
- `GET /api/atenciones` → listado de atenciones (ADMIN, con paginación).  
- `GET /api/atenciones/mias` → atenciones del usuario autenticado.  
- `POST /api/atenciones` → registrar atención (ADMIN/MÉDICO).  
- `PUT /api/atenciones/{id}` → actualizar atención (ADMIN/MÉDICO).  
- `DELETE /api/atenciones/{id}` → eliminar atención (solo ADMIN).  

---

🚀 **Pasos para ejecutar el proyecto**
1. Clonar el repositorio desde **GitHub**.  
2. Crear la base de datos `bd_gestion_atenciones` en **pgAdmin**.  
3. Configurar el archivo `application.properties` con los datos de conexión.  
4. Ejecutar el backend desde **IntelliJ IDEA**.  
5. Probar los endpoints en **Postman**, enviando el token JWT en los headers.  
6. (Opcional) Conectar este backend a un frontend desarrollado en 
   **Angular** o **React**.  

---

🗂️ **Base de datos incluida**
Por si acaso, también he dejado mi base de datos que usé para hacer pruebas.  
Esto permitirá que puedan **probar los endpoints en Postman inmediatamente**, 
sin necesidad de crear registros manualmente.  

La BD ya contiene:  
- Roles de prueba (ADMIN, MÉDICO, PACIENTE).  
- Especialidades (Dermatología, Cardiología).  
- Personas de ejemplo.  
- Empleados y pacientes vinculados.  
- Al menos una atención médica registrada.  

---

📖 Para ejemplos en Postman y más detalles ver: [DOCUMENTACION.md](./DOCUMENTACION.md).
