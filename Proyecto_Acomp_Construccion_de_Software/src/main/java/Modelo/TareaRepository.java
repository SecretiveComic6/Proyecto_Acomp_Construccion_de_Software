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

public class TareaRepository {

    private static final String ARCHIVO = "tareas.json";
    private List<Tarea> tareas;

    private final Gson gson;

    public TareaRepository() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        this.tareas = cargarDesdeArchivo();
    }

    public List<Tarea> obtenerTareas() {
        return new ArrayList<>(tareas);
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        guardarEnArchivo(tareas);
    }

    public void eliminarTarea(Tarea tarea) {
        tareas.remove(tarea);
        guardarEnArchivo(tareas);
    }

    public void guardarEnArchivo(List<Tarea> tareas) {
        try (FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(tareas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    // Clase interna para adaptar LocalDate
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.toString()); // formato ISO-8601
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString());
        }
    }
}
