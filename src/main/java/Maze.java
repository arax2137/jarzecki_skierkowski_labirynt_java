import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Scanner;

public class Maze {

    private String filename;
    private int size_x;
    private int size_y;
    private int start_x;
    private int start_y;
    private int end_x;
    private int end_y;
    private char mAr[][];

    public Maze(String name) {
        this.filename = name;
    }

    public int getStart_x() {
        return start_x;
    }
    public int getStart_y() {
        return start_y;
    }
    public int getEnd_x() {
        return end_x;
    }
    public int getEnd_y() {
        return end_y;
    }
    public int getSize_x() {
        return size_x;
    }
    public int getSize_y() {
        return size_y;
    }
    public void setSize_x(int size_x) {
        this.size_x = size_x;
    }
    public void setSize_y(int size_y) {
        this.size_y = size_y;
    }
    public char getCell(int y, int x){
        return mAr[y][x];
    }

    /**
     * Zmienia wartość komórki w tabeli 2D {@link Maze#mAr} na pozycji ({@code y},{@code x}) na wartość {@code a}
     * @param y
     * @param x
     * @param a
     */
    public void setCell(int y, int x, char a){
        mAr[y][x] = a;
    }

    /**
     *Przypisuje polom {@link Maze#size_x} i {@link Maze#size_y} odpowiednie wartości podczas czytania z pliku
     */
    private void getSizeT(){
        File f = null;
        try {
            f = new File("mazes/"+ filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //LICZYMY X SIZE
        try {
            FileReader r = new FileReader(f);
            int c;
            while((c = r.read()) != '\n' && c != '\r'){
                size_x++;
            }
        } catch (IOException e) {
            System.err.println("Nie znaleziono pliku");
            System.exit(1);
        }
        //LICZYMY Y SIZE
        try {
            Scanner s = new Scanner(f);
            while(s.hasNextLine()){
                size_y++;
                s.nextLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Odczytuje labirynt z pliku tekstowego i wpisuje go do tabeli {@link Maze#mAr}
     */
    private void readT(){
        File f = null;
        try {
            f = new File("mazes/"+ filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            FileReader r = new FileReader(f);

            int c;
            int x = 0;
            int y = 0;

            while((c = r.read()) != -1){
                //System.out.println("Reading: y = "+y+" x = "+x+" c = "+(char)c);
                if((char) c == '\n'){
                    y++;
                    x = 0;
                }
                else if((char) c == '\r'){
                    ;
                }
                else{
                    if((char) c == 'P'){
                        start_x = x;
                        start_y = y;
                    }
                    if((char) c == 'K'){
                        end_x = x;
                        end_y = y;
                    }
                    setCell(y, x, (char) c);
                    x++;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku");
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void getParamsB(){

        int fileID;
        int escape;
        int solOff;
        int columns;
        int lines;
        int entry_x;
        int entry_y;
        int exit_x;
        int exit_y;
        int counter;
        char separator;
        char wall;
        char path;

        byte buff[] = new byte[12];
        Arrays.fill(buff, (byte) 0);



        InputStream in = null;
        try {
            in = new FileInputStream("mazes/"+filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            ByteBuffer b;


            in.read(buff,0,4);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            fileID = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,1);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            escape = b.getInt();
            Arrays.fill(buff, (byte) 0);


            in.read(buff,0,2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            columns = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            lines = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            entry_x = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            entry_y = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            exit_x = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            exit_y = b.getInt();
            Arrays.fill(buff, (byte) 0);

            //RESERVED (useless)
            in.read(buff,0,12);
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,4);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            counter = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,4);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            solOff = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,1);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            separator = b.getChar();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,1);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            wall = b.getChar();
            Arrays.fill(buff, (byte) 0);

            in.read(buff,0,1);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            path = b.getChar();
            Arrays.fill(buff, (byte) 0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.start_x = entry_x-1;
        this.start_y = entry_y-1;
        this.end_x = exit_x-1;
        this.end_y = exit_y-1;
        this.size_x = columns-1;
        this.size_y = lines-1;

        System.out.println("\nBinary file parameters:\nFileID = " + Integer.toHexString(fileID) + "\nEscape = " + Integer.toHexString(escape) + "\nColumns = " + columns +
                "\nLines = " + lines + "\nEntryX = " + entry_x + "\nEntryY = " + entry_y + "\nExitX = " + exit_x + "\nExitY = " + exit_y + "\nCounter = " + counter +
                "\nSolutionOffset = " + solOff + "\nSeparator = " + separator + "\nWall = " + wall + "\nPath = " + path);




    }



    /**
     * Inicjuje labirynt, rozpoznaje rozszerzenie pliku
     */
    public void Init(){

        if(filename.endsWith(".bin")){
            System.out.println("Working on binary file");
            getParamsB();
            mAr = new char[size_y][size_x];

        }
        else if(filename.endsWith(".txt")){
            System.out.println("Working on text file");
            getSizeT();
            mAr = new char[size_y][size_x];
            readT();
        }
        else{
            System.out.println("File type not recognized");
            System.exit(2);
        }


    }


    /**
     * Zwraca jako String zawartość tabeli {@link Maze#mAr}
     * @return String
     */
    @Override
    public String toString(){
        String output = "";
        for(int i=0;i<size_y;i++){

            for(int j = 0; j < size_x; j++) {
                output += mAr[i][j];
            }
            output += '\n';
        }

        return output;
    }

    /**
     * Zwraca parametry labiryntu
     * @return void
     */
    public String paramsToString(){
        return "\nMaze parameters:\nx = "+size_x +"\ny = "+size_y + "\nstart_x = " + start_x + "\nstart_y = " + start_y + "\nend_x = " + end_x + "\nend_y = " + end_y;
    }






}
