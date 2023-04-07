import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Logger {
    private static Logger instance = null;
    private ArrayList<String> Entries = new ArrayList<>();

    // Private constructor to avoid instantiation from outside the class
    private Logger() {
    }

    // Checks and create class instance if instance doesn't already exists
    public static synchronized Logger getInstance() {
        // Checking whether the instance exists already
        if (instance == null) {
            // Intializing instance if doesn't exists
            instance = new Logger();
        }
        return instance;
    }

    // Adding each log Entries to an ArrayList
    public synchronized void addEntry(String log_msg) {
        Entries.add(log_msg);
    }

    // The following function Processes the recorded logs and creates the log file
    // using it
    public void writeLogsToFile(String FileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FileName))) {
            writer.println("\t\tRoad Intersection Simulation LOG");
            writer.println("\t\t**** ************ ********** ***");
            writer.println();
            for (String entry : Entries) {
                writer.println(entry);
            }
            writer.println("\tSimulation has been ended");
        } catch (IOException e) {
            System.err.println("Error while writing to log file: " + e.getMessage());
        }
    }
}
