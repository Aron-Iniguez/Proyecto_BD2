package menus;

import dao.PlataformaDAO;
import modelo.Plataforma;

import java.util.List;
import java.util.Scanner;

public class PlataformaMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void mostrar() {
        while (true) {
            System.out.println("\n--- Menú de Plataformas ---");
            System.out.println("1. Agregar plataforma");
            System.out.println("2. Modificar plataforma");
            System.out.println("3. Eliminar plataforma");
            System.out.println("4. Listar plataformas");
            System.out.println("0. Volver");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarPlataforma();
                    break;
                case 2:
                    modificarPlataforma();
                    break;
                case 3:
                    eliminarPlataforma();
                    break;
                case 4:
                    listarPlataformas();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    private static void agregarPlataforma() {
        System.out.print("Nombre de la plataforma: ");
        String platformName = scanner.nextLine();

        Plataforma p = new Plataforma(platformName);
        if (PlataformaDAO.agregarPlataforma(p)) {
            System.out.println("✅ Plataforma agregada correctamente.");
        } else {
            System.out.println("❌ No se pudo agregar la plataforma.");
        }
    }

    private static void modificarPlataforma() {
        System.out.print("ID de la plataforma a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nuevo nombre de la plataforma: ");
        String newPlatformName = scanner.nextLine();

        Plataforma p = new Plataforma();
        p.setPlatformId(id);
        p.setPlatformName(newPlatformName);

        if (PlataformaDAO.modificarPlataforma(p)) {
            System.out.println("✅ Plataforma modificada correctamente.");
        } else {
            System.out.println("❌ No se pudo modificar la plataforma.");
        }
    }

    private static void eliminarPlataforma() {
        System.out.print("ID de la plataforma a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (PlataformaDAO.eliminarPlataforma(id)) {
            System.out.println("✅ Plataforma eliminada correctamente.");
        } else {
            System.out.println("❌ No se pudo eliminar la plataforma.");
        }
    }

    private static void listarPlataformas() {
        List<Plataforma> lista = PlataformaDAO.listarPlataformas();
        System.out.println("\n--- Lista de Plataformas ---");
        for (Plataforma p : lista) {
            System.out.printf("ID: %d, Nombre: %s, Fecha de adición: %s\n",
                    p.getPlatformId(), p.getPlatformName(), p.getDateAdded());
        }
    }
}