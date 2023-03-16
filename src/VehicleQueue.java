import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VehicleQueue {

    private Queue<Vehicle> queue;

    public VehicleQueue() {
        queue = new LinkedList<>();
    }

    public void enqueue(Vehicle vehicle) {
        queue.add(vehicle);
    }

    public Vehicle dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public List<Vehicle> toList() {
        return null;
    }
}