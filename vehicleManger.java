import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class vehicleManger {
    Stack<String[]> intersection = new Stack<String[]>();
    Stack<String[]> vehicleEast = new Stack<String[]>();
    Stack<String[]> vehicleWest = new Stack<String[]>();
    Stack<String[]> vehicleNorth = new Stack<String[]>();
    Stack<String[]> vehicleSouth = new Stack<String[]>();

    // Reading vehicle csv file and adding it to stack
    public void vehicle() {
        String veh = "";
        int i = 0;
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Vehicles.csv"));
            while ((veh = bfr.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;

                }
                String[] temp = veh.split(",");
                if (temp[2] == "E") {

                    vehicleEast.push(temp);
                } else if (temp[2] == "W") {

                    vehicleWest.push(temp);
                } else if (temp[2] == "N") {

                    vehicleNorth.push(temp);
                } else if (temp[2] == "S") {

                    vehicleSouth.push(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reading intersection csv file and adding it to stack
    public void intersection() {
        String inter = "";
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
