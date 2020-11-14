package data;

import shape.Shape;

import java.util.ArrayList;

public interface StorageInterface {
    /**
     * 
     * @param location
     * @return
     * @throws Exception
     */
    ArrayList<Shape> loadData(String location) throws Exception;
    void saveData(String location, ArrayList<Shape> shapes) throws Exception;
}
