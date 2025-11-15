package Logica;

public class DiccionarioEmocional {
    private String palabra; // Tiene que ser única
    private String emocion; // Aquí podría ir por ej: "frustración", "urgencia", "neutralidad"

    // Constructor
    public DiccionarioEmocional(String objetoPalabra, String objetoEmocion) {
        this.palabra = objetoPalabra;
        this.emocion = objetoEmocion;
    }

    // Getters
    public String getPalabra() { return palabra; }
    public String getEmocion() { return emocion; }

    // Setters
    public void setPalabra(String palabra) { this.palabra = palabra; }
    public void setEmocion(String emocion) { this.emocion = emocion; }

    // toString
    public String toString() {
        return "\nDiccionarioEmocional{ " +
                "\nPalabra='" + palabra + '\'' +
                "\nEmoción='" + emocion + '\'' +
                "\n"+'}';
    }

    public boolean equals(DiccionarioEmocional diccionarioEmocionalComparar) {
        if (diccionarioEmocionalComparar == null) return false;
        return this.palabra.equals(diccionarioEmocionalComparar.palabra);
    }

}
