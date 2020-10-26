package shape;

import java.util.HashMap;

public class Cylinder extends Shape {

    private Double height;
    private Double radius;

    public Cylinder(Double radius, Double height) {
        this.radius = radius;
        this.height = height;
        this.shapeType = "Cylinder";
    }

    private Cylinder() {
        super();
    }

    public static Cylinder createInstance() {
        return new Cylinder();
    }

    @Override
    public HashMap<String, String> getShapeFields() {
        HashMap<String, String> fields = new HashMap<String, String>(){{
            put("radius", "Straal:");
            put("height", "Hoogte:");
        }};
        return fields;
    }

    @Override
    public HashMap<String, Double> getShapeData() {
        HashMap<String, Double> data = new HashMap<>();
        data.put("radius", this.radius);
        data.put("height", this.height);

        return data;
    }

    @Override
    void setShapeData(HashMap<String, Double> shapeData) {
        this.radius = shapeData.getOrDefault("radius", 0.0);
        this.height = shapeData.getOrDefault("height", 0.0);
    }

    @Override
    public double calculateVolume() {
        return Math.PI * Math.pow(this.radius, 2) * this.height;
    }
}
