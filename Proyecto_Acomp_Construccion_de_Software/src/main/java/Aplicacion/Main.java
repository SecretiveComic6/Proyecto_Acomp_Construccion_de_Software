package Aplicacion;

import Modelo.TareaRepository;
import Modelo.Estado;
import Modelo.Prioridad;
import Modelo.TareaService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import Modelo.Tarea;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TareaRepository tareaRepository = new TareaRepository();
    private static final TareaService tareaService = new TareaService(tareaRepository);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> crearTarea();
                case "2" -> listarTareas();
                case "3" -> actualizarTarea();
                case "4" -> eliminarTarea();
                case "5" -> {
                    salir = true;
                    System.out.println("Saliendo del programa...");
                }
                case "6" -> buscarFiltrarTareas();
                default -> System.out.println("Opcion invalida. Intenta nuevamente");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== GESTION DE TAREAS ===");
        System.out.println("1. Crear tarea");
        System.out.println("2. Listar tareas");
        System.out.println("3. Actualizar tarea");
        System.out.println("4. Eliminar tarea");
        System.out.println("5. Salir");
        System.out.println("6. Buscar o filtrar tareas");
        System.out.print("Selecciona una opcion: ");
    }

    private static void crearTarea() {
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        LocalDate fecha = leerFecha();
        Prioridad prioridad = elegirPrioridad();
        Estado estado = elegirEstado();

        tareaService.agregarTarea(titulo, descripcion, fecha, prioridad, estado);
    }

    private static void listarTareas() {
        System.out.print("Ordenar por (fecha/prioridad): ");
        String criterio = scanner.nextLine();
        tareaService.listarTareas(criterio);
    }

    private static void actualizarTarea() {
        int id = leerEntero("ID de la tarea a actualizar: ");

        System.out.print("Nuevo titulo (dejar vacio si no cambia): ");
        String titulo = scanner.nextLine();

        System.out.print("Nueva descripcion (dejar vacio si no cambia): ");
        String descripcion = scanner.nextLine();

        System.out.print("Nueva fecha (AAAA-MM-DD) (ENTER para omitir): ");
        String fechaStr = scanner.nextLine();
        LocalDate fecha = null;
        if (!fechaStr.isEmpty()) {
            try {
                fecha = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha invalida. Se omitira el cambio de fecha");
            }
        }

        Prioridad prioridad = elegirPrioridad();
        Estado estado = elegirEstado();

        tareaService.actualizarTarea(id,
                titulo.isEmpty() ? null : titulo,
                descripcion.isEmpty() ? null : descripcion,
                fecha,
                prioridad,
                estado);
    }

    private static void eliminarTarea() {
        int id = leerEntero("ID de la tarea a eliminar: ");
        tareaService.eliminarTarea(id);
    }

    private static void buscarFiltrarTareas() {
        System.out.println("\n=== FILTROS ===");
        System.out.println("1. Filtrar por Estado");
        System.out.println("2. Filtrar por Prioridad");
        System.out.println("3. Filtrar por Fecha de Vencimiento");
        System.out.println("4. Buscar por palabra clave");
        System.out.print("Selecciona una opcion: ");
        String opcion = scanner.nextLine();

        List tareasFiltradas = switch (opcion) {
            case "1" -> tareaService.filtrarPorEstado(elegirEstado());
            case "2" -> tareaService.filtrarPorPrioridad(elegirPrioridad());
            case "3" -> tareaService.filtrarPorFecha(leerFecha());
            case "4" -> {
                System.out.print("Ingresa la palabra clave: ");
                String palabra = scanner.nextLine();
                yield tareaService.buscarPorPalabraClave(palabra);
            }
            default -> {
                System.out.println("Opcion invalida.");
                yield List.of();
            }
        };

        imprimirTareas(tareasFiltradas);
    }

    private static void imprimirTareas(List<Tarea> tareas) {
        if (tareas.isEmpty()) {
            System.out.println("No se encontraron tareas.");
            return;
        }

        String linea = "+----+----------------------+-------------+-----------+-----------------+------------------------------+";
        System.out.println(linea);
        System.out.printf("| %-2s | %-20s | %-11s | %-9s | %-15s | %-28s |\n",
                "ID", "Título", "Estado", "Prioridad", "Vencimiento", "Descripción");
        System.out.println(linea);

        for (var t : tareas) {
            System.out.printf("| %-2d | %-20s | %-11s | %-9s | %-15s | %-28s |\n",
                    t.getId(),
                    recortar(t.getTitulo(), 20),
                    t.getEstado(),
                    t.getPrioridad(),
                    t.getFechaVencimiento(),
                    recortar(t.getDescripcion(), 28));
        }
        System.out.println(linea);
    }

    private static Prioridad elegirPrioridad() {
        while (true) {
            System.out.println("Selecciona Prioridad:");
            System.out.println("1. ALTA");
            System.out.println("2. MEDIA");
            System.out.println("3. BAJA");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> { return Prioridad.ALTA; }
                case "2" -> { return Prioridad.MEDIA; }
                case "3" -> { return Prioridad.BAJA; }
                default -> System.out.println("Opcion invalida. Intenta de nuevo");
            }
        }
    }

    private static Estado elegirEstado() {
        while (true) {
            System.out.println("Selecciona Estado:");
            System.out.println("1. PENDIENTE");
            System.out.println("2. EN_PROGRESO");
            System.out.println("3. COMPLETADA");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> { return Estado.PENDIENTE; }
                case "2" -> { return Estado.EN_PROGRESO; }
                case "3" -> { return Estado.COMPLETADA; }
                default -> System.out.println("Opcion invalida. Intenta de nuevo.");
            }
        }
    }

    private static LocalDate leerFecha() {
        while (true) {
            try {
                System.out.print("Fecha de vencimiento (AAAA-MM-DD): ");
                String input = scanner.nextLine();
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha invalida. Usa el formato correcto (AAAA-MM-DD).");
            }
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Solo se permiten numeros enteros.");
            }
        }
    }

    private static String recortar(String texto, int max) {
        if (texto == null) return "";
        return texto.length() > max ? texto.substring(0, max - 3) + "..." : texto;
    }
}