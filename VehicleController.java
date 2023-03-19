import java.util.Observer;

public class VehicleController {
    private VehicleModal model;
    private GUI view;

    public VehicleController(VehicleModal model, GUI view) {
        this.model = model;
        this.view = view;

        // Register the view as an observer of the model
        
        model.addObserver(view);
        
    }

    // public void loadData(String fileName) throws noSegmentException {
    //     model.loadDataFromCSV(fileName);
    // }

    // public void add_veh(String typ, int num, char in_s, double cross_time, char
    // direct_to, double leng,
    // double co2) throws noSegmentException {
    // model.add_Vehicle_gui(typ, num, in_s, cross_time, direct_to, leng, co2);
    // }

    public void showView() {
        view.show();
    }
}