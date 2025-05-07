package Modelo;

import Modelo.TareaRepository;
import Modelo.Tarea;
import Modelo.Prioridad;
import Modelo.Estado;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TareaRepositoryTest {

    private TareaRepository tareaRepository;

    @BeforeEach
    public void setUp() {
        tareaRepository = new TareaRepository();
    }

    @Test
    public void testAgregarYObtenerTareas() {
        // Crear 2 tareas de prueba
        Tarea tarea1 = new Tarea("Tarea 1", "Descripción 1",
                LocalDate.of(2025, 5, 10), Prioridad.ALTA, Estado.PENDIENTE);

        Tarea tarea2 = new Tarea("Tarea 2", "Descripción 2",
                LocalDate.of(2025, 5, 12), Prioridad.MEDIA, Estado.EN_PROGRESO);

        // Agregar las tareas
        tareaRepository.agregarTarea(tarea1);
        tareaRepository.agregarTarea(tarea2);

        // Obtener tareas desde repositorio
        List<Tarea> tareasObtenidas = tareaRepository.obtenerTareas();

        // Validaciones
        assertTrue(tareasObtenidas.contains(tarea1), "Debe contener tarea1");
        assertTrue(tareasObtenidas.contains(tarea2), "Debe contener tarea2");
    }
}
