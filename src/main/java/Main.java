public class Main {

    public static void main(String[] args) {

        //TODO dodac pary
        //TODO odczyt labiryntu binarnego do mAr

        String filename = "maze.txt";

        Maze maze = new Maze(filename);
        maze.Init();

        System.out.println(maze.paramsToString());
        //System.out.println(maze.toString());


    }
}
