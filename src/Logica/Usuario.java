package logica;

public class Usuario {
    private String nombreCompleto;
    private String correo;
    private String contrasena;
    private String telefono;
    private String rol;

    public Usuario(String nombreCompleto, String correo, String contrasena, String telefono, String rol){
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.rol = rol;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) {this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) {this.correo = correo; }

    public String getContraseña() { return contrasena; }
    public void setContraseña(String contraseña) {this.contrasena = contraseña; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {this.telefono = telefono; }

    public String getRol() { return rol; }
    public void setRol(String rol) {this.rol = rol; }

    public String toString() {
        return "Usuario{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}