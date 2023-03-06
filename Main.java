import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner Vehicles = null, Intersection = null;
        try {
            Intersection = new Scanner(new File("Intersection.csv"));
            Vehicles = new Scanner(new File("Vehicles.csv"));
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            System.exit(0);
        }
        vehicleManger vh = new vehicleManger();
        vh.vehicle();
        vh.intersection();
        GUI gui = new GUI();
        gui.Invoke(vh);

    }
}