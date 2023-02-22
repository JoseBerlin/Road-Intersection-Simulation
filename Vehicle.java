public class Vehicle {

    private String type;
    private int plate_no;
    private char in_segment;
    private double crossing_time;
    private char direction_to;
    private boolean crossed;
    private double length;
    private double co2_emmision;

    public Vehicle(String typ,int num,char in_s,double cross_time,char direct_to,boolean cross,double leng,double co2){
        type=typ;
        plate_no=num;
        in_segment=in_s;
        crossing_time=cross_time;
        direction_to=direct_to;
        crossed=cross;
        length=leng;
        co2_emmision=co2;
    }
}