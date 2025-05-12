package dao;

import conexion.ConexionDB;
import modelo.Coleccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColeccionDAO {

    public static boolean agregar(Coleccion c) {
        String sql = "INSERT INTO game_collection (user_id, game_id, rating) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getUserId());
            stmt.setInt(2, c.getGameId());
            stmt.setInt(3, c.getRating());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al agregar juego a la colección: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminar(int collectionId) {
        String sql = "DELETE FROM game_collection WHERE collection_id = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, collectionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar de la colección: " + e.getMessage());
            return false;
        }
    }

    public static List<Coleccion> listar() {
        List<Coleccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM game_collection";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Coleccion c = new Coleccion();
                c.setCollectionId(rs.getInt("collection_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setGameId(rs.getInt("game_id"));
                c.setDateAdded(rs.getTimestamp("date_added").toLocalDateTime());
                c.setRating(rs.getInt("rating"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar colecciones: " + e.getMessage());
        }

        return lista;
    }

    public static boolean existeJuegoEnColeccion(int userId, int gameId) {
        String sql = "SELECT COUNT(*) FROM game_collection WHERE user_id = ? AND game_id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, gameId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.err.println("Error comprobando colección: " + e.getMessage());
        }
        return false;
    }
}
