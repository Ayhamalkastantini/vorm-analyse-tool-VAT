package shape;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ConeTest {

    @Test
    void calculateVolume() {
        Cone cone = new Cone(4.0, 3.0);

        cone.calculateVolume();

        assertEquals(((1.0 / 3.0) * Math.PI * Math.pow(4.0, 2) * 3.0), cone.calculateVolume(), 0.0);
    }

    @Test
    void getData() {
        Cone cone = new Cone(1.4, 10.5);

        HashMap<String, Double> data = cone.getShapeData();

        assertEquals(1.4, (double)data.get("radius"), 0);
        assertEquals(10.5, (double) data.get("height"), 0);
    }
}
