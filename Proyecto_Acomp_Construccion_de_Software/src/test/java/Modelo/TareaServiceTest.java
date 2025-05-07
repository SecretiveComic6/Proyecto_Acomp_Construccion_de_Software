package Modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import Modelo.Tarea;
import Modelo.Prioridad;
import Modelo.Estado;


class TareaServiceTest {

    @Test
    public void testFechaVencimientoNoEsPasada() {
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaFutura = fechaHoy.plusDays(1);
        LocalDate fechaPasada = fechaHoy.minusDays(1);

        // Tarea con fecha futura (válida)
        Tarea tareaValida = new Tarea("Tarea válida", "Descripción", fechaFutura, Prioridad.MEDIA, Estado.PENDIENTE);
        assertTrue(fechaEsValida(tareaValida), "La fecha futura debería ser válida");

        // Tarea con fecha pasada (inválida)
        Tarea tareaInvalida = new Tarea("Tarea inválida", "Descripción", fechaPasada, Prioridad.MEDIA, Estado.PENDIENTE);
        assertFalse(fechaEsValida(tareaInvalida), "La fecha pasada no debería ser válida");
    }

    // Método auxiliar de validación
    private boolean fechaEsValida(Tarea tarea) {
        return !tarea.getFechaVencimiento().isBefore(LocalDate.now());
    }
}
