package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación de gestión de tareas.
 * Esta ventana contiene botones para agregar, actualizar, eliminar, filtrar y buscar tareas,
 * así como una tabla para mostrar las tareas.
 */
public class VentanaPrincipal extends JFrame {
    
    // Definición de botones para las acciones principales
    private JButton btnAgregar, btnActualizar, btnEliminar, btnFiltrar, btnBuscar;
    
    // Definición de la tabla para mostrar las tareas
    private JTable tablaTareas;
    
    // Definición del JScrollPane que contiene la tabla
    private JScrollPane scrollPane;

    /**
     * Constructor de la ventana principal.
     * Inicializa la ventana con los componentes necesarios y configura el diseño.
     */
    public VentanaPrincipal() {
        setTitle("Gestor de Tareas");  // Título de la ventana
        setSize(800, 600);  // Tamaño de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // Acción al cerrar la ventana
        setLocationRelativeTo(null);  // Centrar la ventana en la pantalla

        // Inicialización de los botones
        btnAgregar = new JButton("Agregar Tarea");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnFiltrar = new JButton("Filtrar");
        btnBuscar = new JButton("Buscar");

        // Inicialización de la tabla de tareas y el JScrollPane
        tablaTareas = new JTable();  // Se conectará luego a un modelo de datos
        scrollPane = new JScrollPane(tablaTareas);  // Permite el desplazamiento en caso de ser necesario

        // Creación y configuración del panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnBuscar);

        // Agregar los componentes al contenedor de la ventana
        getContentPane().add(panelBotones, BorderLayout.NORTH);  // Agregar panel de botones al norte
        getContentPane().add(scrollPane, BorderLayout.CENTER);  // Agregar la tabla al centro
    }

    /**
     * Obtiene el botón para agregar una nueva tarea.
     * 
     * @return El botón para agregar tarea.
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    /**
     * Obtiene el botón para actualizar una tarea existente.
     * 
     * @return El botón para actualizar tarea.
     */
    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    /**
     * Obtiene el botón para eliminar una tarea seleccionada.
     * 
     * @return El botón para eliminar tarea.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Obtiene el botón para aplicar filtros a las tareas.
     * 
     * @return El botón para filtrar tareas.
     */
    public JButton getBtnFiltrar() {
        return btnFiltrar;
    }

    /**
     * Obtiene el botón para buscar tareas.
     * 
     * @return El botón para buscar tareas.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene la tabla que muestra las tareas.
     * 
     * @return La tabla de tareas.
     */
    public JTable getTablaTareas() {
        return tablaTareas;
    }
}
