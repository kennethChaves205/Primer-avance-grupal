// src/ui/MenuConsola.java
package ui;

import Logica.*;
import java.util.Scanner;

public class MenuConsola {

    private final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private final GestorDepartamentos gestorDepartamentos = new GestorDepartamentos();
    private final GestorTickets gestorTickets = new GestorTickets();
    private final logica.GestorDiccionarios gestorDiccionarios = new logica.GestorDiccionarios();
    private final Scanner sc = new Scanner(System.in);

    public void iniciar() {
        mostrarDemo();
        cargarDatosDemo();
        menuPrincipal();
        sc.close();
    }

    private void mostrarDemo() {
        System.out.println("Hola, bienvenidos a nuestro proyecto!");

        Usuario usuarioReporta = new Usuario("Antonio González", "azaleznio@yahoo.es", "contrasena123", "8888-1111", "Estudiante");
        System.out.println("\n--- Se creó un Usuario: ---\n" + usuarioReporta);

        Departamento deptoTecnico = new Departamento("Soporte Técnico", "Asistencia con hardware y software.", "soporte@ucenfotec.ac.cr");
        System.out.println("\n--- Se creó un Departamento: ---\n" + deptoTecnico);

        Ticket nuevoTicket = new Ticket("Error de impresora", "No puedo imprimir, dice que no está en la red.", "nuevo", usuarioReporta, deptoTecnico);
        System.out.println("\n--- Llegó Ticket nuevo: ---\n" + nuevoTicket);

        DiccionarioTecnico DicTec = new DiccionarioTecnico("imprimir", "Impresora");
        DiccionarioEmocional DicEmo = new DiccionarioEmocional("red", "Urgente");
        System.out.println("\nDiccionarios Actualizados:\n" + DicTec + "\n" + DicEmo);
    }

    private void cargarDatosDemo() {
        Usuario u = new Usuario("Antonio González", "azaleznio@yahoo.es", "contrasena123", "8888-1111", "Estudiante");
        Departamento d = new Departamento("Soporte Técnico", "Asistencia con hardware y software.", "soporte@ucenfotec.ac.cr");
        Ticket t = new Ticket("Error de impresora", "No puedo imprimir, dice que no está en la red.", "nuevo", u, d);
        gestorUsuarios.agregarUsuario(u);
        gestorDepartamentos.agregarDepartamento(d);
        gestorTickets.crearTicket(t);
        gestorDiccionarios.agregarTecnico(new DiccionarioTecnico("imprimir", "Impresora"));
        gestorDiccionarios.agregarEmocional(new DiccionarioEmocional("red", "Urgente"));
    }

    private void menuPrincipal() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> registrarUsuario();
                case 2 -> registrarDepartamento();
                case 3 -> crearTicket();
                case 4 -> listarTickets();
                case 5 -> cambiarEstadoTicket();
                case 6 -> mostrarDiccionarios();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MENÚ PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1. Registrar usuario");
        System.out.println("2. Registrar departamento");
        System.out.println("3. Crear ticket");
        System.out.println("4. Listar tickets");
        System.out.println("5. Cambiar estado de ticket");
        System.out.println("6. Mostrar diccionarios");
        System.out.println("0. Salir");
        System.out.println("=".repeat(50));
    }

    private void registrarUsuario() {
        System.out.println("\n--- REGISTRO DE USUARIO ---");
        String nombre = leerCadena("Nombre completo: ");
        String correo = leerCorreo();
        String contrasena = leerCadena("Contraseña: ");
        String tel = leerCadena("Teléfono: ");
        String rol = leerCadena("Rol (Estudiante/Docente/Admin): ");

        Usuario u = new Usuario(nombre, correo, contrasena, tel, rol);
        if (gestorUsuarios.buscarUsuarioPorCorreo(correo) != null) {
            System.out.println("ERROR: Ya existe un usuario con ese correo.");
        } else {
            gestorUsuarios.agregarUsuario(u);
            System.out.println("USUARIO REGISTRADO CORRECTAMENTE.");
        }
    }

    private void registrarDepartamento() {
        System.out.println("\n--- REGISTRO DE DEPARTAMENTO ---");
        String nombre = leerCadena("Nombre del departamento: ");
        String desc = leerCadena("Descripción: ");
        String contacto = leerCadena("Contacto (correo): ");

        Departamento d = new Departamento(nombre, desc, contacto);
        if (gestorDepartamentos.buscarPorNombre(nombre) != null) {
            System.out.println("ERROR: Ya existe un departamento con ese nombre.");
        } else {
            gestorDepartamentos.agregarDepartamento(d);
            System.out.println("DEPARTAMENTO REGISTRADO CORRECTAMENTE.");
        }
    }

    private void crearTicket() {
        System.out.println("\n--- CREAR TICKET ---");
        String correo = leerCadena("Correo del usuario: ");
        Usuario u = gestorUsuarios.buscarUsuarioPorCorreo(correo);
        if (u == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        String nombreDep = leerCadena("Nombre del departamento: ");
        Departamento d = gestorDepartamentos.buscarPorNombre(nombreDep);
        if (d == null) {
            System.out.println("Departamento no encontrado.");
            return;
        }

        String asunto = leerCadena("Asunto: ");
        String descripcion = leerCadena("Descripción: ");
        String estado = leerCadena("Estado inicial: ");

        Ticket t = new Ticket(asunto, descripcion, estado, u, d);
        gestorTickets.crearTicket(t);
        System.out.println("TICKET CREADO CON ÉXITO. ID: " + t.getId());
    }

    private void listarTickets() {
        System.out.println("\n--- LISTA DE TICKETS ---");
        if (gestorTickets.getTickets().isEmpty()) {
            System.out.println("No hay tickets.");
        } else {
            for (Ticket t : gestorTickets.getTickets()) {
                System.out.println(t);
                System.out.println("-".repeat(60));
            }
        }
    }

    private void cambiarEstadoTicket() {
        System.out.println("\n--- CAMBIAR ESTADO ---");
        String asunto = leerCadena("Asunto del ticket: ");
        String nuevoEstado = leerCadena("Nuevo estado: ");
        if (gestorTickets.cambiarEstado(asunto, nuevoEstado)) {
            System.out.println("Estado actualizado.");
        } else {
            System.out.println("Ticket no encontrado.");
        }
    }

    private void mostrarDiccionarios() {
        System.out.println("\n--- DICCIONARIO TÉCNICO ---");
        for (DiccionarioTecnico dt : gestorDiccionarios.getTecnicos()) {
            System.out.println(dt);
        }
        System.out.println("\n--- DICCIONARIO EMOCIONAL ---");
        for (DiccionarioEmocional de : gestorDiccionarios.getEmocionales()) {
            System.out.println(de);
        }
    }

    // =================== UTILIDADES ===================

    private String leerCadena(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private String leerCorreo() {
        while (true) {
            String correo = leerCadena("Correo electrónico: ");
            if (correo.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                return correo;
            }
            System.out.println("Correo inválido.");
        }
    }
}