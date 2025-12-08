
package analisis;

import Logica.entidades.*;
import Logica.gestores.GestorDiccionarios;

import java.util.*;

public class AnalizadorSentimiento {

    private final GestorDiccionarios gestorDiccionarios;

    public AnalizadorSentimiento(GestorDiccionarios gestorDiccionarios) {
        this.gestorDiccionarios = gestorDiccionarios;
    }

    public void analizarTicketCompleto(Ticket ticket) {
        if (ticket == null) {
            System.out.println("Ticket no encontrado.");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("           ANÁLISIS BAG OF WORDS - TICKET #" + ticket.getId());
        System.out.println("=".repeat(70));
        System.out.println("Asunto: " + ticket.getAsunto());
        System.out.println("Descripción: " + ticket.getDescripcion());
        System.out.println("Usuario: " + ticket.getUsuario().getNombreCompleto());
        System.out.println("-".repeat(70));

        List<String> detonantesEmocion = new ArrayList<>();
        List<String> detonantesTecnica = new ArrayList<>();

        String emocion = detectarEmocion(ticket, detonantesEmocion);
        String categoria = detectarCategoriaTecnica(ticket, detonantesTecnica);

        System.out.println("ESTADO DE ÁNIMO DETECTADO: " + emocion);
        if (!detonantesEmocion.isEmpty()) {
            System.out.println("   Palabras detonantes: " + detonantesEmocion);
        }

        System.out.println("\nCATEGORÍA TÉCNICA SUGERIDA: " + categoria);
        if (!detonantesTecnica.isEmpty()) {
            System.out.println("   Palabras detonantes: " + detonantesTecnica);
        }

        System.out.println("=".repeat(70));
    }

    private String detectarEmocion(Ticket ticket, List<String> detonantes) {
        String texto = normalizar(ticket.getDescripcion());
        int positivo = 0, negativo = 0, urgente = 0;

        for (DiccionarioEmocional de : gestorDiccionarios.getEmocionales()) {
            if (texto.contains(de.getPalabra().toLowerCase())) {
                detonantes.add(de.getPalabra());
                switch (de.getEmocion().toLowerCase()) {
                    case "positivo" -> positivo++;
                    case "negativo" -> negativo++;
                    case "urgente"  -> urgente++;
                }
            }
        }

        if (urgente > positivo && urgente >= negativo) return "URGENTE";
        if (negativo > positivo) return "NEGATIVO / FRUSTRADO";
        if (positivo > negativo) return "POSITIVO";
        return "NEUTRAL";
    }

    private String detectarCategoriaTecnica(Ticket ticket, List<String> detonantes) {
        String texto = normalizar(ticket.getDescripcion());
        Map<String, Integer> conteo = new HashMap<>();

        for (DiccionarioTecnico dt : gestorDiccionarios.getTecnicos()) {
            if (texto.contains(dt.getPalabra().toLowerCase())) {
                detonantes.add(dt.getPalabra());
                conteo.merge(dt.getCategoriaTecnica(), 1, Integer::sum);
            }
        }

        if (conteo.isEmpty()) return "No detectada";

        return conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Desconocida")
                .toUpperCase();
    }

    private String normalizar(String texto) {
        return texto.toLowerCase()
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}