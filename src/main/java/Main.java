public class Main {

    public static void main(String[] args) {

        //TODO dodac pary

        String filename = "maze.bin";

        Maze maze = new Maze(filename);
        maze.Init();

        System.out.println(maze.paramsToString());

        //System.out.println(maze);
        //maze.arrayToFile();
        //System.out.println(maze.getCell(512,512));


    }
}
