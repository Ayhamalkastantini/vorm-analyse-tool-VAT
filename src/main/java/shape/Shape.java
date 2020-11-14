package shape;
import java.io.Serializable;
import java.util.HashMap;

public abstract class Shape implements ShapeServices, Serializable {

    public String shapeType;
    private HashMap<String,String> shapeFields;
    private HashMap<String,Double> shapeData;

    /**
     *
     * @return
     */
    String getShapeType() {
        return this.shapeType;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Double> getShapeData() {
        return shapeData;
    }

    /**
     *
     * @param shapeData
     */
    void setShapeData(HashMap<String, Double> shapeData) {
        this.shapeData = shapeData;
    }

    /**
     *
     * @return
     */
    public HashMap<String, String> getShapeFields() {
        return shapeFields;
    }

    /**
     *
     * @return
     */
    @Override
    public double calculateVolume(){
        return 0.0;
    }

    /**
     *
     * @param shapeType
     */
    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }
}
