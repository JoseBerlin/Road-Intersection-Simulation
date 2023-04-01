import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class VehicleModal extends Observable {
    private Map<Character, Queue<Vehicle>> allvehicle;
    private Stack<Intersection> intersection = new Stack<>();
    private Queue<Vehicle> vehicleEast;
    private Queue<Vehicle> vehicleWest;
    private Queue<Vehicle> vehicleNorth;
    private Queue<Vehicle> vehicleSouth;

    Double crossingTime, waitintTime, waitingLength, totalCo2;

    public VehicleModal() {
        vehicleEast = new LinkedList<>();
        vehicleWest = new LinkedList<>();
        vehicleNorth = new LinkedList<>();
        vehicleSouth = new LinkedList<>();
        allvehicle = new HashMap<>();

    }

    // Reading vehicle csv file and adding it to stack
    public void loadDataFromCSV(String filename) throws noSegmentException {
        try {

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(",");
                if (temp[2].charAt(0) == 'E' || temp[2].charAt(0) == 'W' || temp[2].charAt(0) == 'N'
                        || temp[2].charAt(0) == 'S') {
                    if (temp[4].charAt(0) == 'E' || temp[4].charAt(0) == 'W'
                            || temp[4].charAt(0) == 'N'
                            || temp[4].charAt(0) == 'S') {
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
                    } else {
                        throw new noSegmentException(
                                temp[4].charAt(0) + " segment doesnt Exist. Only W,N,E,S segments exsits.");

                    }
                } else {
                    throw new noSegmentException(
                            temp[2].charAt(0) + " segment doesnt Exist. Only W,N,E,S segments exsits.");
                }
            }
            reader.close();

            getVehicleQueue();
            Change(allvehicle);
           

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Initializing and adding vehicle class from the data obtained from gui
    public void add_Vehicle_gui(String typ, int num, char in_s, double cross_time, char direct_to, double leng,
            double co2) throws noSegmentException {

        if (in_s == 'E' || in_s == 'W' || in_s == 'N' || in_s == 'S') {
            if (direct_to == 'E' || direct_to == 'W'
                    || direct_to == 'N' || direct_to == 'S') {
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

            } else {
                throw new noSegmentException(direct_to + " segment doesnt Exist. Only W, N, E, S segments exsits.");

            }
        } else {
            throw new noSegmentException(in_s + " segment doesnt Exist. Only W, N, E, S segments exsits.");
        }
        Change(allvehicle);
    }

    // returning all vehicles data
    public Map<Character, Queue<Vehicle>> getVehicleQueue() throws noSegmentException {

        allvehicle.put('E', vehicleEast);
        allvehicle.put('W', vehicleWest);
        allvehicle.put('N', vehicleNorth);
        allvehicle.put('S', vehicleSouth);

        return allvehicle;
    }

    public void Change(Object obj) {
        setChanged();
        notifyObservers(obj);
    }

    // Reading intersection csv file and adding it to stack
    public void Loaddataintersection(String filename) {
        String inter = "";
        int i = 0;
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filename));
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

    // calculation for CO2 emmision
    public double calCo2() {
        totalCo2 = 0.0;
        for (Vehicle vh : vehicleEast) {
            totalCo2 += vh.getCrossing_time();
        }
        for (Vehicle vh : vehicleWest) {
            totalCo2 += vh.getCrossing_time();
        }
        for (Vehicle vh : vehicleNorth) {
            totalCo2 += vh.getCrossing_time();
        }
        for (Vehicle vh : vehicleSouth) {
            totalCo2 += vh.getCrossing_time();
        }
        
        return totalCo2;
    }

    // initializing the values to nill
    public void segNill() {
        crossingTime = 0.0;
        waitingLength = 0.0;
        waitintTime = 0.0;
    }

    // calcuations for segment table
    public HashMap<Character, Double[]> calSegment() {
        HashMap<Character, Double[]> segstat = new HashMap<Character, Double[]>();
        segNill();
        for (Vehicle vh : vehicleEast) {
            crossingTime += vh.getCrossing_time();
            waitingLength += vh.getLength();
            waitintTime += vh.getCo2_emission();

        }
        Double[] ddb = new Double[] { crossingTime, waitingLength, waitintTime };
        segstat.put('E', ddb);

        segNill();

        for (Vehicle vh : vehicleWest) {
            crossingTime += vh.getCrossing_time();
            waitingLength += vh.getLength();
            waitintTime += vh.getCo2_emission();

        }
        ddb = new Double[] { crossingTime, waitingLength, waitintTime };
        segstat.put('W', ddb);

        segNill();

        for (Vehicle vh : vehicleNorth) {
            crossingTime += vh.getCrossing_time();
            waitingLength += vh.getLength();
            waitintTime += vh.getCo2_emission();

        }
        ddb = new Double[] { crossingTime, waitingLength, waitintTime };
        segstat.put('N', ddb);

        segNill();

        for (Vehicle vh : vehicleSouth) {
            crossingTime += vh.getCrossing_time();
            waitingLength += vh.getLength();
            waitintTime += vh.getCo2_emission();

        }
        ddb = new Double[] { crossingTime, waitingLength, waitintTime };
        segstat.put('S', ddb);

        return segstat;

    }

}