
package ui;

import Logica.entidades.*;
import Logica.gestores.*;
import analisis.AnalizadorSentimiento;

import java.util.List;
import java.util.Scanner;

public class MenuConsola {

    // Gestores (lógica de negocio)
    private final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private final GestorDepartamentos gestorDepartamentos = new GestorDepartamentos();
    private final GestorTickets gestorTickets = new GestorTickets();
    private final GestorDiccionarios gestorDiccionarios = new GestorDiccionarios();

    // Análisis BoW
    private final AnalizadorSentimiento analizador = new AnalizadorSentimiento(gestorDiccionarios);

    // Scanner
    private final Scanner sc = new Scanner(System.in);

    // Métodos
    public void iniciar() {
        System.out.println("""
            ╔══════════════════════════════════════════════════╗
            ║   MESA DE AYUDA UNIVERSITARIA - CENFOTEC 2025    ║
            ╚══════════════════════════════════════════════════╝
            """);
        menuPrincipal();
        sc.close();
        System.out.println("¡Hasta luego!");
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
                case 6 -> gestionarDiccionarios();
                case 7 -> analizarSentimientoTicket();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n" + "═".repeat(55));
        System.out.println("                 MENÚ PRINCIPAL");
        System.out.println("═".repeat(55));
        System.out.println("1. Registrar usuario");
        System.out.println("2. Registrar departamento");
        System.out.println("3. Crear ticket");
        System.out.println("4. Listar todos los tickets");
        System.out.println("5. Cambiar estado de un ticket");
        System.out.println("6. Gestionar diccionarios (técnico/emocional)");
        System.out.println("7. Analizar sentimiento de un ticket (Bag of Words)");
        System.out.println("0. Salir");
        System.out.println("═".repeat(55));
    }

    // OPCIONES DEL MENÚ

    private void registrarUsuario() {
        System.out.println("\n--- REGISTRO DE USUARIO ---");
        String nombre = leerCadena("Nombre completo: ");
        String correo = leerCorreo();
        String contrasena = leerCadena("Contraseña: ");
        String telefono = leerCadena("Teléfono: ");
        String rol = leerCadena("Rol (Estudiante/Docente/Administrativo): ");

        Usuario usuario = new Usuario(nombre, correo, contrasena, telefono, rol);
        gestorUsuarios.agregarUsuario(usuario); // ya valida duplicados por correo
    }

    private void registrarDepartamento() {
        System.out.println("\n--- REGISTRO DE DEPARTAMENTO ---");
        String nombre = leerCadena("Nombre del departamento: ");
        String descripcion = leerCadena("Descripción: ");
        String contacto = leerCorreoDepartamento("Correo de contacto del departamento: ");

        Departamento depto = new Departamento(nombre, descripcion, contacto);
        gestorDepartamentos.agregarDepartamento(depto); // ya valida duplicados por nombre
    }

    private void crearTicket() {
        System.out.println("\n--- CREAR NUEVO TICKET ---");

        // 1. Seleccionar usuario
        System.out.println("Usuarios registrados:");
        var usuarios = gestorUsuarios.getUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios. Registre uno primero (opción 1).");
            return;
        }
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i).getNombreCompleto() + " (" + usuarios.get(i).getCorreo() + ")");
        }
        int opcionUsuario = leerEntero("Seleccione usuario por número: ") - 1;
        if (opcionUsuario < 0 || opcionUsuario >= usuarios.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        Usuario usuario = usuarios.get(opcionUsuario);

        // 2. Seleccionar departamento ← AQUÍ ESTÁ LA SOLUCIÓN
        System.out.println("\nDepartamentos disponibles:");
        var departamentos = gestorDepartamentos.getDepartamentos();
        if (departamentos.isEmpty()) {
            System.out.println("No hay departamentos registrados. Registre uno primero (opción 2).");
            return;
        }
        for (int i = 0; i < departamentos.size(); i++) {
            Departamento d = departamentos.get(i);
            System.out.println((i + 1) + ". " + d.getNombre() + " - " + d.getDescripcion());
        }
        int opcionDepto = leerEntero("Seleccione departamento por número: ") - 1;
        if (opcionDepto < 0 || opcionDepto >= departamentos.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        Departamento departamento = departamentos.get(opcionDepto);

        // 3. Resto del ticket
        String asunto = leerCadena("Asunto: ");
        String descripcion = leerCadenaMultilinea("Descripción del problema (escribe FIN para terminar):\n");
        String estado = leerCadena("Estado inicial (nuevo/en progreso/resuelto): ");

        Ticket ticket = new Ticket(asunto, descripcion, estado, usuario, departamento);
        gestorTickets.crearTicket(ticket);

        System.out.println("TICKET CREADO CON ÉXITO - ID: " + ticket.getId());
    }

    private void listarTickets() {
        System.out.println("\n--- LISTA DE TICKETS ---");
        if (gestorTickets.getTickets().isEmpty()) {
            System.out.println("No hay tickets registrados aún.");
            return;
        } else {
            for (Ticket t : gestorTickets.getTickets()) {
                System.out.println(t);
                System.out.println("-".repeat(80));
            }
        }
    }

    private void cambiarEstadoTicket() {
        System.out.println("\n--- CAMBIAR ESTADO DE TICKET ---");

        var tickets = gestorTickets.getTickets();
        if (tickets.isEmpty()) {
            System.out.println("No hay tickets registrados aún.");
            return;
        }

        // Mostrar lista numerada
        mostrarTicketsNumerados(tickets);

        int opcion = leerEntero("Seleccione el número del ticket a modificar: ") - 1;
        if (opcion < 0 || opcion >= tickets.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Ticket ticket = tickets.get(opcion);
        System.out.println("\nTicket seleccionado:");
        System.out.println(ticket);
        System.out.println("Estado actual: " + ticket.getEstado());

        String nuevoEstado = leerCadena("Nuevo estado (nuevo/en progreso/resuelto): ").trim();
        if (gestorTickets.cambiarEstado(ticket.getAsunto(), nuevoEstado)) {
            System.out.println("Estado actualizado correctamente a: " + nuevoEstado);
        } else {
            System.out.println("Error al actualizar estado.");
        }
    }

    // Métodos
    private void mostrarTicketsNumerados(List<Ticket> tickets) {
        System.out.println("Tickets disponibles:");
        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            System.out.printf("%d. [%s] %s - %s (%s)%n",
                    (i + 1),
                    t.getEstado().toUpperCase(),
                    t.getAsunto(),
                    t.getUsuario().getNombreCompleto(),
                    t.getDepartamento().getNombre());
        }
    }

    private void gestionarDiccionarios() {
        System.out.println("\n--- CRUD DICCIONARIOS ---");
        System.out.println("1. Agregar palabra técnica");
        System.out.println("2. Agregar palabra emocional");
        System.out.println("3. Listar técnicos");
        System.out.println("4. Listar emocionales");
        int op = leerEntero("Opción: ");

        switch (op) {
            case 1 -> {
                String palabra = leerCadena("Palabra: ");
                String categoria = leerCadena("Categoría: ");
                gestorDiccionarios.agregarTecnico(new DiccionarioTecnico(palabra, categoria));
            }
            case 2 -> {
                String palabra = leerCadena("Palabra: ");
                String emocion = leerCadena("Emoción (positivo/negativo/urgente): ");
                gestorDiccionarios.agregarEmocional(new DiccionarioEmocional(palabra, emocion));
            }
            case 3 -> {
                System.out.println("Diccionario Técnico:");
                for (DiccionarioTecnico dt : gestorDiccionarios.getTecnicos()) {
                    System.out.println(dt);
                }
            }
            case 4 -> {
                System.out.println("Diccionario Emocional:");
                for (DiccionarioEmocional de : gestorDiccionarios.getEmocionales()) {
                    System.out.println(de);
                }
            }
        }
    }

    private void analizarSentimientoTicket() {
        System.out.println("\n=== ANÁLISIS BAG OF WORDS ===");

        var tickets = gestorTickets.getTickets();
        if (tickets.isEmpty()) {
            System.out.println("No hay tickets registrados aún. Cree uno primero.");
            return;
        }

        // MOSTRAR LISTA NUMERADA
        mostrarTicketsNumerados(tickets);

        int opcion = leerEntero("Seleccione el número del ticket a analizar: ") - 1;
        if (opcion < 0 || opcion >= tickets.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Ticket ticket = tickets.get(opcion);
        analizador.analizarTicketCompleto(ticket);
    }

    // UTILIDADES DE ENTRADA

    private String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private String leerCadenaMultilinea(String mensaje) {
        System.out.println(mensaje);
        StringBuilder sb = new StringBuilder();
        String linea;
        while (!(linea = sc.nextLine()).equalsIgnoreCase("FIN")) {
            sb.append(linea).append("\n");
        }
        return sb.toString().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private String leerCorreo() {
        while (true) {
            String correo = leerCadena("Correo electrónico: ");
            if (correo.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                return correo;
            }
            System.out.println("Formato de correo inválido. Ejemplo: nombre@dominio.com");
        }
    }

    private String leerCorreoExistente(String mensaje) {
        while (true) {
            String correo = leerCadena(mensaje);
            if (gestorUsuarios.buscarUsuarioPorCorreo(correo) != null) {
                return correo;
            }
            System.out.println("Usuario no registrado con ese correo. Intente otro.");
        }
    }

    private String leerCorreoDepartamento(String mensaje) {
        while (true) {
            String correo = leerCadena(mensaje);
            if (correo.isEmpty() || correo.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                return correo;
            }
            System.out.println("Correo inválido o vacío.");
        }
    }
}