package app;
import Logica.Usuario;
import Logica.Departamento;
import Logica.Ticket;
import Logica.DiccionarioTecnico;
import Logica.DiccionarioEmocional;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola, bienvenidos a nuestro proyecto!");

        //Impresión de Usuario y rol
        Usuario usuarioReporta = new Usuario(
                "Antonio González",
                "azaleznio@yahoo.es",
                "contrasena123",
                "8888-1111",
                "Estudiante");

        //Impresión de toString en donde se informa que se creó un usuario
        System.out.println("\n---Se creó un Usuario: ---" + usuarioReporta.toString());

        //Creación de un departamento
        Departamento deptoTecnico = new Departamento(
          "Soporte Técnico",
          "Asistencia con hardware y software.",
          "soporte@ucenfotec.ac.cr"
        );
        //Impresión de toString de Dep en donde se informa que se creó un dep
        System.out.println("\n---Se creó un Departamento: ---" + deptoTecnico.toString());

        //Ingresa un tiquete
        Ticket nuevoTicket = new Ticket(
          "Error de impresora",
          "No puedo imprimir, dice que no está en la red.",
          "nuevo",
          usuarioReporta,
          deptoTecnico
        );
        //Impresión de toString del ticket
        System.out.println("\n---Llegó Ticket nuevo: ---" + nuevoTicket.toString());

        //Aquí se crean nuevas palabras
        DiccionarioTecnico DicTec = new DiccionarioTecnico(
                "imprimir",
                "Impresora");
        DiccionarioEmocional DicEmo = new DiccionarioEmocional(
                "red", "Urgente"
        );

        //Mostramos los diccionarios
        System.out.println("\nDiccionarios Actualizados" +
                "\n" + DicTec.toString() +
                "\n" + DicEmo.toString());


    }
}