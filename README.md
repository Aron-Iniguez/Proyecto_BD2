## Proyecto Final - Sistema de Administración de Colección de Videojuegos

## Descripción

Este proyecto es una aplicación de consola desarrollada en Java que permite a múltiples usuarios administrar su colección de videojuegos. Utiliza una base de datos MySQL para almacenar y gestionar la información, incluyendo usuarios, plataformas, juegos, y registros de actividad (bitácora).

## Integrantes del equipo

- Iñiguez Ruíz Aron
- Monroy Alvarez Itzel
- Rodriguez Mendoza Ian Manuel

## Link del powerpoint con capturas
https://unisonmx-my.sharepoint.com/:p:/g/personal/a223209555_unison_mx/EdW8K-fmI35DlYrhnYb3LKwBDhbVM5L3NOrX92cIvI6fdA?e=RbvIPv 

## Tecnologías utilizadas

- Java 
- MySQL
- JDBC (MySQL Connector)
- IDE sugerido: VSCode o IntelliJ IDEA

## Estructura del proyecto

## PROYECTO BD2/
├── src/
│   └── main/
│       ├── java/
│       │   ├── ColeccionDAO.java
│       │   ├── ColeccionDB.java
│       │   ├── ConexionDB.java
│       │   ├── JuegoDAO.java
│       │   ├── PrincipalMenu.java
│       │   └── UsuarioDAO.java
│       └── resources/
├── mysql-connector-java-5.1.48-bin.jar
├── .classpath
├── .project
└── settings.json

## Funcionalidades principales

- Gestión de entidades: Agregar, modificar y eliminar (o desactivar) registros de usuarios, plataformas y juegos.
- Consultas:
  - Listar colección de videojuegos por usuario.
  - Mostrar videojuegos con más coleccionistas.
  - Top 5 de videojuegos con mejor calificación promedio.
- Transacciones: Uso de commit y rollback para asegurar integridad en operaciones múltiples.
- Bitácora y restauración:
  - Triggers para registrar operaciones en la tabla log.
  - Restauración del estado de la base de datos desde el último CHECKP.

## Instrucciones de instalación y ejecución

1. Clonar el repositorio:
   git clone https://github.com/Aron-Iniguez/Proyecto_BD2.git

2. Importar el proyecto en tu IDE (Eclipse o IntelliJ).

3. Configurar la base de datos:
   - Crear una base de datos en MySQL.
   - Ejecutar el script videogame_collection.sql para crear las tablas necesarias.

4. Agregar el conector JDBC:
   - Asegúrate de que mysql-connector-java-5.1.48-bin.jar esté incluido en el classpath del proyecto.

5. Ejecutar el programa:
   - Ejecuta la clase PrincipalMenu.java desde tu IDE.

## Declaración de uso de asistentes digitales

Durante el desarrollo de este proyecto, se utilizaron herramientas de asistencia digital como GitHub Copilot y ChatGPT para apoyo en la escritura de código, depuración y documentación.

## Licencia

Este proyecto es de uso académico y no cuenta con una licencia específica.
