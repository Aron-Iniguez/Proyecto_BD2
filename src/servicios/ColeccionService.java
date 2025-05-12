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

    
    public static void modificarJuegoConTransaccion() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el ID del usuario: ");
        int userId = sc.nextInt();
        sc.nextLine();

        if (!UsuarioDAO.existeUsuario(userId)) {
            System.out.println("⚠️ Usuario no encontrado.");
            return;
        }

        System.out.print("Ingrese el ID del juego en la colección que desea modificar: ");
        int gameId = sc.nextInt();
        sc.nextLine();

        if (!JuegoDAO.existeJuego(gameId)) {
            System.out.println("⚠️ Juego no encontrado.");
            return;
        }

        System.out.print("Ingrese el nuevo rating (1 a 10): ");
        int nuevoRating = sc.nextInt();
        sc.nextLine();

        if (nuevoRating < 1 || nuevoRating > 10) {
            System.out.println("⚠️ Rating inválido.");
            return;
        }

        String sqlUpdate = "UPDATE game_collection SET rating = ?, date_added = CURRENT_TIMESTAMP WHERE user_id = ? AND game_id = ?";
        try (Connection conn = ConexionDB.obtenerConexion()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setInt(1, nuevoRating);
                ps.setInt(2, userId);
                ps.setInt(3, gameId);

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    // Registrar en la tabla log
                    String sqlLog = "INSERT INTO log (user_id, action_type, table_name, record_id, sql_instruction) VALUES (?, 'UPDATE', 'game_collection', ?, ?)";
                    try (PreparedStatement psLog = conn.prepareStatement(sqlLog)) {
                        psLog.setInt(1, userId);
                        psLog.setInt(2, gameId);
                        psLog.setString(3, sqlUpdate.replace("?", String.valueOf(nuevoRating)).replace("?", String.valueOf(userId)).replace("?", String.valueOf(gameId)));
                        psLog.executeUpdate();
                    }

                    conn.commit();
                    System.out.println("✅ Juego modificado en la colección exitosamente.");
                } else {
                    conn.rollback();
                    System.out.println("⚠️ No se encontró el juego en la colección del usuario.");
                }
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("❌ Error al modificar juego: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
        }
    }

    public static void eliminarJuegoConTransaccion() {
        // Por implementar
    }
}
