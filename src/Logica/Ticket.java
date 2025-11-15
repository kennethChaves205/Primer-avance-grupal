package Logica;

public class Ticket {
    private String asunto;
    private String descripcion;
    private String estado; // Aquí tendríamos que escribir: "nuevo", "en progreso", "resuelto"
    private Usuario usuario; // Este es para leer el usuario
    private Departamento departamento; // Este es para leer el departamento
    private static int contador = 1;
    private int id;

    // Constructor
    public Ticket(String objetoAsunto, String objetoDescripcion, String objetoEstado, Usuario usuario, Departamento departamento) {
        id = contador++;
        asunto = objetoAsunto;
        descripcion = objetoDescripcion;
        estado = objetoEstado;
        this.usuario = usuario;
        this.departamento = departamento;
    }

    // Getters
    public String getAsunto() { return asunto; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }
    public Usuario getUsuario() { return usuario; }
    public Departamento getDepartamento() { return departamento; }

    // Setters
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    // toString
    public String toString() {
        return "\nTicket{ " +
                "\nAsunto='" + asunto + '\'' +
                "\nEstado='" + estado + '\'' +
                "\nUsuario='" + usuario.getCorreo() +
                "\nDepartamento='" + departamento.getNombre() +
                "\n"+'}';
    }

    public boolean equals(Ticket ticketComparar) {
        if (ticketComparar == null) return false;
        return this.id == ticketComparar.id;
    }

    public int getId() {
        return id;
    }

}
