package shape;

import java.util.HashMap;

public class Cone extends Shape {
    private Double radius;
    private Double height;

    /**
     *
     * @param radius
     * @param height
     */
    public Cone(Double radius, Double height) {
        this.radius = radius;
        this.height = height;
        this.shapeType = "Cone";
    }

    private Cone() {
        super();
    }

    /**
     *
     * @return
     */
    public static Cone createInstance() {
        return new Cone();
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<String, String> getShapeFields() {
       HashMap<String, String> fields = new HashMap<String, String>(){{
           put("radius", "Straal:");
           put("height", "Hoogte:");
        }};
        return fields;
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<String, Double> getShapeData() {
        HashMap<String, Double> data = new HashMap<>();
        data.put("radius", this.radius);
        data.put("height", this.height);

        return data;
    }

    /**
     *
     * @param shapeData
     */
    @Override
    void setShapeData(HashMap<String, Double> shapeData) {
        this.radius = shapeData.getOrDefault("radius", 0.0);
        this.height = shapeData.getOrDefault("height", 0.0);
    }

    /**
     *
     * @return
     */
    @Override
    public double calculateVolume() {
        return (1.0 / 3.0) * Math.PI * Math.pow(this.radius, 2) * this.height;
    }
}
