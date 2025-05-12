package main;
/**
 *
 * @authors Aron Iñiguez Ruiz
 *          Alejandra Monroy
 *          Ian Rodriguez
 */

import conexion.ConexionDB;
import menus.*;
import servicios.*;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        ConexionDB.obtenerConexion();
        
        //Al iniciar, registrar un CHECKP en la bitácora
        //BitacoraService.insertarCheckpoint();

        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestión de Entidades");
            System.out.println("2. Consultas y Listados");
            System.out.println("3. Manejo de Colecciones (Transacciones)");
            System.out.println("4. Restaurar desde Bitácora");
            System.out.println("0. Salir");

            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    menuGestionEntidades();
                    break;
                case 2:
                    menuConsultas();
                    break;
                case 3:
                    menuColecciones();
                    break;
                case 4:
                    //BitacoraService.restaurarDesdeCheckpoint()
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    private static void menuGestionEntidades() {
        while (true) {
            System.out.println("\n--- Gestión de Entidades ---");
            System.out.println("1. Usuarios");
            System.out.println("2. Plataformas");
            System.out.println("3. Videojuegos");
            System.out.println("4. Colecciones");
            System.out.println("0. Volver al menú principal");

            System.out.print("Seleccione una entidad: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    UsuarioMenu.mostrar();
                    break;
                case 2:
                    PlataformaMenu.mostrar();
                    break;
                case 3:
                    JuegoMenu.mostrar();
                    break;
                case 4:
                    ColeccionMenu.mostrar();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("⚠️ Opción inválida.");
            }
        }
    }
    private static void menuConsultas() {
        while (true) {
            System.out.println("\n--- Consultas y Listados ---");
            System.out.println("1. Ver colección completa por usuario");
            System.out.println("2. Videojuegos con más coleccionistas");
            System.out.println("3. Top 5 con mejor rating promedio");
            System.out.println("0. Volver al menú principal");

            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    ConsultaService.mostrarColeccionPorUsuario();
                    break;  
                case 2:
                    ConsultaService.juegosConMasColeccionistas();
                    break;
                case 3:
                    ConsultaService.top5MejorRating();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("⚠️ Opción inválida.");
            }
        }
    }
    private static void menuColecciones() {
        while (true) {
            System.out.println("\n--- Manejo de Colecciones (Transacciones) ---");
            System.out.println("1. Agregar videojuego a colección");
            System.out.println("2. Modificar videojuego en colección");
            System.out.println("3. Eliminar videojuego de colección");
            System.out.println("0. Volver al menú principal");

            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    ColeccionService.agregarJuegoConTransaccion();
                    break;
                case 2: 
                    //ColeccionService.modificarJuegoConTransaccion();
                    break;
                case 3:
                    //ColeccionService.eliminarJuegoConTransaccion();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("⚠️ Opción inválida.");
            }
        }
    }
}