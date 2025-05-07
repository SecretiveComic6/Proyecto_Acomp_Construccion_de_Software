package Modelo;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import Modelo.Tarea;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio encargado de gestionar la persistencia de las tareas en un archivo JSON.
 * <p>
 * Proporciona métodos para agregar, eliminar, obtener y almacenar tareas.
 * Utiliza la librería Gson para la serialización/deserialización de objetos, incluyendo
 * el manejo especial del tipo {@link LocalDate}.
 */
public class TareaRepository {

    /** Nombre del archivo JSON donde se almacenan las tareas. */
    private static final String ARCHIVO = "tareas.json";

    /** Lista de tareas en memoria. */
    private List<Tarea> tareas;

    /** Instancia de Gson configurada para manejar LocalDate y formato bonito. */
    private final Gson gson;

    /**
     * Crea una instancia del repositorio y carga las tareas desde el archivo si existe.
     */
    public TareaRepository() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        this.tareas = cargarDesdeArchivo();
    }

    /**
     * Obtiene una copia de la lista de tareas almacenadas.
     *
     * @return lista de tareas
     */
    public List<Tarea> obtenerTareas() {
        return new ArrayList<>(tareas);
    }

    /**
     * Agrega una nueva tarea al repositorio y la persiste en el archivo.
     *
     * @param tarea la tarea a agregar
     */
    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        guardarEnArchivo(tareas);
    }

    /**
     * Elimina una tarea del repositorio y actualiza el archivo.
     *
     * @param tarea la tarea a eliminar
     */
    public void eliminarTarea(Tarea tarea) {
        tareas.remove(tarea);
        guardarEnArchivo(tareas);
    }

    /**
     * Guarda la lista de tareas en el archivo JSON.
     *
     * @param tareas lista de tareas a guardar
     */
    public void guardarEnArchivo(List<Tarea> tareas) {
        try (FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(tareas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga la lista de tareas desde el archivo JSON.
     * <p>
     * Si el archivo no existe o ocurre un error, se devuelve una lista vacía.
     * Además, actualiza el contador de ID de las tareas con el valor máximo encontrado + 1.
     *
     * @return lista de tareas cargadas o vacía si no hay datos
     */
    private List<Tarea> cargarDesdeArchivo() {
        try (FileReader reader = new FileReader(ARCHIVO)) {
            Type tareaListType = new TypeToken<ArrayList<Tarea>>() {}.getType();
            List<Tarea> tareasCargadas = gson.fromJson(reader, tareaListType);

            int maxId = tareasCargadas.stream()
                    .mapToInt(Tarea::getId)
                    .max()
                    .orElse(0);
            Tarea.setContadorId(maxId + 1);

            return tareasCargadas;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Adaptador personalizado para serializar y deserializar objetos {@link LocalDate}
     * en formato ISO-8601 cuando se usa Gson.
     */
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        /**
         * Serializa un {@code LocalDate} a un {@code JsonElement}.
         *
         * @param date la fecha a serializar
         * @param typeOfSrc el tipo del objeto de origen
         * @param context el contexto de serialización
         * @return representación JSON de la fecha (cadena ISO-8601)
         */
        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.toString());
        }

        /**
         * Deserializa un {@code JsonElement} a un {@code LocalDate}.
         *
         * @param json el elemento JSON
         * @param typeOfT el tipo del objeto destino
         * @param context el contexto de deserialización
         * @return objeto LocalDate correspondiente
         * @throws JsonParseException si el formato no es válido
         */
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString());
        }
    }
}