import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class vehicleManger {
    Stack<String[]> intersection = new Stack<String[]>();
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
                Vehicle vhi=new Vehicle(temp[0], Integer.parseInt(temp[1]), temp[2].charAt(0), Double.parseDouble(temp[3]), temp[4].charAt(0), Boolean.parseBoolean(temp[5]), Double.parseDouble(temp[6]), Double.parseDouble(temp[7]));
                if (temp[2] == "E") {

                    vehicleEast.add(vhi);
                } else if (temp[2] == "W") {

                    vehicleWest.add(vhi);
                } else if (temp[2] == "N") {

                    vehicleNorth.add(vhi);
                } else if (temp[2] == "S") {

                    vehicleSouth.add(vhi);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Intializing and adding vehcile class from the data obtained from gui
    public void add_Vehicle_gui(String typ,int num,char in_s,double cross_time,char direct_to,double leng,double co2){
        Vehicle fc=new Vehicle(typ, num, in_s, cross_time, direct_to, false, leng, co2);
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
                System.out.println(temp[1]);
                intersection.push(temp);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}