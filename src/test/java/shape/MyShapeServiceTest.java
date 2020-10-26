package shape;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MyShapeServiceTest {

    @Test
    public void calculateTotalVolume() {
        MyShapeService myShapeService = new MyShapeService();

        Cone cone = new Cone(3.4, 2.2);
        myShapeService.addShape(cone);

        Cube cube = new Cube(1.2);
        myShapeService.addShape(cube);

        System.out.println(myShapeService.calculateTotalVolume());

        double totalVolume = 0.0;
        totalVolume += cone.calculateVolume();
        totalVolume += cube.calculateVolume();
        assertEquals(((double) Math.round(totalVolume * 100) / 100), myShapeService.calculateTotalVolume(), 0);
    }


    @Test
    public void getFields() {
        MyShapeService myShapeService = new MyShapeService();
        assertNull(myShapeService.getFields(""));

        Cube cube = new Cube(1.2);
        assertEquals(myShapeService.getFields("Kubus"), cube.getShapeFields());

        Cone cone = new Cone(3.4, 2.2);
        assertEquals(myShapeService.getFields("Kegel"), cone.getShapeFields());

        Cylinder cylinder = new Cylinder(1.2,3.1);
        assertEquals(myShapeService.getFields("Cilinder"), cylinder.getShapeFields());

        Pyramid pyramid = new Pyramid(1.2,3.1,0.42);
        assertEquals(myShapeService.getFields("Piramide"), pyramid.getShapeFields());

        Sphere sphere = new Sphere(1.2);
        assertEquals(myShapeService.getFields("Bol"), sphere.getShapeFields());
    }
}
