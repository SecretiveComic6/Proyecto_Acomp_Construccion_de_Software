package Vista;

import Modelo.Tarea;
import Modelo.Estado;
import Modelo.Prioridad;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Diálogo para agregar o editar una tarea.
 * <p>
 * Este formulario permite al usuario ingresar o modificar información de una tarea, como el título, 
 * la descripción, el estado, la prioridad y la fecha límite. Si la tarea es editada, los campos 
 * se precargan con los valores existentes.
 */
public class FormularioTareaDialog extends JDialog {
    
    /** Campo de texto para ingresar el título de la tarea. */
    private JTextField campoTitulo = new JTextField(20);
    
    /** Campo de texto para ingresar la descripción de la tarea. */
    private JTextField campoDescripcion = new JTextField(20);
    
    /** ComboBox para seleccionar el estado de la tarea. */
    private JComboBox<Estado> comboEstado = new JComboBox<>(Estado.values());
    
    /** ComboBox para seleccionar la prioridad de la tarea. */
    private JComboBox<Prioridad> comboPrioridad = new JComboBox<>(Prioridad.values());
    
    /** Campo de texto para ingresar la fecha límite de la tarea. */
    private JTextField campoFechaLimite = new JTextField(10); // Formato: AAAA-MM-DD
    
    /** Indica si el usuario confirmó la acción (guardar o cancelar). */
    private boolean confirmado = false;

    /**
     * Constructor para crear el formulario de tarea para agregar una nueva tarea.
     *
     * @param padre la ventana principal que abrirá este diálogo
     */
    public FormularioTareaDialog(JFrame padre) {
        super(padre, true);
        setTitle("Formulario de Tarea");
        setSize(350, 200);
        setLocationRelativeTo(padre);
        construirFormulario();
    }

    /**
     * Constructor para crear el formulario de tarea para editar una tarea existente.
     *
     * @param parent la ventana principal que abrirá este diálogo
     * @param tareaExistente la tarea que se desea editar
     */
    public FormularioTareaDialog(JFrame parent, Tarea tareaExistente) {
        super(parent, "Editar Tarea", true);
        setSize(350, 200);
        construirFormulario();

        // Precargar datos de la tarea existente
        campoTitulo.setText(tareaExistente.getTitulo());
        campoDescripcion.setText(tareaExistente.getDescripcion());
        campoFechaLimite.setText(tareaExistente.getFechaVencimiento().toString());
        comboPrioridad.setSelectedItem(tareaExistente.getPrioridad());
        comboEstado.setSelectedItem(tareaExistente.getEstado());
    }

    /**
     * Método que construye la interfaz del formulario.
     */
    private void construirFormulario() {
        setLayout(new BorderLayout());

        // Panel para los campos del formulario
        JPanel panelCampos = new JPanel(new GridLayout(5, 2));

        panelCampos.add(new JLabel("Título:"));
        panelCampos.add(campoTitulo);

        panelCampos.add(new JLabel("Descripción:"));
        panelCampos.add(campoDescripcion);

        panelCampos.add(new JLabel("Estado:"));
        panelCampos.add(comboEstado);

        panelCampos.add(new JLabel("Prioridad:"));
        panelCampos.add(comboPrioridad);

        panelCampos.add(new JLabel("Fecha límite (AAAA-MM-DD):"));
        panelCampos.add(campoFechaLimite);

        add(panelCampos, BorderLayout.CENTER);

        // Panel para los botones de guardar y cancelar
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acción del botón "Guardar"
        btnGuardar.addActionListener(e -> {
            confirmado = true;
            dispose();
        });

        // Acción del botón "Cancelar"
        btnCancelar.addActionListener(e -> {
            confirmado = false;
            dispose();
        });
    }

    /**
     * Establece los valores de la tarea en los campos del formulario.
     *
     * @param tarea la tarea con los datos que se deben cargar en el formulario
     */
    public void setTarea(Tarea tarea) {
        campoTitulo.setText(tarea.getTitulo());
        campoDescripcion.setText(tarea.getDescripcion());
        comboEstado.setSelectedItem(tarea.getEstado());
        comboPrioridad.setSelectedItem(tarea.getPrioridad());
        campoFechaLimite.setText(tarea.getFechaVencimiento().toString());
    }

    /**
     * Obtiene la tarea con los datos ingresados en el formulario.
     *
     * @return la tarea con los datos del formulario, o null si la fecha es inválida
     */
    public Tarea getTarea() {
        Tarea tarea = new Tarea();
        tarea.setTitulo(campoTitulo.getText());
        tarea.setDescripcion(campoDescripcion.getText());
        tarea.setEstado((Estado) comboEstado.getSelectedItem());
        tarea.setPrioridad((Prioridad) comboPrioridad.getSelectedItem());
        try {
            tarea.setFechaVencimiento(LocalDate.parse(campoFechaLimite.getText()));
        } catch (DateTimeParseException e) {
            // Si la fecha es inválida, se muestra un mensaje de error
            JOptionPane.showMessageDialog(this, "Por favor, ingresa una fecha válida en formato AAAA-MM-DD", "Fecha inválida", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return tarea;
    }

    /**
     * Verifica si el usuario ha confirmado la acción (guardar o cancelar).
     *
     * @return true si el usuario ha confirmado la acción, false si ha cancelado
     */
    public boolean isConfirmado() {
        return confirmado;
    }
}
