CREATE TABLE IF NOT EXISTS Aeropuertos(
Id INTEGER UNIQUE NOT NULL PRIMARY KEY,
Nombre TEXT,
Codigo TEXT);

CREATE TABLE IF NOT EXISTS Vuelos(
NumVuelo INTEGER UNIQUE NOT NULL PRIMARY KEY,
Fecha NUMERIC,
Hora NUMERIC,
Asientos INTEGER);

CREATE TABLE IF NOT EXISTS Compañias(
Id INTEGER UNIQUE NOT NULL PRIMARY KEY,
Nombre TEXT,
PaginaWeb TEXT,
Pais TEXT,
NumTelefono TEXT,
Email TEXT);

CREATE TABLE IF NOT EXISTS Billetes(
Id INTEGER UNIQUE NOT NULL PRIMARY KEY,
Categoria TEXT,
Precio INTEGER,
Reservado NUMERIC,
Pagado NUMERIC,
Confirmado NUMERIC);

CREATE TABLE IF NOT EXISTS Clientes(
Id INTEGER UNIQUE NOT NULL PRIMARY KEY,
Nombre TEXT,
Apellido TEXT,
DNI TEXT,
Email TEXT,
NumTelefono TEXT,
Password TEXT);

CREATE TABLE IF NOT EXISTS "Comapñias-Vuelos"(
IdCompañia INTEGER REFERENCES Compañias,
IdVuelo INTEGER REFERENCES Vuelos);

CREATE TABLE IF NOT EXISTS Empleados(
Id INTEGER UNIQUE NOT NULL PRIMARY KEY,
Nombre TEXT,
Apellido TEXT,
Correo TEXT,
Password TEXT,
Puesto TEXT,
Sueldo INTEGER,
DNI TEXT,
IdAeropuerto INTEGER REFERECES Aeropuertos
);
