import java.util.Observer;

public class VehicleController {
    private VehicleModel model;
    private VehicleView view;

    public VehicleController(VehicleModel model, VehicleView view) {
        this.model = model;
        this.view = view;

        // Register the view as an observer of the model
        model.addObserver((Observer) view);
    }

    public void loadData(String fileName) {
        model.loadDataFromCSV(fileName);
    }

    public void showView() {
        view.show();
    }
}
