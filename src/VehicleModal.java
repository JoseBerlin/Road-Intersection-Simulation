import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class VehicleModal extends Observable implements Runnable {
    private Map<Character, Queue<Vehicle>> allvehicle;
    private Queue<Intersection> intersection;
    private Queue<Vehicle> vehicleEast;
    private Queue<Vehicle> vehicleWest;
    private Queue<Vehicle> vehicleNorth;
    private ArrayList<Double[]> phases;
    int hh = 0, pl = 0, dblin = 0;
    Double totalemmsion;
    private Queue<Vehicle> vehicleSouth;
    Thread myThread;
    long startTime, elptime;
    boolean cont = true;
    Map<Character, List<Double[]>> segstat;

    Double crossingTime, waitintTime, waitingLength, totalCo2;
    List<Double[]> dummystat = new ArrayList<>();
    private static final DecimalFormat decon = new DecimalFormat("0.00");

    public VehicleModal() {
        vehicleEast = new LinkedList<>();
        vehicleWest = new LinkedList<>();
        vehicleNorth = new LinkedList<>();
        vehicleSouth = new LinkedList<>();
        allvehicle = new HashMap<>();
        intersection = new LinkedList<>();
        phases = new ArrayList<>();
        Double[] arrayd = { 0.0, 0.0, 0.0 };
        dummystat.add(arrayd);
        dummystat.set(0, arrayd);
        segstat = new HashMap<>();
        segstat.put('E', dummystat);
        segstat.put('W', dummystat);
        segstat.put('S', dummystat);
        segstat.put('N', dummystat);
        totalemmsion = 0.0;

    }

    // Reading vehicle csv file and adding it to stack
    public void loadDataFromCSV(String filename) throws noSegmentException {
        try {

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
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
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
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
        Logger.getInstance().addEntry(String.format("Vehicle %d has been added to the segment", num));
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
                intersection.add(intersection1);
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    // start time
    public void starttime() {
        startTime = System.currentTimeMillis();
    }

    // elapsed tim
    public long elapsedtime() {
        elptime = System.currentTimeMillis() - startTime;
        return elptime;
    }

    // add phases value
    public void addvaluePhase(double numb, double secval) {
        phases.add(new Double[] { numb,
                Double.parseDouble(String.format("%.2f", secval)) });
        Change(phases);
    }

    // random creation of vehicle instances
    public void randomGeneration() throws noSegmentException {

        String[] typearray = { "Bus", "Car", "Truck" };
        // Used ThreadLocalRandom class inorder to make it thread-safe while generating
        // Random number values
        // rnd variable for generating random int values
        int rnd = ThreadLocalRandom.current().nextInt(typearray.length);
        String type = typearray[rnd];

        int plateno = 2000000 + pl++;
        char[] segmentarray = { 'N', 'S', 'E', 'W' };
        rnd = ThreadLocalRandom.current().nextInt(segmentarray.length);
        char seg = segmentarray[rnd];
        double crossigntime = ThreadLocalRandom.current().nextDouble(10, 25);
        crossigntime = Double.parseDouble(decon.format(crossigntime));
        while (seg == segmentarray[rnd]) {
            rnd = ThreadLocalRandom.current().nextInt(segmentarray.length);
        }
        char segto = segmentarray[rnd];
        double waitlen = ThreadLocalRandom.current().nextDouble(10, 30);
        waitlen = Double.parseDouble(decon.format(waitlen));
        double co2eem = ThreadLocalRandom.current().nextDouble(5, 34);
        co2eem = Double.parseDouble(decon.format(co2eem));
        Logger.getInstance().addEntry(String.format("New vehile %d has been generated", plateno));
        add_Vehicle_gui(type, plateno, seg, crossigntime, segto, waitlen, co2eem);

    }

    // stat calculations
    public void statcal(Vehicle curveh, Intersection cursection, long waiting) {
        List<Double[]> dummy = segstat.get(cursection.getSegment_in());
        Double[] vbv = dummy.get(0);

        double wl = vbv[1];
        double ct = vbv[2];
        double elp = elapsedtime();
        elp = (double) (elp / 1000F);
        Double[] arrayd = { elp, wl + curveh.getLength(), ct + waiting / 1000 };
        List<Double[]> statdum = new ArrayList<>();
        statdum.add(arrayd);
        statdum.set(0, arrayd);
        segstat.put(cursection.getSegment_in(), statdum);
        Change(segstat);
    }

    public void co2total(Vehicle curveh, Intersection cursection, long waiting) {
        double totlwait = elapsedtime();
        double emmision = curveh.getCo2_emission();
        String type = curveh.getType();
        double typ = 1.0;
        totlwait = (double) (totlwait / 1000F);

        if (type.equals("Car")) {
            typ = 1.8;
        } else if (type.equals("Truck")) {
            typ = 2.9;
        } else if (type.equals("Bike")) {
            typ = 1.2;
        }
        totalemmsion += ((waiting / 1000) * emmision) + (totlwait / emmision) * typ;
        Change(totalemmsion);
    }

    // threading
    public void startThread(Vehicle curveh, TrafficLight trafficLight, Intersection cursection, long waiting,
            boolean newphase) {

        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Do something with the parameter passed in

                long elapsedTime;

                synchronized (this) {

                    if (cursection.getDirection_1() == curveh.getDirection_to()
                            || cursection.getDirection_2() == curveh.getDirection_to()) {

                        if (trafficLight.getState().equals("green")) {

                            try {
                                wait(waiting); // wait until the thread is finished
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } else {
                            try {
                                wait(waiting); // wait until the thread is finished
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                        elapsedTime = System.currentTimeMillis() - startTime;
                        double elp = (double) (elapsedTime / 1000F);

                        curveh.setCrossed(true);
                        Logger.getInstance().addEntry(
                                String.format("Vehicle %d has crossed the intersection", curveh.getPlate_no()));
                        Change(allvehicle);

                    }
                }

            }
        });

        myThread.start();

    }

    public Queue<Intersection> getIntersection() {
        return intersection;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}