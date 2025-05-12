package servicios;

import conexion.ConexionDB;
import dao.*;

import java.sql.*;
import java.util.Scanner;

public class ColeccionService {
    private static final Scanner sc = new Scanner(System.in);

    public static void agregarJuegoConTransaccion() {
        Connection conn = null;

        try {
            conn = ConexionDB.obtenerConexion();
            conn.setAutoCommit(false); // Inicia la transacción

            System.out.print("Ingrese el ID del usuario: ");
            int userId = sc.nextInt();
            sc.nextLine();

            if (!UsuarioDAO.existeUsuario(userId)) {
                System.out.println("❌ El usuario no existe.");
                return;
            }

            System.out.print("Ingrese el ID del juego: ");
            int gameId = sc.nextInt();
            sc.nextLine();

            if (!JuegoDAO.existeJuego(gameId)) {
                System.out.println("❌ El juego no existe.");
                return;
            }

            if (ColeccionDAO.existeJuegoEnColeccion(userId, gameId)) {
                System.out.println("⚠️ El juego ya está en la colección de este usuario.");
                return;
            }

            System.out.print("Ingrese la calificación (1 a 10): ");
            int rating = sc.nextInt();
            sc.nextLine();

            if (rating < 1 || rating > 10) {
                System.out.println("❌ Calificación inválida.");
                return;
            }

            // Insertar en la colección
            String insertSql = "INSERT INTO game_collection (user_id, game_id, rating) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setInt(2, gameId);
                ps.setInt(3, rating);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                int collectionId = -1;
                if (rs.next()) {
                    collectionId = rs.getInt(1);
                }

                // Insertar en el log
                String logSql = "INSERT INTO log (user_id, action_type, table_name, record_id, sql_instruction) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                    logStmt.setInt(1, userId);
                    logStmt.setString(2, "INSERT");
                    logStmt.setString(3, "game_collection");
                    logStmt.setInt(4, collectionId);
                    logStmt.setString(5, "INSERT INTO game_collection (user_id, game_id, rating) VALUES (" +
                            userId + ", " + gameId + ", " + rating + ")");
                    logStmt.executeUpdate();
                }

                conn.commit();
                System.out.println("✅ Juego agregado exitosamente a la colección.");
            }

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("⚠️ Error al hacer rollback: " + ex.getMessage());
            }
            System.err.println("❌ Error al agregar juego a colección: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("⚠️ Error al restaurar autoCommit: " + e.getMessage());
            }
        }
    }

    // Métodos pendientes a implementar
    public static void modificarJuegoConTransaccion() {
        // Por implementar
    }

    public static void eliminarJuegoConTransaccion() {
        // Por implementar
    }
}
