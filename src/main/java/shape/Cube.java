package shape;


import java.util.HashMap;

public class Cube extends Shape  {

    private Double sideLength;

    /**
     *
     * @param sideLength
     */
    public Cube(Double sideLength) {
        this.sideLength = sideLength;
        this.shapeType = "Cube";
    }

    private Cube() {
        super();
    }

    /**
     *
     * @return
     */
    public static Cube createInstance() {
        return new Cube();
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<String, String> getShapeFields() {
        HashMap<String, String> fields = new HashMap<String, String>(){{
            put("sideLength", "zijkant lengte:");
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
        data.put("sideLength", this.sideLength);
        return data;
    }

    /**
     *
     * @param shapeData
     */
    @Override
    void setShapeData(HashMap<String, Double> shapeData) {
        this.sideLength = shapeData.getOrDefault("sideLength", 0.0);
    }

    /**
     *
     * @return
     */
    @Override
    public double calculateVolume() {
        return Math.pow(this.sideLength, 3);
    }

}
