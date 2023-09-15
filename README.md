# Taller
Proyecto para la cátedra Taller de Requerimientos

Observaciones: estoy usando Java 17, y Workbench 8.* para MySQL

Ver en el archivo SpringSecurity.java de Config, la configuración para la base de datos

POSTMAN para pruebas

// POST registrar usuario

http://localhost:8080/register/save

BODY

{
  "firstName": "Johny",
  "lastName": "Doe",
  "email": "johndfffoe@examople.com",
  "password": "password123"
}

------------------------------------------------

//POST apra login

http://localhost:8080/register/login
BODY
{
  "email": "johndfffoe@example.com",
  "password": "password123"
}

------------------------------------------------

// GET listar usuarios registrados

http://localhost:8080/register/users



Actualemnte al registrar un usuario, se le asigna el rol admin.
