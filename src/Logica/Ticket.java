package Logica;

public class Ticket {
    private static int contador = 1;
    private int id;
    private String asunto;
    private String descripcion;
    private String estado;
    private Usuario usuario;
    private Departamento departamento;

    public Ticket(String asunto, String descripcion, String estado, Usuario usuario, Departamento departamento) {
        this.id = contador++;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = estado;
        this.usuario = usuario;
        this.departamento = departamento;
    }

    public int getId() { return id; }
    public String getAsunto() { return asunto; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }
    public Usuario getUsuario() { return usuario; }
    public Departamento getDepartamento() { return departamento; }

    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Ticket #" + id + " {\n" +
                "  Asunto: '" + asunto + "'\n" +
                "  Estado: '" + estado + "'\n" +
                "  Usuario: '" + usuario.getNombreCompleto() + "' (" + usuario.getCorreo() + ")\n" +
                "  Departamento: '" + departamento.getNombre() + "'\n" +
                "  Descripción: '" + descripcion + "'\n" +
                '}';
    }

    public boolean equals(Ticket otro) {
        if (otro == null) return false;
        return this.id == otro.id;
    }
}