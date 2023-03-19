import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws noSegmentException {

        VehicleModal model = new VehicleModal();
        GUI view = new GUI();
        view.invoke();
        VehicleController controller = new VehicleController(model, view);

        model.loadDataFromCSV("Vehicles.csv");
        controller.showView();
        // Scanner Vehicles = null, Intersection = null;

        // Intersection = ReadFile("Intersection.csv");
        // Vehicles = ReadFile("Vehicles.csv");
        // initializeProgram();

        // }

        // //Initializes all the Program Main Components
        // private static void initializeProgram() throws noSegmentException {
        // VehicleModal vh = new VehicleModal();

        // vh.intersection();
        // GUI gui = new GUI();
        // gui.invoke(vh);

        // }

        // //Read File Function
        // private static Scanner ReadFile(String Filename) {
        // Scanner Temp = null;
        // try {
        // Temp = new Scanner(new File(Filename));
        // } catch (FileNotFoundException e) {
        // System.out.println(e.toString());
        // System.exit(0);
        // }
        // return Temp;

        // }
    }
}