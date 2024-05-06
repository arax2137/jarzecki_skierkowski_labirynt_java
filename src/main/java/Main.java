
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        //TODO dodac pary

        String filename = "maze.txt";

        Maze maze = new Maze(filename);

        if(filename.endsWith(".bin")){
            System.out.println("Working on binary file");

        }
        else if(filename.endsWith(".txt")){
            System.out.println("Working on text file");

            maze.mInitT();
        }
        else{
            System.out.println("File type not recognized");
            System.exit(2);
        }

        System.out.println("x = "+maze.getSize_x() +"\ty = "+maze.getSize_y());
        System.out.println("start_x = " + maze.getStart_x() + " start_y = " + maze.getStart_y() + " end_x = " + maze.getEnd_x() + " end_y = " + maze.getEnd_y());
        System.out.println(maze.toString());


    }
}
