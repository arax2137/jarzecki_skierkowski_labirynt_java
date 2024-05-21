import java.util.ArrayList;
import java.util.Objects;

public class Node {

    String label;
    private int x;
    private int y;
    private ArrayList<Edge> edges;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        label = Integer.toString(x) + "-" + Integer.toString(y);
        edges = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Edge getEdge(int index){
        return edges.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node vertex = (Node) o;
        return x == vertex.x && y == vertex.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String edgesToString(){
        String output = "";
        output += "Edges starting on node " + label + ":\n";
        for(Edge i : edges){
            output += i + "\n";
        }
        return output;
    }

    @Override
    public String toString(){
        return label;
    }

    public void addEdge(Edge e){
        edges.add(e);
    }

}
