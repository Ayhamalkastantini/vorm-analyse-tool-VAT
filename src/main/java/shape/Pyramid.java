package shape;

import java.util.HashMap;

public class Pyramid extends Shape {

    private Double baseWidth;
    private Double baseLength;
    private Double height;

    public Pyramid(Double baseWidth, Double baseLength, Double height) {
        this.baseWidth = baseWidth;
        this.baseLength = baseLength;
        this.height = height;
        this.shapeType = "Pyramid";
    }

    private Pyramid() {
        super();
    }

    public static Pyramid createInstance() {
        return new Pyramid();
    }

    @Override
    public HashMap<String, String> getShapeFields() {
        HashMap<String, String> fields = new HashMap<String, String>(){{
            put("baseWidth", "Basis Breedte:");
            put("baseLength", "basis Lengte:");
            put("height", "Hoogte:");
        }};
        return fields;
    }

    @Override
    public HashMap<String, Double> getShapeData() {
        HashMap<String, Double> data = new HashMap<>();
        data.put("baseWidth", this.baseWidth);
        data.put("baseLength", this.baseLength);
        data.put("height", this.height);

        return data;
    }

    @Override
    void setShapeData(HashMap<String, Double> shapeData) {
        this.baseWidth = shapeData.getOrDefault("baseWidth", 0.0);
        this.baseLength = shapeData.getOrDefault("baseLength", 0.0);
        this.height = shapeData.getOrDefault("height", 0.0);
    }

    @Override
    public double calculateVolume() {
        return ((baseWidth * baseLength) * height) / 3.0;
    }
}
