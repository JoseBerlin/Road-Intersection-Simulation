public class Vehicle {

    private String type;
    private int plate_no;
    private char in_segment;
    private double crossing_time;
    private char direction_to;
    private boolean crossed;
    private double length;
    private double co2_emission;

    public Vehicle(){}

    public Vehicle(String typ, int num, char in_s, double cross_time, char direct_to, boolean cross, double leng,
            double co2) {
        type = typ;
        plate_no = num;
        in_segment = in_s;
        crossing_time = cross_time;
        direction_to = direct_to;
        crossed = cross;
        length = leng;
        co2_emission = co2;
    }

    public String getType() {
        return type;
    }

    public int getPlate_no() {
        return plate_no;
    }

    public char getIn_segment() {
        return in_segment;
    }

    public double getCrossing_time() {
        return crossing_time;
    }

    public char getDirection_to() {
        return direction_to;
    }

    public String isCrossed() {
        if (crossed == false) {
            return "waiting";
        }
        return "crossed";
    }

    public double getLength() {
        return length;
    }

    public double getCo2_emission() {
        return co2_emission;
    }

    public double estimatedEmmision() {
        return co2_emission * crossing_time + length;
    }

    public void setCrossed(boolean b) {
    }
}
