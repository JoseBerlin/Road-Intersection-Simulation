public class Intersection {
    private int phases;
    private int duration;
    private char segment_in;
    private char direction_1;
    private char direction_2;

    public Intersection(int ph, int dur, char seg, char d1, char d2) {
        phases = ph;
        duration = dur;
        segment_in = seg;
        direction_1 = d1;
        direction_2 = d2;
    }

    public int getPhases() {
        return phases;
    }

    public int getDuration() {
        return duration;
    }

    public char getSegment_in() {
        return segment_in;
    }

    public char getDirection_1() {
        return direction_1;
    }

    public char getDirection_2() {
        return direction_2;
    }
}
