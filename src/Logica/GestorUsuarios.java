package Logica;

import java.util.ArrayList;

public class GestorUsuarios {
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public void agregarUsuario(Usuario usuario) {
        if (buscarUsuarioPorCorreo(usuario.getCorreo()) != null) {
            System.out.println("ERROR: Ya existe un usuario con ese correo.");
        } else {
            usuarios.add(usuario);
            System.out.println("Usuario agregado.");
        }
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
