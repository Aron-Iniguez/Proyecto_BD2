package dao;

import modelo.Juego;
import conexion.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JuegoDAO {

    public static boolean agregarJuego(Juego j) {
        String sql = "INSERT INTO games (game_name, platform_id, year_released, image_url) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, j.getGameName());
            stmt.setInt(2, j.getPlatformId());
            stmt.setInt(3, j.getYearReleased());
            stmt.setString(4, j.getImageUrl());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar juego: " + e.getMessage());
            return false;
        }
    }

    public static List<Juego> listarJuegos() {
        List<Juego> lista = new ArrayList<>();
        String sql = "SELECT * FROM games";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Juego j = new Juego();
                j.setGameId(rs.getInt("game_id"));
                j.setGameName(rs.getString("game_name"));
                j.setPlatformId(rs.getInt("platform_id"));
                j.setYearReleased(rs.getInt("year_released"));
                j.setImageUrl(rs.getString("image_url"));
                lista.add(j);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar juegos: " + e.getMessage());
        }

        return lista;
    }

    public static boolean modificarJuego(Juego j) {
        String sql = "UPDATE games SET game_name = ?, platform_id = ?, year_released = ?, image_url = ? WHERE game_id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, j.getGameName());
            stmt.setInt(2, j.getPlatformId());
            stmt.setInt(3, j.getYearReleased());
            stmt.setString(4, j.getImageUrl());
            stmt.setInt(5, j.getGameId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar juego: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarJuego(int id) {
        String sql = "DELETE FROM games WHERE game_id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar juego: " + e.getMessage());
            return false;
        }
    }
}
