package menus;

import dao.ColeccionDAO;
import modelo.Coleccion;

import java.util.List;
import java.util.Scanner;

public class ColeccionMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void mostrar() {
        while (true) {
            System.out.println("\n--- Menú de Colección ---");
            System.out.println("1. Agregar juego a colección");
            System.out.println("2. Eliminar juego de colección");
            System.out.println("3. Listar colección");
            System.out.println("0. Volver");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> agregar();
                case 2 -> eliminar();
                case 3 -> listar();
                case 0 -> { return; }
                default -> System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    private static void agregar() {
        System.out.print("ID del usuario: ");
        int userId = scanner.nextInt();

        System.out.print("ID del juego: ");
        int gameId = scanner.nextInt();

        System.out.print("Rating (1-10): ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        Coleccion c = new Coleccion(userId, gameId, rating);
        if (ColeccionDAO.agregar(c)) {
            System.out.println("✅ Juego agregado a la colección.");
        } else {
            System.out.println("❌ No se pudo agregar.");
        }
    }

    private static void eliminar() {
        System.out.print("ID de la colección a eliminar: ");
        int collectionId = scanner.nextInt();
        scanner.nextLine();

        if (ColeccionDAO.eliminar(collectionId)) {
            System.out.println("✅ Juego eliminado de la colección.");
        } else {
            System.out.println("❌ No se pudo eliminar.");
        }
    }

    private static void listar() {
        List<Coleccion> lista = ColeccionDAO.listar();
        System.out.println("\n--- Colección de Juegos ---");
        for (Coleccion c : lista) {
            System.out.printf("ID Colección: %d | Usuario ID: %d | Juego ID: %d | Rating: %d | Fecha Agregado: %s\n",
                    c.getCollectionId(), c.getUserId(), c.getGameId(), c.getRating(), c.getDateAdded());
        }
    }
}
