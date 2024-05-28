public class Main {

    public static void main(String[] args) {

        //TODO dodac pary

        String filename = "maze_forks_5_5.txt";

        Maze maze = new Maze(filename);
        maze.mazeInit();

        //MazeGraph graph = new MazeGraph(maze);
        System.out.println(maze.paramsToString());
        //graph.graphInit();
        //System.out.println("\nNode count: " + graph.getNodeCount());
        //System.out.println(graph.nodesToString());


        System.out.println(maze);
        maze.arrayToFile("test.txt");
        //System.out.println(maze.getCell(512,512));


    }
}
