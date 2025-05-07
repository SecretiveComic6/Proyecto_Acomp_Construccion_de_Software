package Modelo;

/**
 * Enumera los niveles de prioridad que puede tener una tarea.
 * <p>
 * La prioridad ayuda a clasificar las tareas según su urgencia o importancia.
 */
public enum Prioridad {
    /** La tarea tiene alta prioridad y debe atenderse con urgencia. */
    ALTA,

    /** La tarea tiene una prioridad media. */
    MEDIA,

    /** La tarea tiene baja prioridad y puede postergarse si es necesario. */
    BAJA
}