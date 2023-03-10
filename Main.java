import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws noSegmentException {

        Scanner Vehicles = null, Intersection = null;

        Intersection = ReadFile("Intersection.csv");
        Vehicles = ReadFile("Vehicles.csv");
        initializeProgram();

    }

    //Initializes all the Program Main Components
    private static void initializeProgram() throws noSegmentException {
        vehicleManger vh = new vehicleManger();
        vh.vehicle();
        vh.intersection();
        GUI gui = new GUI();
        gui.invoke(vh);

    }

    //Read File Function
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