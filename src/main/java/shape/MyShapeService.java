package shape;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MyShapeService{


    private ArrayList<Shape> shapes = new ArrayList<>();

    public static HashMap<String, String> getFields(String shapeType) {
        switch (shapeType) {
            case "Kubus":
                Shape cube = Cube.createInstance();
                return cube.getShapeFields();
            case "Kegel":
                Shape cone = Cone.createInstance();
                return cone.getShapeFields();
            case "Cilinder":
                Shape cylinder = Cylinder.createInstance();
                return cylinder.getShapeFields();
            case "Piramide":
                Shape pyramid = Pyramid.createInstance();
                return pyramid.getShapeFields();
            case "Bol":
                Shape sphere = Sphere.createInstance();
                return sphere.getShapeFields();
            default:
                return null;
        }
    }

    public ArrayList<Shape> getShapes() {
        return this.shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public double calculateTotalVolume() {
        double totalVolume = 0.0;

        for (Shape shape : shapes) {
            totalVolume += shape.calculateVolume();
        }

        totalVolume = (double) Math.round(totalVolume * 100) / 100;;

        return totalVolume;
    }

    public Shape addShape(String shapeType, HashMap<String, Double> data) {
        Shape newShape;

        switch (shapeType) {
            case "Kubus":
                newShape = new Cube(data.get("sideLength"));
                break;
            case "Kegel":
                newShape = new Cone(data.get("radius"),data.get("height"));
                break;
            case "Cilinder":
                newShape = new Cylinder(data.get("radius"),data.get("height"));
                break;
            case "Piramide":
                newShape = new Pyramid(data.get("baseWidth"),data.get("baseLength"),data.get("height"));
                break;
            case "Bol":
                newShape = new Sphere(data.get("radius"));
                break;
            default:
                return null;
        }

        this.shapes.add(newShape);
        return newShape;
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    public void editShape(Shape shape,HashMap<String, Double> data) {
        shape.setShapeData(data);

    }
}
