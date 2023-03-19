public class VehicleMain {
    public static void main(String[] args) {
        VehicleModel model = new VehicleModel();
        VehicleView view = new VehicleView();
        VehicleController controller = new VehicleController(model, view);

        controller.loadData("Vehicles.csv");
        controller.showView();
    }
}
