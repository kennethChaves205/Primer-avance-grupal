package Logica;

import java.util.ArrayList;

public class GestorDepartamentos {
    private ArrayList<Departamento> departamentos = new ArrayList<>();

    public void agregarDepartamento(Departamento d) {
        if (buscarPorNombre(d.getNombre()) != null) {
            System.out.println("ERROR: Ya existe un departamento con ese nombre.");
        } else {
            departamentos.add(d);
            System.out.println("Departamento agregado.");
        }
    }

    public Departamento buscarPorNombre(String nombre) {
        for (Departamento d : departamentos) {
            if (d.getNombre().equalsIgnoreCase(nombre)) {
                return d;
            }
        }
        return null;
    }

    public ArrayList<Departamento> getDepartamentos() {
        return departamentos;
    }
}
