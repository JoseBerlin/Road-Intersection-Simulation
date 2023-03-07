import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class vehicle_test {
    // private Double expected2;

    // @Before
    // public void init() throws noSegmentException {
    //     vehicleManger vm = new vehicleManger();
    //     vm.vehicle();
    //     HashMap<Character, Double[]> fgf = vm.calSegment();
    //     expected2 = 4343436.9;
    // }

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

    // testing of stat table
    // @Test
    // void StatTableTest() throws noSegmentException {

    //     assertEquals(expected2, 12.0);

    //     assertEquals(expected2, 21.8);

    //     assertEquals(expected2, 30.0);

    // }

    // @After
    // public void tear(){
     
    //     expected2 = 4343436.9;
    // }
}
