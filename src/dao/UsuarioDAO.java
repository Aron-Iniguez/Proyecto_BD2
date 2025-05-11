package dao;

import modelo.Usuario;
import conexion.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static boolean agregarUsuario(Usuario u) {
        String sql = "INSERT INTO users (username, password, email, preferred_platform_id, access_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());
            stmt.setInt(4, u.getPreferredPlatformId());
            stmt.setString(5, u.getAccessType());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
            return false;
        }
    }

    public static List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
                u.setPreferredPlatformId(rs.getInt("preferred_platform_id"));
                u.setAccessType(rs.getString("access_type"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    public static boolean modificarUsuario(Usuario u) {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, preferred_platform_id = ?, access_type = ? WHERE user_id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());
            stmt.setInt(4, u.getPreferredPlatformId());
            stmt.setString(5, u.getAccessType());
            stmt.setInt(6, u.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar usuario: " + e.getMessage());
            return false;
        }
    }

    public static boolean desactivarUsuario(int id) {
        // Desactivar (borrado lógico): en este ejemplo lo eliminamos físicamente
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al desactivar usuario: " + e.getMessage());
            return false;
        }
    }
}
