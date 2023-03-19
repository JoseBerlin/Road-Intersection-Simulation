import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class VehicleTest {
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

    @Test
    void intersection() {

        Intersection ib = new Intersection(2, 38, 'N', 'S', ' ');

        assertEquals(ib.getPhases(), 2);

        assertEquals(ib.getDuration(), 38);

        assertEquals(ib.getSegment_in(), 'N');

        assertEquals(ib.getDirection_1(), 'S');

        assertEquals(ib.getDirection_2(), ' ');

    }

    @Test
    void crosstest() {

        Vehicle vc = new Vehicle("car", 1003, 'E', 10, 'S', false, 4.98, 2);
        Intersection ib = new Intersection(2, 38, 'E', 'S', 'W');
        boolean cf = ib.crossPossible(vc.getIn_segment(), vc.getDirection_to());

        assertEquals(cf, true);

    }

}