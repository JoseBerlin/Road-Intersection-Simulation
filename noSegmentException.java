//This exception class checks whether the segment has North, West, South, or East directions.
// If any other direction is specified in the Vehicle Object, the exception is thrown, stating that "Segment does not exist." 
public class noSegmentException extends Exception {

    public noSegmentException(String exMessage) {
        super(exMessage);

    }
}
