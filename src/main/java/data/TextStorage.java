package data;

import shape.*;
//https://github.com/google/gson
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * The type Text storage.
 */
public class TextStorage implements StorageInterface {

    private Type shapeList = new TypeToken<ArrayList<Shape>>() {
    }.getType();


    private JsonDeserializer<ArrayList<Shape>> deserializer = (json, typeOfT, context) -> {
        ArrayList<Shape> shapes = new ArrayList<Shape>();
        JsonArray jsonArray = (JsonArray) json;

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            switch (jsonObject.get("shapeType").getAsString()) {
                case "Cone":
                    shapes.add(new Cone(jsonObject.get("radius").getAsDouble(), jsonObject.get("height").getAsDouble()));
                    break;
                case "Cube":
                    shapes.add(new Cube(jsonObject.get("sideLength").getAsDouble()));
                    break;
                case "Cylinder":
                    shapes.add(new Cylinder(jsonObject.get("radius").getAsDouble(), jsonObject.get("height").getAsDouble()));
                    break;
                case "Pyramid":
                    shapes.add(new Pyramid(jsonObject.get("baseWidth").getAsDouble(), jsonObject.get("baseLength").getAsDouble(), jsonObject.get("height").getAsDouble()));
                    break;
                case "Sphere":
                    shapes.add(new Sphere(jsonObject.get("radius").getAsDouble()));
                    break;
            }
        }

        return shapes;
    };

    /**
     *
     * @param location
     * @return
     * @throws Exception
     */
    public ArrayList<Shape> loadData(String location) throws Exception {
        FileReader fileReader = new FileReader(location);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder file = new StringBuilder();

        String currentLine = bufferedReader.readLine();

        while (currentLine != null) {
            file.append(currentLine);
            file.append(System.lineSeparator());
            currentLine = bufferedReader.readLine();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(shapeList, this.deserializer);
        Gson customGson = gsonBuilder.create();

        return customGson.fromJson(file.toString(), shapeList);
    }

    /**
     *
     * @param location
     * @param shapes
     * @throws Exception
     */
    public void saveData(String location, ArrayList<Shape> shapes) throws Exception {
        String json = new Gson().toJson(shapes);
        FileWriter fileWriter = new FileWriter(location);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(json);
        bufferedWriter.close();
    }
}
