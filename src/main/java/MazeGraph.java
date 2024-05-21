import java.util.HashMap;

public class MazeGraph {

    HashMap<String, Node> graph;
    Maze m;

    public MazeGraph(Maze m){
        graph = new HashMap<>();
        this.m = m;
    }

    public void addNode(Node n){
        graph.put(n.label, n);
    }

    public Node getNode(String label){
        return graph.get(label);
    }

    public int getNodeCount(){
        return graph.size();
    }

    public String nodesToString(){
        String output = "";
        output += "Nodes in graph:\n";
        for(Node i : graph.values()){
            output += i + "\n";
            output += i.edgesToString() + "\n";
        }
        return output;
    }

    public void graphInit(){

        /*
        przechodzimy labirynt wierszami

        dla każdego pola sprawdzamy czy jest węzłem:
        jeżeli droga nieprosta
        droga prosta ale skrzyżowanie 3 dróg/4 dróg
        ślepa uliczka

        start/koniec - mają współrzędne na granicy done

        jeżeli węzeł to:
        przechodze w lewo i w górę, aż nie napotkam ściany
        jeżeli napotkam ścianę to nic nie robię
        jeżeli napotkam węzeł to dodaje go jako sąsiada, i do znalezionego węzła dodaje pierwotny węzeł jako sąsiada
         */


        for(int i = 1; i < m.getSize_y()-1; i++) {
            for (int j = 1; j < m.getSize_x()-1; j++) {

                if(m.getCell(i,j) == ' ') {

                    //sprawdzam czy skrzyzowanie / slepa uliczka
                    int exits = 0;
                    if (m.getCell(i - 1, j) == ' ') {
                        exits++;
                    }
                    if (m.getCell(i + 1, j) == ' ') {
                        exits++;
                    }
                    if (m.getCell(i, j - 1) == ' ') {
                        exits++;
                    }
                    if (m.getCell(i, j + 1) == ' ') {
                        exits++;
                    }

                    if (exits != 2) {
                        addNode(new Node(j, i));
                    }

                    //sprawdzam czy zakret
                    else if (
                            (m.getCell(i, j - 1) == ' ' && m.getCell(i, j + 1) != ' ') ||
                                    (m.getCell(i, j + 1) == ' ' && m.getCell(i, j - 1) != ' ') ||
                                    (m.getCell(i + 1, j) == ' ' && m.getCell(i - 1, j) != ' ') ||
                                    (m.getCell(i - 1, j) == ' ' && m.getCell(i + 1, j) != ' ')
                    ) {
                        addNode(new Node(j, i));
                    }
                }
            }

        }
        addNode(new Node(m.getStart_x(), m.getStart_y()));
        addNode(new Node(m.getEnd_x(), m.getEnd_y()));





    }

}
