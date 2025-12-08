// src/logica/gestores/GestorDepartamentos.java
package Logica.gestores;

import Logica.entidades.Departamento;
import persistencia.ConectorDB;
import java.sql.*;
import java.util.ArrayList;

public class GestorDepartamentos {
    public void agregarDepartamento(Departamento d) {
        String sql = "INSERT INTO Departamento (nombre, descripcion, contacto) VALUES (?, ?, ?)";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getDescripcion());
            ps.setString(3, d.getContacto());
            ps.executeUpdate();
            System.out.println("Departamento guardado en base de datos.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // duplicado
                System.out.println("ERROR: Ya existe un departamento con ese nombre.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public Departamento buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM Departamento WHERE nombre = ?";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Departamento(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("contacto")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Departamento> getDepartamentos() {
        ArrayList<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Departamento";
        try (Connection conn = ConectorDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Departamento(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("contacto")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}