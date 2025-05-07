package Modelo;

import Modelo.Estado;
import Modelo.Prioridad;
import Modelo.Tarea;

import java.time.LocalDate;
import java.util.*;

/**
 * Servicio que gestiona las operaciones sobre las tareas, como agregar, eliminar, actualizar, y filtrar.
 * <p>
 * Esta clase interactúa con el repositorio para almacenar y recuperar tareas,
 * y proporciona métodos para ordenar y filtrar las tareas según diferentes criterios.
 */
public class TareaService {
    /** Lista de tareas cargadas en memoria. */
    private List<Tarea> tareas = new ArrayList<>();
    
    /** Instancia de Scanner para recibir entradas del usuario. */
    private final Scanner scanner = new Scanner(System.in);
    
    /** Repositorio para persistir las tareas en el almacenamiento (archivo JSON). */
    private final TareaRepository repository;

    /**
     * Crea una instancia del servicio con un repositorio específico.
     *
     * @param repository el repositorio para almacenar las tareas
     */
    public TareaService(TareaRepository repository) {
        this.repository = repository;
        this.tareas = new ArrayList<>(repository.obtenerTareas());
    }

    /**
     * Agrega una nueva tarea al sistema tras validar que el título no esté vacío y que la fecha de vencimiento
     * sea una fecha futura o igual a la actual.
     *
     * @param tarea la tarea a agregar
     */
    public void agregarTarea(Tarea tarea) {
        if (tarea.getTitulo() == null || tarea.getTitulo().trim().isEmpty()) {
            System.out.println("El titulo no puede estar vacio.");
            return;
        }
        if (tarea.getFechaVencimiento().isBefore(LocalDate.now())) {
            System.out.println("La fecha de vencimiento debe ser hoy o una fecha futura.");
            return;
        }

        Tarea nuevaTarea = new Tarea(tarea.getTitulo().trim(), tarea.getDescripcion(), tarea.getFechaVencimiento(), tarea.getPrioridad(), tarea.getEstado());
        tareas.add(nuevaTarea);
        repository.guardarEnArchivo(tareas); // Guardar después de agregar
        System.out.println("Tarea agregada: \n" + nuevaTarea);
    }

    /**
     * Obtiene todas las tareas almacenadas en el sistema.
     *
     * @return lista de todas las tareas
     */
    public List<Tarea> obtenerTodas() {
        return new ArrayList<>(tareas);
    }

    /**
     * Muestra la lista de tareas ordenada según el criterio proporcionado (por fecha o prioridad).
     *
     * @param criterioOrdenamiento el criterio de ordenamiento: "fecha", "prioridad" o vacío para no ordenar
     */
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

    /**
     * Recorta un texto a un tamaño máximo, agregando "..." si es más largo que el límite.
     *
     * @param texto el texto a recortar
     * @param max el tamaño máximo permitido
     * @return el texto recortado
     */
    private String recortar(String texto, int max) {
        if (texto == null) return "";
        return texto.length() > max ? texto.substring(0, max - 3) + "..." : texto;
    }

    /**
     * Guarda las tareas en el archivo utilizando el repositorio.
     */
    public void guardarTareas() {
        repository.guardarEnArchivo(tareas); // Guarda la lista de tareas actualizada
    }

    /**
     * Actualiza una tarea en el sistema según su ID.
     *
     * @param tarea la tarea actualizada
     */
    public void actualizarTarea(Tarea tarea) {
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == tarea.getId()) {
                tareas.set(i, tarea);
                break;
            }
        }
        guardarTareas();
    }

    /**
     * Elimina una tarea del sistema según su ID.
     *
     * @param id el ID de la tarea a eliminar
     */
    public void eliminarTarea(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea != null) {
            tareas.remove(tarea);
            repository.guardarEnArchivo(tareas); // Guardar después de eliminar
        }
    }

    /**
     * Busca una tarea por su ID.
     *
     * @param id el ID de la tarea
     * @return la tarea encontrada o null si no existe
     */
    private Tarea buscarPorId(int id) {
        return tareas.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Filtra las tareas por su estado.
     *
     * @param estado el estado por el que filtrar
     * @return lista de tareas con el estado especificado
     */
    public List<Tarea> filtrarPorEstado(Estado estado) {
        return tareas.stream()
                .filter(t -> t.getEstado() == estado)
                .toList();
    }

    /**
     * Filtra las tareas por su prioridad.
     *
     * @param prioridad la prioridad por la que filtrar
     * @return lista de tareas con la prioridad especificada
     */
    public List<Tarea> filtrarPorPrioridad(Prioridad prioridad) {
        return tareas.stream()
                .filter(t -> t.getPrioridad() == prioridad)
                .toList();
    }

    /**
     * Filtra las tareas por su fecha de vencimiento.
     *
     * @param fecha la fecha de vencimiento por la que filtrar
     * @return lista de tareas con la fecha de vencimiento especificada
     */
    public List<Tarea> filtrarPorFecha(LocalDate fecha) {
        return tareas.stream()
                .filter(t -> t.getFechaVencimiento().equals(fecha))
                .toList();
    }

    /**
     * Busca tareas que contengan una palabra clave en su título o descripción.
     *
     * @param palabra la palabra clave a buscar
     * @return lista de tareas que contienen la palabra clave
     */
    public List<Tarea> buscarPorPalabraClave(String palabra) {
        String palabraClave = palabra.toLowerCase();
        return tareas.stream()
                .filter(t -> t.getTitulo().toLowerCase().contains(palabraClave)
                        || t.getDescripcion().toLowerCase().contains(palabraClave))
                .toList();
    }
}