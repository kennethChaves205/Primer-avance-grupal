
package Logica.gestores;

import Logica.entidades.Usuario;
import persistencia.ConectorDB;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;

public class GestorUsuarios {

    public void agregarUsuario(Usuario usuario) {
        String contrasenaEncriptada = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());
        String sql = "INSERT INTO Usuario (nombreCompleto, correo, contrasena, telefono, rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreCompleto());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, contrasenaEncriptada);
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getRol());

            ps.executeUpdate();
            System.out.println("Usuario guardado con contraseña encriptada.");

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("ERROR: Correo duplicado.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM Usuario WHERE correo = ?";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getString("nombreCompleto"),
                        rs.getString("correo"),
                        rs.getString("contrasena"), // la contraseña está encriptada
                        rs.getString("telefono"),
                        rs.getString("rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = ConectorDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getString("nombreCompleto"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("telefono"),
                        rs.getString("rol")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}