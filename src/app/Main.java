package app;
import Logica.Usuario;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola, bienvenidos a nuestro proyecto!");

        //Impresión de Usuario y rol
        Usuario nuevoUsuario = new Usuario(
                "Antonio González",
                "azaleznio@yahoo.es",
                "contrasena123",
                "8888-1111",
                "Admin");

        //Impresión de toString
        System.out.println("Creación Usuario\n" +
                "Nombre del Usuario: " + nuevoUsuario.getNombreCompleto() +
                "\nRol: " + nuevoUsuario.getRol() +
                "\n" + nuevoUsuario.toString());

        //Cambio de rol
        nuevoUsuario.setRol("Vendedor");
        System.out.println("\nNuevo rol de: " + nuevoUsuario.getNombreCompleto() + ": " + nuevoUsuario.getRol());

        //toString Actualizado
        System.out.println("Creación Usuario\n" +
                "Nombre del Usuario: " + nuevoUsuario.getNombreCompleto() +
                "\nRol: " + nuevoUsuario.getRol() +
                "\n" + nuevoUsuario.toString());

    }
}