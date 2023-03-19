import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

public class VehicleController {
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

    // public void add_veh(String typ, int num, char in_s, double cross_time, char
    // direct_to, double leng,
    // double co2) throws noSegmentException {
    // model.add_Vehicle_gui(typ, num, in_s, cross_time, direct_to, leng, co2);
    // }

    public void showView() {
        view.show();
    }
}