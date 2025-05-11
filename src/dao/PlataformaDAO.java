package dao;

import modelo.Plataforma;
import conexion.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlataformaDAO {

    // Método para agregar una plataforma
    public static boolean agregarPlataforma(Plataforma p) {
        String sql = "INSERT INTO platform (platform_name) VALUES (?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getPlatformName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar plataforma: " + e.getMessage());
            return false;
        }
    }

    // Método para listar todas las plataformas
    public static List<Plataforma> listarPlataformas() {
        List<Plataforma> lista = new ArrayList<>();
        String sql = "SELECT * FROM platform";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Plataforma p = new Plataforma();
                p.setPlatformId(rs.getInt("platform_id"));
                p.setDateAdded(rs.getTimestamp("date_added"));
                p.setPlatformName(rs.getString("platform_name"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar plataformas: " + e.getMessage());
        }

        return lista;
    }

    // Método para modificar una plataforma
    public static boolean modificarPlataforma(Plataforma p) {
        String sql = "UPDATE platform SET platform_name = ? WHERE platform_id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getPlatformName());
            stmt.setInt(2, p.getPlatformId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar plataforma: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar una plataforma
    public static boolean eliminarPlataforma(int id) {
        String sql = "DELETE FROM platform WHERE platform_id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar plataforma: " + e.getMessage());
            return false;
        }
    }
}