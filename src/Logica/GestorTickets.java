package logica;

import java.util.ArrayList;

public class GestorTickets {

    private ArrayList<Ticket> tickets;

    public GestorTickets() {
        tickets = new ArrayList<>();
    }

    public void crearTicket(Ticket t) {
        tickets.add(t);
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public boolean cambiarEstado(String asunto, String nuevoEstado) {
        for (Ticket t : tickets) {
            if (t.getAsunto().equalsIgnoreCase(asunto)) {
                t.setEstado(nuevoEstado);
                return true;
            }
        }
        return false;
    }
}
