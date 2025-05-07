package Controlador;

import Modelo.Tarea;
import Modelo.TareaService;
import Vista.VentanaPrincipal;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Modelo.Estado;
import Modelo.Prioridad;
import Vista.DialogoFiltroTarea;
import Vista.FormularioTareaDialog;
import Vista.TareaTableModel;

/**
 * Controlador principal para gestionar las interacciones entre la interfaz gráfica
 * {@link VentanaPrincipal} y la lógica de negocio {@link TareaService}.
 * <p>
 * Se encarga de manejar las acciones del usuario relacionadas con la gestión de tareas:
 * agregar, actualizar, eliminar, buscar y filtrar.
 */
public class TareaControlador {
    private final TareaService service;
    private final VentanaPrincipal vista;
    private TareaTableModel tablaModelo;

    /**
     * Crea una instancia del controlador, inicializando los manejadores de eventos
     * y cargando las tareas existentes en la tabla de la vista.
     *
     * @param service instancia del servicio de tareas que gestiona la lógica de negocio
     * @param vista   instancia de la ventana principal que actúa como interfaz de usuario
     */
    public TareaControlador(TareaService service, VentanaPrincipal vista) {
        this.service = service;
        this.vista = vista;

        vista.getBtnAgregar().addActionListener(e -> agregarTarea());
        vista.getBtnActualizar().addActionListener(e -> actualizarTarea());
        vista.getBtnEliminar().addActionListener(e -> eliminarTarea());
        vista.getBtnFiltrar().addActionListener(e -> filtrarTareas());
        vista.getBtnBuscar().addActionListener(e -> buscarTareas());

        cargarTareasEnTabla();
    }

    /**
     * Carga todas las tareas desde el servicio y las muestra en la tabla de la vista.
     */
    private void cargarTareasEnTabla() {
        List<Tarea> tareas = service.obtenerTodas();
        tablaModelo = new TareaTableModel(tareas);
        vista.getTablaTareas().setModel(tablaModelo);
    }

    /**
     * Abre un formulario para agregar una nueva tarea.
     * Si se confirma la creación, la tarea se guarda y se actualiza la tabla.
     */
    private void agregarTarea() {
        FormularioTareaDialog dialogo = new FormularioTareaDialog(vista);
        dialogo.setVisible(true);

        Tarea nuevaTarea = dialogo.getTarea();

        if (nuevaTarea != null) {
            service.agregarTarea(nuevaTarea);
            cargarTareasEnTabla();
        }
    }

    /**
     * Permite actualizar una tarea existente seleccionada en la tabla.
     * Si se confirma la edición, los datos se actualizan y se refleja en la tabla.
     */
    private void actualizarTarea() {
        int filaSeleccionada = vista.getTablaTareas().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona una tarea para actualizar.");
            return;
        }

        Tarea tareaExistente = tablaModelo.getTareaEnFila(filaSeleccionada);
        FormularioTareaDialog dialogo = new FormularioTareaDialog(vista, tareaExistente);
        dialogo.setVisible(true);

        if (dialogo.isConfirmado()) {
            Tarea datosActualizados = dialogo.getTarea();

            tareaExistente.setTitulo(datosActualizados.getTitulo());
            tareaExistente.setDescripcion(datosActualizados.getDescripcion());
            tareaExistente.setFechaVencimiento(datosActualizados.getFechaVencimiento());
            tareaExistente.setPrioridad(datosActualizados.getPrioridad());
            tareaExistente.setEstado(datosActualizados.getEstado());

            service.actualizarTarea(tareaExistente);
            tablaModelo.fireTableDataChanged();
        }
    }

    /**
     * Elimina la tarea seleccionada en la tabla tras confirmar la acción con el usuario.
     */
    private void eliminarTarea() {
        int filaSeleccionada = vista.getTablaTareas().getSelectedRow();
        if (filaSeleccionada != -1) {
            Tarea tareaSeleccionada = tablaModelo.getTareaEnFila(filaSeleccionada);

            int opcion = JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro de que deseas eliminar la tarea?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                service.eliminarTarea(tareaSeleccionada.getId());
                cargarTareasEnTabla();
                JOptionPane.showMessageDialog(vista, "Tarea eliminada exitosamente.");
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona una tarea para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abre un diálogo para seleccionar un criterio de filtrado y muestra las tareas que coinciden.
     * Los filtros disponibles son por estado, prioridad o fecha.
     */
    private void filtrarTareas() {
        DialogoFiltroTarea dialogo = new DialogoFiltroTarea(vista);
        dialogo.setVisible(true);

        if (dialogo.isConfirmado()) {
            String filtroSeleccionado = dialogo.getFiltroSeleccionado();
            List<Tarea> tareasFiltradas = new ArrayList<>();

            switch (filtroSeleccionado) {
                case "Estado":
                    Estado estadoSeleccionado = dialogo.getEstado();
                    tareasFiltradas = service.filtrarPorEstado(estadoSeleccionado);
                    break;
                case "Prioridad":
                    Prioridad prioridadSeleccionada = dialogo.getPrioridad();
                    tareasFiltradas = service.filtrarPorPrioridad(prioridadSeleccionada);
                    break;
                case "Fecha":
                    LocalDate fechaSeleccionada = dialogo.getFecha();
                    if (fechaSeleccionada != null) {
                        tareasFiltradas = service.filtrarPorFecha(fechaSeleccionada);
                    } else {
                        JOptionPane.showMessageDialog(vista, "La fecha ingresada no es válida.");
                        return;
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(vista, "Por favor, selecciona un filtro válido.");
                    return;
            }

            if (tareasFiltradas.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No se encontraron tareas con los criterios seleccionados.");
            } else {
                tablaModelo.setTareas(tareasFiltradas);
                tablaModelo.fireTableDataChanged();
            }
        }
    }

    /**
     * Permite buscar tareas que contengan una palabra clave en su título o descripción.
     * Si no se ingresa palabra clave, se restauran todas las tareas.
     */
    private void buscarTareas() {
        String palabraClave = JOptionPane.showInputDialog(null, "Ingresa una palabra clave para buscar:");

        if (palabraClave != null && !palabraClave.trim().isEmpty()) {
            List<Tarea> resultados = service.buscarPorPalabraClave(palabraClave.trim());

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron tareas que coincidan.");
            } else {
                tablaModelo.setTareas(resultados);
            }
        } else {
            tablaModelo.setTareas(service.obtenerTodas());
        }
    }

}
