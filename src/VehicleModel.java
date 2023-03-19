import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Observable;

public class VehicleModel extends Observable {
    private LinkedList<Vehicle> vehicleQueue;

    public VehicleModel() {
        vehicleQueue = new LinkedList<Vehicle>();
    }

    public void loadDataFromCSV(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Vehicle vehicle = new Vehicle(parts[0], Integer.parseInt(parts[1]), parts[2].charAt(0),
                        Double.parseDouble(parts[3]), parts[4].charAt(0), Boolean.parseBoolean(parts[5]),
                        Double.parseDouble(parts[6]), Double.parseDouble(parts[7]));
                vehicleQueue.add(vehicle);
            }
            reader.close();
            setChanged();
            notifyObservers(vehicleQueue);
        } catch (Exception e) {
            System.out.println("Error while loading data from CSV: " + e.getMessage());
        }
    }

    public LinkedList<Vehicle> getVehicleQueue() {
        return vehicleQueue;
    }
}
