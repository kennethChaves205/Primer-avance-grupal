
package Logica.gestores;

import Logica.entidades.Ticket;
import Logica.entidades.Usuario;
import Logica.entidades.Departamento;
import persistencia.ConectorDB;

import java.sql.*;
import java.util.ArrayList;

public class GestorTickets {

    public void crearTicket(Ticket ticket) {
        String sql = "INSERT INTO Ticket (asunto, descripcion, estado, usuario_id, departamento_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ticket.getAsunto());
            ps.setString(2, ticket.getDescripcion());
            ps.setString(3, ticket.getEstado());

            // Obtener IDs de usuario y departamento
            int usuarioId = obtenerIdUsuario(ticket.getUsuario().getCorreo());
            int deptoId = obtenerIdDepartamento(ticket.getDepartamento().getNombre());

            ps.setInt(4, usuarioId);
            ps.setInt(5, deptoId);

            ps.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ticket.setId(rs.getInt(1));
            }

            System.out.println("Ticket creado con éxito en la base de datos. ID: " + ticket.getId());

        } catch (SQLException e) {
            System.err.println("Error al crear ticket: " + e.getMessage());
            if (e.getMessage().contains("foreign key")) {
                System.out.println("Verifique que el usuario y departamento existan.");
            }
        }
    }

    public ArrayList<Ticket> getTickets() {
        ArrayList<Ticket> lista = new ArrayList<>();
        String sql = """
            SELECT t.*, u.*, d.*
            FROM Ticket t
            JOIN Usuario u ON t.usuario_id = u.id
            JOIN Departamento d ON t.departamento_id = d.id
            """;

        try (Connection conn = ConectorDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("u.nombreCompleto"),
                        rs.getString("u.correo"),
                        rs.getString("u.contrasena"),
                        rs.getString("u.telefono"),
                        rs.getString("u.rol")
                );

                Departamento depto = new Departamento(
                        rs.getString("d.nombre"),
                        rs.getString("d.descripcion"),
                        rs.getString("d.contacto")
                );

                Ticket ticket = new Ticket(
                        rs.getString("t.asunto"),
                        rs.getString("t.descripcion"),
                        rs.getString("t.estado"),
                        usuario,
                        depto
                );
                ticket.setId(rs.getInt("t.id"));  // Set ID desde BD

                lista.add(ticket);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar tickets: " + e.getMessage());
        }
        return lista;
    }

    public boolean cambiarEstado(String asunto, String nuevoEstado) {
        String sql = "UPDATE Ticket SET estado = ? WHERE asunto = ?";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setString(2, asunto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al cambiar estado: " + e.getMessage());
            return false;
        }
    }

    public Ticket buscarPorAsunto(String asunto) {
        String sql = """
            SELECT t.*, u.*, d.*
            FROM Ticket t
            JOIN Usuario u ON t.usuario_id = u.id
            JOIN Departamento d ON t.departamento_id = d.id
            WHERE t.asunto = ?
            """;
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, asunto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("u.nombreCompleto"),
                        rs.getString("u.correo"),
                        rs.getString("u.contrasena"),
                        rs.getString("u.telefono"),
                        rs.getString("u.rol")
                );

                Departamento depto = new Departamento(
                        rs.getString("d.nombre"),
                        rs.getString("d.descripcion"),
                        rs.getString("d.contacto")
                );

                Ticket ticket = new Ticket(
                        rs.getString("t.asunto"),
                        rs.getString("t.descripcion"),
                        rs.getString("t.estado"),
                        usuario,
                        depto
                );
                ticket.setId(rs.getInt("t.id"));
                return ticket;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar ticket: " + e.getMessage());
        }
        return null;
    }

    // Métodos auxiliares privados
    private int obtenerIdUsuario(String correo) throws SQLException {
        String sql = "SELECT id FROM Usuario WHERE correo = ?";
        try (PreparedStatement ps = ConectorDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        throw new SQLException("Usuario no encontrado: " + correo);
    }

    private int obtenerIdDepartamento(String nombre) throws SQLException {
        String sql = "SELECT id FROM Departamento WHERE nombre = ?";
        try (PreparedStatement ps = ConectorDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        throw new SQLException("Departamento no encontrado: " + nombre);
    }
}