
package Logica.gestores;

import Logica.entidades.DiccionarioEmocional;
import Logica.entidades.DiccionarioTecnico;
import persistencia.ConectorDB;

import java.sql.*;
import java.util.ArrayList;

public class GestorDiccionarios {

    public void agregarTecnico(DiccionarioTecnico dt) {
        if (existePalabraTecnica(dt.getPalabra())) {
            System.out.println("ERROR: Palabra técnica ya existe.");
            return;
        }
        String sql = "INSERT INTO DiccionarioTecnico (palabra, categoriaTecnica) VALUES (?, ?)";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dt.getPalabra());
            ps.setString(2, dt.getCategoriaTecnica());
            ps.executeUpdate();
            System.out.println("Palabra técnica agregada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarEmocional(DiccionarioEmocional de) {
        if (existePalabraEmocional(de.getPalabra())) {
            System.out.println("ERROR: Palabra emocional ya existe.");
            return;
        }
        String sql = "INSERT INTO DiccionarioEmocional (palabra, emocion) VALUES (?, ?)";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, de.getPalabra());
            ps.setString(2, de.getEmocion());
            ps.executeUpdate();
            System.out.println("Palabra emocional agregada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DiccionarioTecnico> getTecnicos() {
        ArrayList<DiccionarioTecnico> lista = new ArrayList<>();
        String sql = "SELECT * FROM DiccionarioTecnico";
        try (Connection conn = ConectorDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new DiccionarioTecnico(
                        rs.getString("palabra"),
                        rs.getString("categoriaTecnica")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<DiccionarioEmocional> getEmocionales() {
        ArrayList<DiccionarioEmocional> lista = new ArrayList<>();
        String sql = "SELECT * FROM DiccionarioEmocional";
        try (Connection conn = ConectorDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new DiccionarioEmocional(
                        rs.getString("palabra"),
                        rs.getString("emocion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Métodos para palabras únicas
    private boolean existePalabraTecnica(String palabra) {
        String sql = "SELECT COUNT(*) FROM DiccionarioTecnico WHERE palabra = ?";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, palabra);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean existePalabraEmocional(String palabra) {
        String sql = "SELECT COUNT(*) FROM DiccionarioEmocional WHERE palabra = ?";
        try (Connection conn = ConectorDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, palabra);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}