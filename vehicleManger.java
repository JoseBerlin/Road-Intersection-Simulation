import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class vehicleManger {

    public void vehicle() {
        String veh = "";

        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Vehicles.csv"));
            while ((veh = bfr.readLine()) != null) {
                String[] vehicles = veh.split(",");
                System.out.println(vehicles[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
