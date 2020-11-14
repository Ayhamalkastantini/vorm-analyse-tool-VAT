package shape;

import java.util.HashMap;

public class Sphere extends Shape{
    
    private Double radius;

    /**
     *
     * @param radius
     */
    public Sphere(Double radius) {
        this.radius = radius;
        this.shapeType = "Sphere";
    }

    private Sphere() {
        super();
    }

    /**
     *
     * @return
     */
    public static Sphere createInstance() {
        return new Sphere();
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<String, String> getShapeFields() {
        HashMap<String, String> fields = new HashMap<String, String>(){{
            put("radius", "straal:");
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

        return data;
    }

    /**
     *
     * @param shapeData
     */
    @Override
    void setShapeData(HashMap<String, Double> shapeData) {
        this.radius = shapeData.getOrDefault("radius", 0.0);
    }

    /**
     *
     * @return
     */
    @Override
    public double calculateVolume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }
}
