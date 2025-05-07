package Modelo;

import java.time.LocalDate;

public class Tarea {
    private static int contadorId = 1;

    private final int id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaVencimiento;
    private Prioridad prioridad;
    private Estado estado;

    public Tarea(String titulo, String descripcion, LocalDate fechaVencimiento,
                 Prioridad prioridad, Estado estado) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public static void setContadorId(int nuevoValor) {
        contadorId = nuevoValor;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Estado: %s | Prioridad: %s | Vence: %s",
                id, titulo, estado, prioridad, fechaVencimiento);
    }
}
