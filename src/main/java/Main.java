public class Main {

    public static void main(String[] args) {

        //TODO STWORZYC OGRANICZENIA PODCZAS WYBIERANIA PUNKTOW STARTU KONCA
        String filename = "maze_cycles_1024_1024.txt";

        Maze maze = new Maze(filename);
        maze.mazeInit();

        MazeGraph graph = new MazeGraph(maze);
        System.out.println(maze.paramsToString());
        graph.graphInit();
        System.out.println("\nNode count: " + graph.getNodeCount());
        //System.out.println(graph.nodesToString());
        /*Node start = new Node( maze.getStart_x(), maze.getStart_y());
        System.out.println(graph.getNode(start.label).edgesToString());*/
        graph.dijsktra();


       // System.out.println(maze);
        //maze.arrayToFile("test.txt");



    }

}

