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

    public static void juegosConMasColeccionistas() {
        String sql = """
                SELECT g.game_name, p.platform_name, COUNT(gc.user_id) AS coleccionistas
                FROM game_collection gc
                JOIN games g ON gc.game_id = g.game_id
                JOIN platform p ON g.platform_id = p.platform_id
                GROUP BY g.game_id, g.game_name, p.platform_name
                ORDER BY coleccionistas DESC
                """;

        try (Connection conn = ConexionDB.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Videojuegos con más coleccionistas ---");

            boolean hayResultados = false;

            while (rs.next()) {
                hayResultados = true;
                String gameName = rs.getString("game_name");
                String platform = rs.getString("platform_name");
                int count = rs.getInt("coleccionistas");

                System.out.printf("🎮 %s (%s) → Coleccionistas: %d%n", gameName, platform, count);
            }

            if (!hayResultados) {
                System.out.println("⚠️ No hay juegos en colecciones.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al consultar los juegos con más coleccionistas: " + e.getMessage());
        }
    }
    
    public static void top5MejorRating() {
        String sql = """
                SELECT g.game_name, p.platform_name, ROUND(AVG(gc.rating), 2) AS promedio_rating
                FROM game_collection gc
                JOIN games g ON gc.game_id = g.game_id
                JOIN platform p ON g.platform_id = p.platform_id
                GROUP BY g.game_id, g.game_name, p.platform_name
                HAVING COUNT(gc.rating) > 0
                ORDER BY promedio_rating DESC
                LIMIT 5
                """;

        try (Connection conn = ConexionDB.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Top 5 Juegos con Mejor Rating Promedio ---");

            boolean hayResultados = false;

            while (rs.next()) {
                hayResultados = true;
                String nombre = rs.getString("game_name");
                String plataforma = rs.getString("platform_name");
                double promedio = rs.getDouble("promedio_rating");

                System.out.printf("⭐ %s (%s) → Promedio: %.2f%n", nombre, plataforma, promedio);
            }

            if (!hayResultados) {
                System.out.println("⚠️ No hay juegos con calificaciones suficientes.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener el top 5 de juegos mejor calificados: " + e.getMessage());
        }
    }
}
