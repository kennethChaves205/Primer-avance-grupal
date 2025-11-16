package app;

import Logica.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hola, bienvenidos a nuestro proyecto!");

        // =======================
        //  SECCIÓN 1: DEMOSTRACIÓN
        // =======================

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

        // =======================
        //  SECCIÓN 2: MENÚ COMPLETO
        // =======================

        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorDepartamentos gestorDepartamentos = new GestorDepartamentos();
        GestorTickets gestorTickets = new GestorTickets();
        GestorDiccionarios gestorDiccionarios = new GestorDiccionarios();

        // Se agregan también los diccionarios y objetos creados en la demo
        gestorUsuarios.agregarUsuario(usuarioReporta);
        gestorDepartamentos.agregarDepartamento(deptoTecnico);
        gestorTickets.crearTicket(nuevoTicket);
        gestorDiccionarios.agregarTecnico(DicTec);
        gestorDiccionarios.agregarEmocional(DicEmo);

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n========== MENÚ PRINCIPAL ==========");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Registrar departamento");
            System.out.println("3. Crear ticket");
            System.out.println("4. Listar tickets");
            System.out.println("5. Cambiar estado de ticket");
            System.out.println("6. Mostrar diccionarios");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1 -> {
                    System.out.print("Nombre completo: ");
                    String nombre = sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String contrasena = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String tel = sc.nextLine();
                    System.out.print("Rol: ");
                    String rol = sc.nextLine();

                    Usuario u = new Usuario(nombre, correo, contrasena, tel, rol);
                    gestorUsuarios.agregarUsuario(u);
                    System.out.println("Usuario registrado correctamente.");
                }

                case 2 -> {
                    System.out.print("Nombre del departamento: ");
                    String nombre = sc.nextLine();
                    System.out.print("Descripción: ");
                    String desc = sc.nextLine();
                    System.out.print("Contacto: ");
                    String contacto = sc.nextLine();

                    Departamento d = new Departamento(nombre, desc, contacto);
                    gestorDepartamentos.agregarDepartamento(d);
                    System.out.println("Departamento registrado correctamente.");
                }

                case 3 -> {
                    System.out.print("Correo del usuario: ");
                    String correoUsuario = sc.nextLine();
                    Usuario u = gestorUsuarios.buscarUsuarioPorCorreo(correoUsuario);
                    if (u == null) {
                        System.out.println(" Usuario no encontrado.");
                        break;
                    }

                    System.out.print("Departamento destino: ");
                    String nombreDep = sc.nextLine();
                    Departamento d = gestorDepartamentos.buscarPorNombre(nombreDep);
                    if (d == null) {
                        System.out.println(" Departamento no encontrado.");
                        break;
                    }

                    System.out.print("Asunto: ");
                    String asunto = sc.nextLine();
                    System.out.print("Descripción: ");
                    String descripcion = sc.nextLine();
                    System.out.print("Estado inicial (nuevo/en progreso/resuelto): ");
                    String estado = sc.nextLine();

                    Ticket ticket = new Ticket(asunto, descripcion, estado, u, d);
                    gestorTickets.crearTicket(ticket);

                    System.out.println("✔ Ticket creado exitosamente.");
                }

                case 4 -> {
                    System.out.println("\n--- LISTA DE TICKETS ---");
                    for (Ticket t : gestorTickets.getTickets()) {
                        System.out.println(t);
                    }
                }

                case 5 -> {
                    System.out.print("Asunto del ticket: ");
                    String asunto = sc.nextLine();
                    System.out.print("Nuevo estado: ");
                    String nuevoEstado = sc.nextLine();

                    boolean cambio = gestorTickets.cambiarEstado(asunto, nuevoEstado);

                    if (cambio) {
                        System.out.println("4 Estado actualizado.");
                    } else {
                        System.out.println(" Ticket no encontrado.");
                    }
                }

                case 6 -> {
                    System.out.println("\n--- DICCIONARIO TÉCNICO ---");
                    for (DiccionarioTecnico dt : gestorDiccionarios.getTecnicos()) {
                        System.out.println(dt);
                    }

                    System.out.println("\n--- DICCIONARIO EMOCIONAL ---");
                    for (DiccionarioEmocional de : gestorDiccionarios.getEmocionales()) {
                        System.out.println(de);
                    }
                }

                case 0 -> System.out.println("Saliendo del sistema...");

                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}