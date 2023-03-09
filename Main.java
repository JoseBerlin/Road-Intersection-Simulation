import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws noSegmentException {

        Scanner Vehicles = null, Intersection = null;
        // try {
        // Intersection = new Scanner(new File("Intersection.csv"));
        // Vehicles = new Scanner(new File("Vehicles.csv"));
        // } catch (FileNotFoundException e) {
        // System.out.println(e.toString());
        // System.exit(0);
        // }

        // vehicleManger vh = new vehicleManger();
        // vh.vehicle();
        // vh.intersection();
        // GUI gui = new GUI();
        // gui.invoke(vh);

        Intersection = ReadFile("Intersection.csv");
        Vehicles = ReadFile("Vehicles.csv");
        initializeProgram();

    }

    private static void initializeProgram() throws noSegmentException {
        vehicleManger vh = new vehicleManger();
        vh.vehicle();
        vh.intersection();
        GUI gui = new GUI();
        gui.invoke(vh);

    }

    private static Scanner ReadFile(String Filename) {
        Scanner Temp = null;
        try {
            Temp = new Scanner(new File(Filename));
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            System.exit(0);
        }
        return Temp;

    }
}