import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.*;

public class vehicleManger {
    Stack<Intersection> intersection = new Stack<>();
    Queue<Vehicle> vehicleEast = new LinkedList<>();
    Queue<Vehicle> vehicleWest = new LinkedList<>();
    Queue<Vehicle> vehicleNorth = new LinkedList<>();
    Queue<Vehicle> vehicleSouth = new LinkedList<>();

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
                Vehicle vhi = new Vehicle(temp[0], Integer.parseInt(temp[1]), temp[2].charAt(0),
                        Double.parseDouble(temp[3]), temp[4].charAt(0), Boolean.parseBoolean(temp[5]),
                        Double.parseDouble(temp[6]), Double.parseDouble(temp[7]));
                if (temp[2].equals("E")) {

                    vehicleEast.add(vhi);
                } else if (temp[2].equals("W")) {

                    vehicleWest.add(vhi);
                } else if (temp[2].equals("N")) {

                    vehicleNorth.add(vhi);
                } else if (temp[2].equals("S")) {

                    vehicleSouth.add(vhi);
                }
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Initializing and adding vehicle class from the data obtained from gui
    public void add_Vehicle_gui(String typ, int num, char in_s, double cross_time, char direct_to, double leng,
            double co2) {
        vehicleManger vh = new vehicleManger();
        Vehicle fc = new Vehicle(typ, num, in_s, cross_time, direct_to, false, leng, co2);
        if (in_s == 'E') {
            vehicleEast.add(fc);

        } else if (in_s == 'W') {

            vehicleWest.add(fc);
        } else if (in_s == 'N') {

            vehicleNorth.add(fc);
        } else if (in_s == 'S') {

            vehicleSouth.add(fc);
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
                Intersection intersection1 = new Intersection(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]),
                        temp[2].charAt(0),
                        temp[3].charAt(0), temp[4].charAt(0));
                intersection.push(intersection1);
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // String[][] arr = new String[intersection.size()][];
        // intersection.toArray(arr);
        // for (int k = 0; k < arr.length; k++) {
        // for (int j = 0; j < arr[k].length; j++) {
        // System.out.print(arr[k][j] + " ");
        // }
        // System.out.println();
        // }
    }

}