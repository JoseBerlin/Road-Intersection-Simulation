import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class vehicleManger {

    public void vehicle() {
        String veh = "";
        Stack<String[]> vehicles = new Stack<String[]>();
        int i = 0;
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Vehicles.csv"));
            while ((veh = bfr.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;

                }
                String[] temp = veh.split(",");

                vehicles.push(temp);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void intersection() {
        String inter = "";
        Stack<String[]> intersection = new Stack<String[]>();
        int i = 0;
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Intersection.csv"));
            while ((inter = bfr.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;

                }
                String[] temp = inter.split(",");
                System.out.println(temp[1]);
                intersection.push(temp);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
