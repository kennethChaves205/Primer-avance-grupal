package Logica;

public class DiccionarioTecnico {
    private String palabra; // Tiene que ser única
    private String categoriaTecnica; // Aquí va, por ejemplo: "redes", "impresoras", "cuentas"

    // Constructor
    public DiccionarioTecnico(String objetoPalabra, String objetoCategoriaTecnica) {
        this.palabra = objetoPalabra;
        this.categoriaTecnica = objetoCategoriaTecnica;
    }

    // Getters
    public String getPalabra() { return palabra; }
    public String getCategoriaTecnica() { return categoriaTecnica; }

    // Setters
    public void setPalabra(String palabra) { this.palabra = palabra; }
    public void setCategoriaTecnica(String categoriaTecnica) { this.categoriaTecnica = categoriaTecnica; }

    // toString
    public String toString() {
        return "\nDiccionarioTecnico{ " +
                "\nPalabra='" + palabra + '\'' +
                "\nCategoría Técnica='" + categoriaTecnica + '\'' +
                "\n"+'}';
    }

    public boolean equals(DiccionarioTecnico diccionarioTecnicoComparar) {
        if (diccionarioTecnicoComparar == null) return false;
        return this.palabra.equals(diccionarioTecnicoComparar.palabra);
    }

}
