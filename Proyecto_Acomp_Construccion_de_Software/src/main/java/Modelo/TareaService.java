package Modelo;

import Modelo.Estado;
import Modelo.Prioridad;
import Modelo.Tarea;

import java.time.LocalDate;
import java.util.*;

public class TareaService {
    private final List<Tarea> tareas = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void agregarTarea(String titulo, String descripcion, LocalDate fechaVencimiento,
                             Prioridad prioridad, Estado estado) {
        if (titulo == null || titulo.trim().isEmpty()) {
            System.out.println("El titulo no puede estar vacio.");
            return;
        }
        if (fechaVencimiento.isBefore(LocalDate.now())) {
            System.out.println("La fecha de vencimiento debe ser hoy o una fecha futura.");
            return;
        }

        Tarea nuevaTarea = new Tarea(titulo.trim(), descripcion, fechaVencimiento, prioridad, estado);
        tareas.add(nuevaTarea);
        System.out.println("Tarea agregada: \n" + nuevaTarea);
    }

    public List<Tarea> obtenerTodas() {
        return new ArrayList<>(tareas);
    }

    public void listarTareas(String criterioOrdenamiento) {
        List<Tarea> copia = new ArrayList<>(tareas);

        switch (criterioOrdenamiento.toLowerCase()) {
            case "fecha" -> copia.sort(Comparator.comparing(Tarea::getFechaVencimiento));
            case "prioridad" -> copia.sort(Comparator.comparing(Tarea::getPrioridad));
            case "" -> {} // sin orden
            default -> System.out.println("Criterio invalido. Se mostrara sin ordenar.");
        }

        String linea = "+----+----------------------+-------------+-----------+-----------------+------------------------------+";
        System.out.println("Lista de Tareas:");
        System.out.println(linea);
        System.out.printf("| %-2s | %-20s | %-11s | %-9s | %-15s | %-28s |\n",
                "ID", "Título", "Estado", "Prioridad", "Vencimiento", "Descripción");
        System.out.println(linea);

        for (Tarea t : copia) {
            System.out.printf("| %-2d | %-20s | %-11s | %-9s | %-15s | %-28s |\n",
                    t.getId(),
                    recortar(t.getTitulo(), 20),
                    t.getEstado(),
                    t.getPrioridad(),
                    t.getFechaVencimiento(),
                    recortar(t.getDescripcion(), 28));
        }
        System.out.println(linea);
    }

    private String recortar(String texto, int max) {
        if (texto == null) return "";
        return texto.length() > max ? texto.substring(0, max - 3) + "..." : texto;
    }


    public void actualizarTarea(int id, String nuevoTitulo, String nuevaDescripcion,
                                LocalDate nuevaFecha, Prioridad nuevaPrioridad, Estado nuevoEstado) {
        Tarea tarea = buscarPorId(id);
        if (tarea == null) {
            System.out.println("No se encontró una tarea con ese ID.");
            return;
        }

        if (nuevoTitulo != null && !nuevoTitulo.trim().isEmpty()) tarea.setTitulo(nuevoTitulo);
        if (nuevaDescripcion != null) tarea.setDescripcion(nuevaDescripcion);
        if (nuevaFecha != null && !nuevaFecha.isBefore(LocalDate.now())) tarea.setFechaVencimiento(nuevaFecha);
        if (nuevaPrioridad != null) tarea.setPrioridad(nuevaPrioridad);
        if (nuevoEstado != null) tarea.setEstado(nuevoEstado);

        System.out.println("Tarea actualizada:\n" + tarea);
    }

    public void eliminarTarea(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea == null) {
            System.out.println("Tarea no encontrada.");
            return;
        }

        System.out.print("¿Estás seguro de que deseas eliminar esta tarea? (Si/No): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();

        if (respuesta.equals("sí") || respuesta.equals("si")) {
            tareas.remove(tarea);
            System.out.println("Tarea eliminada.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private Tarea buscarPorId(int id) {
        return tareas.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
