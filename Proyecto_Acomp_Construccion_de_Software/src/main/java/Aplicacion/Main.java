package Aplicacion;

import Controlador.TareaControlador;
import Modelo.TareaRepository;
import Modelo.TareaService;
import Vista.VentanaPrincipal;

/**
 * Clase principal de la aplicación.
 * <p>
 * Inicia la interfaz gráfica y configura las dependencias necesarias para el funcionamiento
 * del sistema de gestión de tareas.
 */
public class Main {

    /**
     * Punto de entrada de la aplicación.
     * <p>
     * Inicializa el repositorio, servicio, vista y controlador de tareas, 
     * y lanza la ventana principal en el hilo de la interfaz gráfica.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TareaRepository repo = new TareaRepository();
            TareaService service = new TareaService(repo);
            VentanaPrincipal vista = new VentanaPrincipal();
            new TareaControlador(service, vista);
            vista.setVisible(true);
        });
    }
}
