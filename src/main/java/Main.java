import java.io.File;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        String filename = "maze.txt";
        Maze maze = new Maze(filename);
        maze.mazeInit();
        MazeGraph graph = new MazeGraph(maze);
        System.out.println(maze.paramsToString());

        graph.graphInit();
        System.out.println("\nNode count: " + graph.getNodeCount());

        //System.out.println(graph.nodesToString());


        graph.dijsktra();


        System.out.println(maze);
        //maze.arrayToFile("test.txt");

        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });


    }

}

