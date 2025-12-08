package Logica.entidades;

public class Departamento {
    private String nombre; //Tiene que ser único
    private String descripcion;
    private String contacto;

    //Constructor
    public Departamento(String objetoNombre, String objetoDescripcion, String objetoContacto){
        this.nombre = objetoNombre;
        this.descripcion = objetoDescripcion;
        this.contacto = objetoContacto;
    }

    //Getters
    public String getNombre() { return nombre;}
    public String getDescripcion() {return descripcion;}
    public String getContacto() {return contacto;}

    //Setters
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setContacto(String contacto) {this.contacto = contacto;}

    //toString
    public String toString() {
        return "\nDepartamento{ " +
                "\nNombre='" + nombre + '\'' +
                "\nDescripcion='" + descripcion + '\'' +
                "\nContacto='" + contacto + '\'' +
                "\n"+'}';
    }

    public boolean equals(Departamento departamentoComparar) {
        if (departamentoComparar == null) return false;
        return this.nombre.equals(departamentoComparar.nombre);
    } //equals

}
