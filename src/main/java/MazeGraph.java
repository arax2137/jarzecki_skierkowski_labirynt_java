
import java.util.*;

public class MazeGraph {

    HashMap<String, Node> graph;
    Maze m;
    HashMap<Node, Integer> distance;
    HashMap<Node, Node> previous;


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

    /**
     * inicjuje graf - tworzy wierzcholki i dodaje do niech krawedzie
     */
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

        if(m.getStart_x() == m.getEnd_x() && m.getStart_y() == m.getEnd_y()){
            System.err.println("Ustawiono punkty startowe i końcowe na sobie");
            System.exit(1);
        }


        for(int i = 1; i < m.getSize_y()-1; i++) {
            for (int j = 1; j < m.getSize_x()-1; j++) {

                if(m.getCell(i,j) == ' ') {
                    boolean isNode = false;

                    //sprawdzam czy skrzyzowanie / slepa uliczka
                    int exits = 0;
                    if (m.getCell(i - 1, j) == ' ' || m.getCell(i - 1, j) == 'P' || m.getCell(i - 1, j) == 'K') {
                        exits++;
                    }
                    if (m.getCell(i + 1, j) == ' ' || m.getCell(i + 1, j) == 'P' || m.getCell(i + 1, j) == 'K') {
                        exits++;
                    }
                    if (m.getCell(i, j - 1) == ' ' || m.getCell(i, j - 1) == 'P' || m.getCell(i, j - 1) == 'K') {
                        exits++;
                    }
                    if (m.getCell(i, j + 1) == ' ' || m.getCell(i, j + 1) == 'P' || m.getCell(i, j + 1) == 'K') {
                        exits++;
                    }

                    if (exits != 2) {
                        isNode = true;
                    }

                    //sprawdzam czy zakret
                    else if (
                            (m.getCell(i, j - 1) == ' ' && m.getCell(i, j + 1) != ' ') ||
                                    (m.getCell(i, j + 1) == ' ' && m.getCell(i, j - 1) != ' ') ||
                                    (m.getCell(i + 1, j) == ' ' && m.getCell(i - 1, j) != ' ') ||
                                    (m.getCell(i - 1, j) == ' ' && m.getCell(i + 1, j) != ' ')
                    ) {
                        isNode = true;
                    }

                    //tworzenie wezla i dodawania krawedzi
                    if(isNode){
                        Node n = new Node(j,i);

                        int x = j - 1;
                        int y = i - 1;

                        while(m.getCell(i, x) != 'X' && x > 0){

                            if(graph.containsKey(createLabel(x,i))){
                                n.addEdge(new Edge(n, graph.get(createLabel(x,i))));
                                graph.get(createLabel(x,i)).addEdge(new Edge(graph.get(createLabel(x,i)), n));
                                break;
                            }
                            x--;
                        }

                        while(m.getCell(y, j) != 'X' && y > 0){
                            if(graph.containsKey(createLabel(j, y))){
                                n.addEdge(new Edge(n, graph.get(createLabel(j, y))));
                                graph.get(createLabel(j, y)).addEdge(new Edge(graph.get(createLabel(j, y)), n));
                                break;
                            }
                            y--;
                        }
                        addNode(n);
                    }
                }
            }

        }

        Node start = new Node(m.getStart_x(), m.getStart_y());
        if(m.getStart_x() == 0){
            start.addEdge(new Edge(start, graph.get(createLabel(m.getStart_x()+1, m.getStart_y()))));
            graph.get(createLabel(m.getStart_x()+1, m.getStart_y())).addEdge(new Edge(graph.get(createLabel(m.getStart_x()+1, m.getStart_y())), start ));
        }
        if(m.getStart_x() == m.getSize_x()-1){
            start.addEdge(new Edge(start, graph.get(createLabel(m.getStart_x()-1, m.getStart_y()))));
            graph.get(createLabel(m.getStart_x()-1, m.getStart_y())).addEdge(new Edge( graph.get(createLabel(m.getStart_x()-1, m.getStart_y())), start ));
        }
        if(m.getStart_y() == 0){
            start.addEdge(new Edge(start, graph.get(createLabel(m.getStart_x(), m.getStart_y()+1))));
            graph.get(createLabel(m.getStart_x(), m.getStart_y()+1)).addEdge(new Edge(graph.get(createLabel(m.getStart_x(), m.getStart_y()+1)), start));
        }
        if(m.getStart_y() == m.getSize_y()-1){
            start.addEdge(new Edge(start, graph.get(createLabel(m.getStart_x(), m.getStart_y()-1))));
            graph.get(createLabel(m.getStart_x(), m.getStart_y()-1)).addEdge(new Edge(graph.get(createLabel(m.getStart_x(), m.getStart_y()-1)), start));
        }
        addNode(start);




        Node end = new Node(m.getEnd_x(), m.getEnd_y());
        if(m.getEnd_x() == 0){
            end.addEdge(new Edge(end, graph.get(createLabel(m.getEnd_x()+1, m.getEnd_y()))));
            graph.get(createLabel(m.getEnd_x()+1, m.getEnd_y())).addEdge(new Edge(graph.get(createLabel(m.getEnd_x()+1, m.getEnd_y())), end ));
        }
        if(m.getEnd_x() == m.getSize_x()-1){
            end.addEdge(new Edge(end, graph.get(createLabel(m.getEnd_x()-1, m.getEnd_y()))));
            graph.get(createLabel(m.getEnd_x()-1, m.getEnd_y())).addEdge(new Edge( graph.get(createLabel(m.getEnd_x()-1, m.getEnd_y())), end ));
        }
        if(m.getEnd_y() == 0){
            end.addEdge(new Edge(end, graph.get(createLabel(m.getEnd_x(), m.getEnd_y()+1))));
            graph.get(createLabel(m.getEnd_x(), m.getEnd_y()+1)).addEdge(new Edge(graph.get(createLabel(m.getEnd_x(), m.getEnd_y()+1)), end));
        }
        if(m.getEnd_y() == m.getSize_y()-1){
            end.addEdge(new Edge(end, graph.get(createLabel(m.getEnd_x(), m.getEnd_y()-1))));
            graph.get(createLabel(m.getEnd_x(), m.getEnd_y()-1)).addEdge(new Edge(graph.get(createLabel(m.getEnd_x(), m.getEnd_y()-1)), end));
        }
        addNode(end);

        System.out.println("\nNode count: " + getNodeCount());


    }

    /**
     * rozwiazuje labirynt, wstawia kropki na sciezce
     */
    public void dijsktra(){

        System.out.println("Solving maze:");

        distance = new HashMap<>(); // 999999 = inf, poczatkowa odleglosc
        previous = new HashMap<>();
        PriorityQueue<Node> q = new PriorityQueue<>(new NodeComparator());



        for(Node n : graph.values()){
            distance.put(n, 999999);
            previous.put(n, null);
            q.offer(n);
        }

        Node start = graph.get(new Node(m.getStart_x(),m.getStart_y()).label);
        distance.replace(start, 0);
        q.remove(start);
        q.offer(start);

        Node end = graph.get(new Node(m.getEnd_x(), m.getEnd_y()).label);



        while(!q.isEmpty()){
            Node n1 = q.poll();
            //System.out.println();
            //System.out.println("Extracted node: " + n1 + ", distance = " + distance.get(n1) + ", neighbour count: " + n1.edges.size());

            if(n1.equals(end)){
                System.out.println("Exit found, distance: " + distance.get(n1));
                break;
            }


            for(Edge e : n1.edges){
                Node n2 = e.getDestination();
                //System.out.println("Analyzing neighbour " + n2);
                int altDist = distance.get(n1) + e.getDistance();
                if(altDist < distance.get(n2)){
                    previous.replace(n2, n1);
                    distance.replace(n2, altDist);
                    //System.out.println("New distance found: " + distance.get(n2));
                    q.remove(n2);
                    q.offer(n2);
                }

            }

        }

        System.out.println("Reading path: ");
        Stack<Node> s = new Stack<>();
        Node n;

        n = end;
        s.push(n);

        while(n!=start){
            m.putDots(n, previous.get(n));
            n = previous.get(n);
        }




    }

    private String createLabel(int x, int y){
        return Integer.toString(x) + "-" + Integer.toString(y);
    }

    /**
     * porownuje wezly na bazie ich odleglosci od startu
     */
    public class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            if(distance.get(o1) < distance.get(o2)){
                return -1;
            }
            else if(distance.get(o1) > distance.get(o2)){
                return 1;
            }
            return 0;
        }
    }

}
