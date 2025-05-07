package Vista;

import Modelo.Tarea;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Modelo de tabla para representar una lista de tareas en una JTable.
 * Extiende AbstractTableModel para adaptar los datos de las tareas a una tabla.
 */
public class TareaTableModel extends AbstractTableModel {
    
    /** Los nombres de las columnas que se mostrarán en la tabla. */
    private final String[] columnas = {"ID", "Titulo", "Descripción", "Estado", "Prioridad", "Fecha Límite"};
    
    /** La lista de tareas que se mostrará en la tabla. */
    private List<Tarea> tareas;

    /**
     * Constructor del modelo de tabla.
     * 
     * @param tareas La lista de tareas que se mostrará en la tabla.
     */
    public TareaTableModel(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    /**
     * Obtiene el número de filas de la tabla (basado en el tamaño de la lista de tareas).
     * 
     * @return El número de filas (tareas) en la lista.
     */
    @Override
    public int getRowCount() {
        return tareas.size();
    }

    /**
     * Obtiene el número de columnas de la tabla.
     * 
     * @return El número de columnas (6 en total, por las diferentes propiedades de las tareas).
     */
    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    /**
     * Obtiene el valor de una celda en la tabla en función del índice de la fila y la columna.
     * 
     * @param rowIndex El índice de la fila.
     * @param columnIndex El índice de la columna.
     * @return El valor de la celda en la fila y columna especificada.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarea tarea = tareas.get(rowIndex); // Obtenemos la tarea en la fila indicada
        return switch (columnIndex) { // Según la columna, devolvemos el valor correspondiente
            case 0 -> tarea.getId();
            case 1 -> tarea.getTitulo();
            case 2 -> tarea.getDescripcion();
            case 3 -> tarea.getEstado();
            case 4 -> tarea.getPrioridad();
            case 5 -> tarea.getFechaVencimiento();
            default -> null;
        };
    }

    /**
     * Obtiene el nombre de la columna dado su índice.
     * 
     * @param column El índice de la columna.
     * @return El nombre de la columna.
     */
    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    /**
     * Obtiene la tarea en una fila específica.
     * 
     * @param row El índice de la fila.
     * @return La tarea que se encuentra en la fila indicada.
     */
    public Tarea getTareaAt(int row) {
        return tareas.get(row);
    }

    /**
     * Establece una nueva lista de tareas y notifica a la tabla para que se actualice.
     * 
     * @param tareas La nueva lista de tareas que se mostrará en la tabla.
     */
    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
        fireTableDataChanged(); // Notifica a la JTable que los datos han cambiado
    }
    
    /**
     * Obtiene la tarea en una fila específica, es otro método alternativo para obtener la tarea.
     * 
     * @param fila El índice de la fila.
     * @return La tarea que se encuentra en la fila indicada.
     */
    public Tarea getTareaEnFila(int fila) {
        return tareas.get(fila);
    }
}
