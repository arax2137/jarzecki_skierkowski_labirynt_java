import static java.lang.Math.*;

public class Edge {

    private Node destination;
    private int distance;

    public Edge(Node start, Node destination){
        this.destination = destination;
        distance = (int) sqrt(pow(destination.getX()-start.getX(),2) + pow(destination.getY()-start.getY(),2));
    }

    public int getDistance() {
        return distance;
    }

    public Node getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Edge: destination - " + destination + ", distance - " + distance;
    }
}
