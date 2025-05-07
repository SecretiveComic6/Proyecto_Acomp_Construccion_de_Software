package Vista;

import Modelo.Estado;
import Modelo.Prioridad;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Ventana de diálogo para filtrar tareas según criterios como estado, prioridad o fecha.
 * <p>
 * Este diálogo permite seleccionar un filtro y aplicar las opciones seleccionadas para 
 * obtener una lista de tareas filtradas. Los filtros disponibles son:
 * - Estado
 * - Prioridad
 * - Fecha de vencimiento
 */
public class DialogoFiltroTarea extends JDialog {
    
    /** ComboBox para seleccionar el tipo de filtro. */
    private JComboBox<String> comboFiltro;
    
    /** ComboBox para seleccionar el estado de las tareas. */
    private JComboBox<Estado> comboEstado;
    
    /** ComboBox para seleccionar la prioridad de las tareas. */
    private JComboBox<Prioridad> comboPrioridad;
    
    /** Campo de texto para ingresar la fecha de vencimiento. */
    private JTextField campoFecha;
    
    /** Indica si el filtro fue confirmado. */
    private boolean confirmado = false;

    /**
     * Crea un nuevo diálogo para filtrar tareas.
     *
     * @param padre la ventana principal que abrirá este diálogo
     */
    public DialogoFiltroTarea(JFrame padre) {
        super(padre, true);
        setTitle("Filtrar Tareas");
        setSize(300, 200);
        setLocationRelativeTo(padre);

        // Inicializar componentes
        comboFiltro = new JComboBox<>(new String[]{"Seleccionar Filtro", "Estado", "Prioridad", "Fecha"});
        comboEstado = new JComboBox<>(Estado.values());
        comboPrioridad = new JComboBox<>(Prioridad.values());
        campoFecha = new JTextField(10); // Formato: AAAA-MM-DD

        // Crear panel para los filtros
        JPanel panelCampos = new JPanel(new GridLayout(5, 2));
        
        panelCampos.add(new JLabel("Filtro:"));
        panelCampos.add(comboFiltro);

        panelCampos.add(new JLabel("Estado:"));
        panelCampos.add(comboEstado);

        panelCampos.add(new JLabel("Prioridad:"));
        panelCampos.add(comboPrioridad);

        panelCampos.add(new JLabel("Fecha (AAAA-MM-DD):"));
        panelCampos.add(campoFecha);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAplicar = new JButton("Aplicar");
        JButton btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnAplicar);
        panelBotones.add(btnCancelar);

        // Agregar paneles al diálogo
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Escuchar cambios en el combo de filtro
        comboFiltro.addActionListener(e -> actualizarVistaFiltro());

        // Acción del botón "Aplicar"
        btnAplicar.addActionListener(e -> {
            confirmado = true;
            dispose();
        });

        // Acción del botón "Cancelar"
        btnCancelar.addActionListener(e -> dispose());

        // Al inicio deshabilitar los campos dependiendo del filtro
        actualizarVistaFiltro();
    }

    /**
     * Actualiza la interfaz de usuario de acuerdo con el filtro seleccionado.
     * Habilita y deshabilita los componentes (estado, prioridad, fecha) según el filtro.
     */
    private void actualizarVistaFiltro() {
        String filtroSeleccionado = (String) comboFiltro.getSelectedItem();
        
        // Activar/desactivar los componentes basados en el filtro seleccionado
        comboEstado.setEnabled(filtroSeleccionado.equals("Estado"));
        comboPrioridad.setEnabled(filtroSeleccionado.equals("Prioridad"));
        campoFecha.setEnabled(filtroSeleccionado.equals("Fecha"));

        // Limpiar los campos cuando se cambia el filtro
        if (filtroSeleccionado.equals("Estado")) {
            comboEstado.setSelectedIndex(0);
        } else if (filtroSeleccionado.equals("Prioridad")) {
            comboPrioridad.setSelectedIndex(0);
        } else if (filtroSeleccionado.equals("Fecha")) {
            campoFecha.setText("");
        }
    }

    /**
     * Verifica si el filtro fue confirmado por el usuario.
     *
     * @return true si el filtro fue confirmado, false si fue cancelado
     */
    public boolean isConfirmado() {
        return confirmado;
    }

    /**
     * Obtiene el filtro seleccionado por el usuario.
     *
     * @return el nombre del filtro seleccionado
     */
    public String getFiltroSeleccionado() {
        return (String) comboFiltro.getSelectedItem();
    }

    /**
     * Obtiene el estado seleccionado en el filtro.
     *
     * @return el estado seleccionado, o null si no se ha seleccionado un estado
     */
    public Estado getEstado() {
        return (Estado) comboEstado.getSelectedItem();
    }

    /**
     * Obtiene la prioridad seleccionada en el filtro.
     *
     * @return la prioridad seleccionada, o null si no se ha seleccionado una prioridad
     */
    public Prioridad getPrioridad() {
        return (Prioridad) comboPrioridad.getSelectedItem();
    }

    /**
     * Obtiene la fecha ingresada en el campo de texto de fecha.
     *
     * @return la fecha de vencimiento como LocalDate, o null si el formato es incorrecto
     */
    public LocalDate getFecha() {
        try {
            return LocalDate.parse(campoFecha.getText());
        } catch (Exception e) {
            return null;
        }
    }
}
