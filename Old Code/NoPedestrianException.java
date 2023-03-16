//This is thrown when a car is crossing the segments and the pedestrian button is called. 
//This exception is thrown to prevent the activation of the pedestrian phase while cars are still crossing. 
public class NoPedestrianException extends Exception {
    public NoPedestrianException(String message) {
        super(message);

    }
}
