package Modelo;

import java.time.LocalDate;

/**
 * Representa una tarea con atributos como título, descripción, fecha de vencimiento,
 * prioridad y estado.
 * <p>
 * Cada tarea tiene un identificador único que se asigna automáticamente al crearla.
 */
public class Tarea {
    /** Contador utilizado para generar IDs únicos para cada tarea. */
    private static int contadorId = 1;

    /** Identificador único de la tarea. */
    private int id;

    /** Título breve que describe la tarea. */
    private String titulo;

    /** Descripción detallada de la tarea. */
    private String descripcion;

    /** Fecha límite para completar la tarea. */
    private LocalDate fechaVencimiento;

    /** Prioridad asignada a la tarea. */
    private Prioridad prioridad;

    /** Estado actual de la tarea. */
    private Estado estado;

    /**
     * Crea una nueva instancia de {@code Tarea} con los valores especificados.
     *
     * @param titulo el título de la tarea
     * @param descripcion la descripción de la tarea
     * @param fechaVencimiento la fecha de vencimiento de la tarea
     * @param prioridad la prioridad de la tarea
     * @param estado el estado actual de la tarea
     */
    public Tarea(String titulo, String descripcion, LocalDate fechaVencimiento,
                 Prioridad prioridad, Estado estado) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    /** Crea una instancia vacía de {@code Tarea}. */
    public Tarea() {
        // constructor vacío
    }

    /**
     * Obtiene el identificador único de la tarea.
     *
     * @return el ID de la tarea
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el título de la tarea.
     *
     * @return el título de la tarea
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título de la tarea.
     *
     * @param titulo el nuevo título de la tarea
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la descripción de la tarea.
     *
     * @return la descripción de la tarea
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la tarea.
     *
     * @param descripcion la nueva descripción de la tarea
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha de vencimiento de la tarea.
     *
     * @return la fecha de vencimiento
     */
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Establece la fecha de vencimiento de la tarea.
     *
     * @param fechaVencimiento la nueva fecha de vencimiento
     */
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Obtiene la prioridad de la tarea.
     *
     * @return la prioridad de la tarea
     */
    public Prioridad getPrioridad() {
        return prioridad;
    }

    /**
     * Establece la prioridad de la tarea.
     *
     * @param prioridad la nueva prioridad de la tarea
     */
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * Obtiene el estado actual de la tarea.
     *
     * @return el estado de la tarea
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual de la tarea.
     *
     * @param estado el nuevo estado de la tarea
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Establece un nuevo valor para el contador de IDs.
     * <p>
     * Este método solo debe utilizarse en casos excepcionales como
     * reinicialización o restauración de datos.
     *
     * @param nuevoValor el nuevo valor para el contador de IDs
     */
    public static void setContadorId(int nuevoValor) {
        contadorId = nuevoValor;
    }

    /**
     * Devuelve una representación en cadena de la tarea.
     *
     * @return una cadena con los principales atributos de la tarea
     */
    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Estado: %s | Prioridad: %s | Vence: %s",
                id, titulo, estado, prioridad, fechaVencimiento);
    }
}
