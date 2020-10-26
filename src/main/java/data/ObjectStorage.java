package data;

import shape.Shape;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ObjectStorage implements StorageInterface {


    public ArrayList<Shape> loadData(String location) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(location);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (ArrayList<Shape>) objectInputStream.readObject();
    }

    public void saveData(String location, ArrayList<Shape> shapes) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(location));
        objectOutputStream.writeObject(shapes);
    }
}
