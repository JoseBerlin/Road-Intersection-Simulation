class TrafficLight {
    private String state;

    public TrafficLight(String state) {
        this.state = state;
    }

    public synchronized void setState(String state) {
        this.state = state;
        notifyAll();
    }

    public synchronized String getState() {
        return state;
    }
}
