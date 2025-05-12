package servicios;

import conexion.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BitacoraService {

    public static void insertarCheckpoint() {
        String sql = "INSERT INTO log (user_id, action_type, table_name, record_id, sql_instruction) VALUES (NULL, 'CHECKPOINT', NULL, NULL, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String mensaje = "CHECKPOINT: " + LocalDateTime.now();
            ps.setString(1, mensaje);
            ps.executeUpdate();

            System.out.println("✅ Checkpoint registrado en la bitácora.");
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar checkpoint: " + e.getMessage());
        }
    }

    public static void restaurarDesdeCheckpoint() {
        System.out.println("♻️ Restaurando acciones desde el último checkpoint...");

        String sqlCheckpoint = "SELECT log_id FROM log WHERE action_type = 'CHECKPOINT' ORDER BY timestamp DESC LIMIT 1";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement psCheckpoint = conn.prepareStatement(sqlCheckpoint);
             var rsCheckpoint = psCheckpoint.executeQuery()) {

            if (!rsCheckpoint.next()) {
                System.out.println("⚠️ No se encontró ningún checkpoint en la bitácora.");
                return;
            }

            int ultimoCheckpointId = rsCheckpoint.getInt("log_id");

            String sqlLogs = "SELECT * FROM log WHERE log_id > ? ORDER BY log_id DESC";

            try (PreparedStatement psLogs = conn.prepareStatement(sqlLogs)) {
                psLogs.setInt(1, ultimoCheckpointId);
                var rsLogs = psLogs.executeQuery();

                int restauradas = 0;

                while (rsLogs.next()) {
                    String instruccion = rsLogs.getString("sql_instruction");

                    if (instruccion != null && !instruccion.trim().isEmpty()) {
                        try (PreparedStatement psRestore = conn.prepareStatement(instruccion)) {
                            psRestore.executeUpdate();
                            restauradas++;
                        } catch (SQLException e) {
                            System.out.println("⚠️ No se pudo restaurar una instrucción: " + instruccion);
                        }
                    }
                }

                System.out.println("✅ Restauración completa. Instrucciones ejecutadas: " + restauradas);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al restaurar desde bitácora: " + e.getMessage());
        }
    }
}
