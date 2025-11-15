package logica;

import java.util.ArrayList;

public class GestorDepartamentos {
    private ArrayList<Departamento> departamentos;

    public GestorDepartamentos() {
        departamentos = new ArrayList<>();
    }

    public void agregarDepartamento(Departamento d) {
        departamentos.add(d);
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
