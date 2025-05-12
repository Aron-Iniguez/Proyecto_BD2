package servicios;

import conexion.ConexionDB;

import java.sql.*;
import java.util.Scanner;

public class ConsultaService {
    private static final Scanner scanner = new Scanner(System.in);

    public static void mostrarColeccionPorUsuario() {
        System.out.print("Ingrese el ID del usuario: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        String sql = """
                SELECT g.game_name, p.platform_name, g.year_released, gc.rating, gc.date_added
                FROM game_collection gc
                JOIN games g ON gc.game_id = g.game_id
                JOIN platform p ON g.platform_id = p.platform_id
                WHERE gc.user_id = ?
                ORDER BY gc.date_added DESC
                """;

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n--- Colección del Usuario (ID: " + userId + ") ---");

                boolean hayResultados = false;

                while (rs.next()) {
                    hayResultados = true;
                    String gameName = rs.getString("game_name");
                    String platform = rs.getString("platform_name");
                    int year = rs.getInt("year_released");
                    int rating = rs.getInt("rating");
                    Timestamp dateAdded = rs.getTimestamp("date_added");

                    System.out.printf("🎮 %s (%s, %d) | Rating: %d | Añadido: %s%n",
                            gameName, platform, year, rating, dateAdded.toLocalDateTime());
                }

                if (!hayResultados) {
                    System.out.println("⚠️ Este usuario no tiene juegos en su colección.");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al consultar la colección: " + e.getMessage());
        }
    }

    // Métodos para opciones 2 y 3
}
