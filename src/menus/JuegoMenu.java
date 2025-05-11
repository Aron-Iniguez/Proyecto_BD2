package menus;

import dao.JuegoDAO;
import modelo.Juego;

import java.util.List;
import java.util.Scanner;

public class JuegoMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void mostrar() {
        while (true) {
            System.out.println("\n--- Menú de Juegos ---");
            System.out.println("1. Agregar juego");
            System.out.println("2. Modificar juego");
            System.out.println("3. Eliminar juego");
            System.out.println("4. Listar juegos");
            System.out.println("0. Volver");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarJuego();
                    break;
                case 2:
                    modificarJuego();
                    break;
                case 3:
                    eliminarJuego();
                    break;
                case 4:
                    listarJuegos();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    private static void agregarJuego() {
        System.out.print("Nombre del juego: ");
        String nombre = scanner.nextLine();

        System.out.print("ID de plataforma: ");
        int plataformaId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Año de lanzamiento: ");
        int anio = scanner.nextInt();
        scanner.nextLine();

        System.out.print("URL de la imagen: ");
        String url = scanner.nextLine();

        Juego j = new Juego(nombre, plataformaId, anio, url);
        if (JuegoDAO.agregarJuego(j)) {
            System.out.println("✅ Juego agregado correctamente.");
        } else {
            System.out.println("❌ No se pudo agregar el juego.");
        }
    }

    private static void modificarJuego() {
        System.out.print("ID del juego a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nuevo nombre del juego: ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo ID de plataforma: ");
        int plataformaId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nuevo año de lanzamiento: ");
        int anio = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nueva URL de imagen: ");
        String url = scanner.nextLine();

        Juego j = new Juego(nombre, plataformaId, anio, url);
        j.setGameId(id);

        if (JuegoDAO.modificarJuego(j)) {
            System.out.println("✅ Juego modificado correctamente.");
        } else {
            System.out.println("❌ No se pudo modificar el juego.");
        }
    }

    private static void eliminarJuego() {
        System.out.print("ID del juego a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (JuegoDAO.eliminarJuego(id)) {
            System.out.println("✅ Juego eliminado correctamente.");
        } else {
            System.out.println("❌ No se pudo eliminar el juego.");
        }
    }

    private static void listarJuegos() {
        List<Juego> lista = JuegoDAO.listarJuegos();
        System.out.println("\n--- Lista de Juegos ---");
        for (Juego j : lista) {
            System.out.printf("ID: %d, Nombre: %s, PlataformaID: %d, Año: %d, Imagen: %s\n",
                    j.getGameId(), j.getGameName(), j.getPlatformId(), j.getYearReleased(), j.getImageUrl());
        }
    }
}
