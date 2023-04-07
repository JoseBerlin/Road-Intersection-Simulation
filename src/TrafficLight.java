// This class contains the status of Traffic Light
class TrafficLight {
    private String state;

    public TrafficLight(String state) {
        this.state = state;
    }

    // To change different Traffic Light State
    public synchronized void setState(String state) {
        Logger.getInstance().addEntry(String.format("Traffic Light Signal has changed to %s", state.toUpperCase()));
        this.state = state;
        notifyAll();
    }

    // Returns the current State of Traffic Light
    public synchronized String getState() {
        return state;
    }
}
