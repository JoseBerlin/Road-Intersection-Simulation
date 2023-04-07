
public class Main {

    public static void main(String[] args) throws noSegmentException, InterruptedException {

        VehicleModal model = new VehicleModal();
        GUI view = new GUI();
        view.invoke();
        VehicleController controller = new VehicleController(model, view);

        controller.loadData("Vehicles.csv");
        controller.loadDataInter("Intersection.csv");
        controller.showView();
        controller.start();

        controller.threadrun();

    }
}