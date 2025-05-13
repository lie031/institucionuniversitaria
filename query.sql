CREATE DATABASE IF NOT EXISTS InstitucionUniversitaria;
USE InstitucionUniversitaria;

CREATE TABLE funcionarios (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    tipo_identificacion VARCHAR(20) NOT NULL,
    numero_identificacion VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    estado_civil VARCHAR(20) NOT NULL,
    sexo VARCHAR(10) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fecha_nacimiento DATE NOT NULL
);

CREATE TABLE grupo_familiar (
    id_familiar INT AUTO_INCREMENT PRIMARY KEY,
    id_funcionario INT NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    parentesco VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    FOREIGN KEY (id_funcionario) REFERENCES Funcionario(id_funcionario) ON DELETE CASCADE
);

CREATE TABLE informacion_academica (
    id_academico INT AUTO_INCREMENT PRIMARY KEY,
    id_funcionario INT NOT NULL,
    universidad VARCHAR(100) NOT NULL,
    nivel_estudio VARCHAR(50) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_funcionario) REFERENCES Funcionario(id_funcionario) ON DELETE CASCADE
);

-- POBLADO DE LAS TABLAS

INSERT INTO funcionarios (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento)
VALUES 
('CC', '123456789', 'Juan', 'Pérez', 'Soltero', 'Masculino', 'Calle 123', '3001234567', '1990-05-10'),
('CC', '987654321', 'María', 'Gómez', 'Casada', 'Femenino', 'Avenida 456', '3007654321', '1985-08-22'),
('TI', '111222333', 'Carlos', 'Rodríguez', 'Soltero', 'Masculino', 'Carrera 789', '3001112233', '2000-03-15');

INSERT INTO grupo_familiar (id_funcionario, nombres, apellidos, parentesco, fecha_nacimiento)
VALUES 
(1, 'Ana', 'Pérez', 'Madre', '1960-06-12'),
(1, 'Luis', 'Pérez', 'Hermano', '1995-10-20'),
(2, 'Pedro', 'Gómez', 'Esposo', '1983-02-14'),
(2, 'Camila', 'Gómez', 'Hija', '2012-09-18');

INSERT INTO informacion_academica (id_funcionario, universidad, nivel_estudio, titulo)
VALUES 
(1, 'Universidad Nacional', 'Pregrado', 'Ingeniería de Sistemas'),
(1, 'Universidad de Antioquia', 'Maestría', 'Ciencias de la Computación'),
(2, 'Universidad de Medellín', 'Pregrado', 'Administración de Empresas'),
(3, 'IU Digital de Antioquia', 'Tecnología', 'Desarrollo de Software');
