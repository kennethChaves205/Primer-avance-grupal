package logica;

import java.util.ArrayList;

public class GestorDiccionarios {

    private ArrayList<DiccionarioTecnico> tecnicos;
    private ArrayList<DiccionarioEmocional> emocionales;

    public GestorDiccionarios() {
        tecnicos = new ArrayList<>();
        emocionales = new ArrayList<>();
    }

    public void agregarTecnico(DiccionarioTecnico dt) {
        tecnicos.add(dt);
    }

    public void agregarEmocional(DiccionarioEmocional de) {
        emocionales.add(de);
    }

    public ArrayList<DiccionarioTecnico> getTecnicos() {
        return tecnicos;
    }

    public ArrayList<DiccionarioEmocional> getEmocionales() {
        return emocionales;
    }
}
