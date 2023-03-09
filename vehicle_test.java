import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class vehicle_test {

    @Test
    void Type() {

        // testing values of vehicle
        Vehicle expected = new Vehicle("car", 1003, 'E', 43.90, 'S', false, 4.98, 98.09);

        assertEquals(expected.getType(), "car");

        assertEquals(expected.getPlate_no(), 1003);

        assertEquals(expected.getIn_segment(), 'E');

        assertEquals(expected.getCrossing_time(), 43.9);

        assertEquals(expected.getDirection_to(), 'S');

        assertEquals(expected.isCrossed(), false);

        assertEquals(expected.getLength(), 4.98);

        assertEquals(expected.getCo2_emission(), 98.09);

    }

    @Test
    void estEmmission() {
        Vehicle vc = new Vehicle("car", 1003, 'E', 10, 'S', false, 4.98, 2);
        Double expected = vc.estimatedEmmision();
        Double actual = 24.98;
        assertEquals(expected, actual);

    }

}
