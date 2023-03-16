public class Intersection {
    private int phases;
    private int duration;
    private char segment_in;
    private char direction_1;
    private char direction_2;

    public Intersection(int ph, int dur, char seg, char i, char j) {
        phases = ph;
        duration = dur;
        segment_in = seg;
        direction_1 = i;
        direction_2 = j;
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

    public boolean crossPossible(char dto,char cfm){
        if(dto==segment_in && direction_1==cfm || direction_2==cfm){
            return true;
        }

        return false;
    }
}
