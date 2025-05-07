package Modelo;

/**
 * Enumera los posibles estados de una tarea.
 * <p>
 * Los estados describen el progreso de una tarea dentro de su ciclo de vida.
 */
public enum Estado {
    /** La tarea aún no ha sido iniciada. */
    PENDIENTE,

    /** La tarea está en curso. */
    EN_PROGRESO,

    /** La tarea ha sido completada. */
    COMPLETADA
}

