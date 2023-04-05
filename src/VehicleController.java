import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observer;
import java.util.Queue;
import java.util.Stack;

import org.junit.platform.console.shadow.picocli.CommandLine.Model;

public class VehicleController extends Thread {
    private VehicleModal model;
    private GUI view;

    public VehicleController(VehicleModal model, GUI view) {
        this.model = model;
        this.view = view;

        // Register the view as an observer of the model

        model.addObserver(view);

        view.getAddButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String[] vida = view.getTableinfo();
                    model.add_Vehicle_gui(vida[0], Integer.parseInt(vida[1]),
                            vida[2].charAt(0),
                            Double.parseDouble(vida[3]), vida[4].charAt(0),
                            Double.parseDouble(vida[5]),
                            Double.parseDouble(vida[6]));
                } catch (NumberFormatException | noSegmentException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

    }

    public void loadData(String fileName) throws noSegmentException {
        model.loadDataFromCSV(fileName);
    }

    public void loadDataInter(String fileName) throws noSegmentException {
        model.Loaddataintersection(fileName);
    }

    // public void add_veh(String typ, int num, char in_s, double cross_time, char
    // direct_to, double leng,
    // double co2) throws noSegmentException {
    // model.add_Vehicle_gui(typ, num, in_s, cross_time, direct_to, leng, co2);
    // }

    public void showView() {
        view.show();
    }

    public void threadrun() throws noSegmentException, InterruptedException {
        boolean newphase = false;
        TrafficLight trafficLight = new TrafficLight("green");
        int sigtest = 0;
        long waiting = 0;
        Queue<Intersection> intersection = model.getIntersection();
        model.starttime();

        while (!intersection.isEmpty()) {
            model.randomGeneration();
            model.randomGeneration();
            model.randomGeneration();
            Intersection currentsection = intersection.poll();
            Map<Character, Queue<Vehicle>> allvehicle = model.getVehicleQueue();
            Queue<Vehicle> tempveh = allvehicle.get(currentsection.getSegment_in());
            System.out.println(currentsection.getSegment_in());
            synchronized (this) {
                for (Vehicle vc : tempveh) {

                    if (!vc.isCrossedinB()) {
                        if (sigtest % 4 == 0) {
                            trafficLight.setState("red");
                        }

                        waiting = (long) vc.getCrossing_time() * 1000;
                        if (trafficLight.getState().equals("red")) {
                            waiting += 10000;
                        }
                        model.startThread(vc, trafficLight, currentsection, waiting, newphase);

                        try {
                            wait(waiting); // wait until the thread is finished
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        model.statcal(vc, currentsection, waiting);
                        model.co2total(vc, currentsection, waiting);

                        // while (model.tstatus) {
                        // wait();
                        // }

                        trafficLight.setState("green");
                        sigtest++;
                        // vc.notifyAll();
                    } else {

                        continue;
                    }

                }
                double elapsedTime = model.elapsedtime();
                double fac = (double) (elapsedTime / 1000F);
                model.addvaluePhase(
                        currentsection.getPhases(), fac);
                model.randomGeneration();
                // model.randomGeneration();
                // model.randomGeneration();
            }
        }
    }
}