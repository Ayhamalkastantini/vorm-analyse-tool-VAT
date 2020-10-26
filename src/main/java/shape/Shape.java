package shape;
import java.io.Serializable;
import java.util.HashMap;

public abstract class Shape implements ShapeServices, Serializable {

    public String shapeType;
    private HashMap<String,String> shapeFields;
    private HashMap<String,Double> shapeData;

    String getShapeType() {
        return this.shapeType;
    }

    public HashMap<String, Double> getShapeData() {
        return shapeData;
    }

    void setShapeData(HashMap<String, Double> shapeData) {
        this.shapeData = shapeData;
    }

    public HashMap<String, String> getShapeFields() {
        return shapeFields;
    }

    @Override
    public double calculateVolume(){
        return 0.0;
    }

    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }
}
