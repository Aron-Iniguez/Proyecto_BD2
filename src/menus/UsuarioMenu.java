package menus;

import dao.UsuarioDAO;
import modelo.Usuario;

import java.util.List;
import java.util.Scanner;

public class UsuarioMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void mostrar() {
        while (true) {
            System.out.println("\n--- Menú de Usuarios ---");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Modificar usuario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Listar usuarios");
            System.out.println("0. Volver");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarUsuario();
                    break;
                case 2:
                    modificarUsuario();
                    break;
                case 3:
                    eliminarUsuario();
                    break;
                case 4:
                    listarUsuarios();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    private static void agregarUsuario() {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("ID de plataforma preferida: ");
        int platformId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tipo de acceso (user/admin): ");
        String accessType = scanner.nextLine();

        Usuario u = new Usuario(username, password, email, platformId, accessType);
        if (UsuarioDAO.agregarUsuario(u)) {
            System.out.println("✅ Usuario agregado correctamente.");
        } else {
            System.out.println("❌ No se pudo agregar el usuario.");
        }
    }

    private static void modificarUsuario() {
        System.out.print("ID del usuario a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nuevo username: ");
        String username = scanner.nextLine();

        System.out.print("Nueva password: ");
        String password = scanner.nextLine();

        System.out.print("Nuevo email: ");
        String email = scanner.nextLine();

        System.out.print("Nuevo ID de plataforma preferida: ");
        int platformId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nuevo tipo de acceso (user/admin): ");
        String accessType = scanner.nextLine();

        Usuario u = new Usuario(username, password, email, platformId, accessType);
        u.setUserId(id);

        if (UsuarioDAO.modificarUsuario(u)) {
            System.out.println("✅ Usuario modificado correctamente.");
        } else {
            System.out.println("❌ No se pudo modificar el usuario.");
        }
    }

    private static void eliminarUsuario() {
        System.out.print("ID del usuario a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (UsuarioDAO.desactivarUsuario(id)) {
            System.out.println("✅ Usuario eliminado correctamente.");
        } else {
            System.out.println("❌ No se pudo eliminar el usuario.");
        }
    }

    private static void listarUsuarios() {
        List<Usuario> lista = UsuarioDAO.listarUsuarios();
        System.out.println("\n--- Lista de Usuarios ---");
        for (Usuario u : lista) {
            System.out.printf("ID: %d, Username: %s, Email: %s, PlataformaID: %d, Acceso: %s\n",
                    u.getUserId(), u.getUsername(), u.getEmail(), u.getPreferredPlatformId(), u.getAccessType());
        }
    }
}
