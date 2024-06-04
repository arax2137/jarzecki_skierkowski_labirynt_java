import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Maze implements ClassConstants {

    private String filename;
    private int size_x;
    private int size_y;
    private int start_x;
    private int start_y;
    private int end_x;
    private int end_y;
    private char mAr[][];
    //pola do obslugi pliku binarnego
    private int counter;
    private char wall;
    private char path;
    private char separator;

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

    /**
     * zwraca zawartosc pola na wsporzednych podanych jako parametr
     * @param y
     * @param x
     * @return
     */
    public char getCell(int y, int x) {
        return mAr[y][x];
    }

    /**
     * Zmienia start labiryntu na wspolrzedne podane jako parametr
     * @param x
     * @param y
     */
    public void swapStart(int x, int y){
        if((x==0 && y==0) || (x==0 && y==size_y-1) || (x==size_x-1 && y==0) || (x==size_x-1 && y==size_y-1)){
            System.err.println("Nie można ustawić punktu na rogu");
            return;
        }
        if(mAr[start_y][start_x] == 'P'){
            mAr[start_y][start_x] = 'X';
        }
        mAr[y][x] = 'P';
        start_x = x;
        start_y = y;

        //System.out.println("New start_x = " + start_x);
        //System.out.println("New start_x = " + start_y);
    }

    /**
     * Zmienia koniec labiryntu na wspolrzedne podane jako parametr
     * @param x
     * @param y
     */
    public void swapEnd(int x, int y){

        if((x==0 && y==0) || (x==0 && y==size_y-1) || (x==size_x-1 && y==0) || (x==size_x-1 && y==size_y-1)){
            System.err.println("Nie można ustawić punktu na rogu");
            return;
        }


        if(mAr[end_y][end_x] == 'K'){
            mAr[end_y][end_x] = 'X';
        }
        mAr[y][x] = 'K';
        end_x = x;
        end_y = y;
        //System.out.println("New end_x = " + end_x);
        //System.out.println("New end_y = " + end_y);
    }

    public void setCell(int y, int x, char a) {
        mAr[y][x] = a;
    }

    /**
     * wstawia kropki miedzy dwoma podanymi wezlami
     * @param n1
     * @param n2
     */
    public void putDots(Node n1, Node n2){
        if(n1.getX()<n2.getX()){
            for(int i=n1.getX(); i<=n2.getX();i++){
                mAr[n1.getY()][i] = '.';
            }
        }
        if(n1.getX()>n2.getX()){
            for(int i=n2.getX(); i<=n1.getX();i++){
                mAr[n1.getY()][i] = '.';
            }

        }
        if(n1.getY()<n2.getY()){
            for(int i=n1.getY(); i<=n2.getY();i++){
                mAr[i][n1.getX()] = '.';
            }

        }
        if(n1.getY()>n2.getY()){
            for(int i=n2.getY(); i<=n1.getY();i++){
                mAr[i][n1.getX()] = '.';
            }
        }




    }

    /**
     * Przypisuje polom {@link Maze#size_x} i {@link Maze#size_y} odpowiednie wartości podczas czytania z pliku
     */
    private void getSizeT() {
        File f = null;
        try {
            f = new File("mazes/" + filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //LICZYMY X SIZE
        try {
            FileReader r = new FileReader(f);
            int c;
            while ((c = r.read()) != '\n' && c != '\r') {
                size_x++;
            }
            r.close();
        } catch (IOException e) {
            System.err.println("Nie znaleziono pliku");
            System.exit(1);
        }
        //LICZYMY Y SIZE
        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                size_y++;
                s.nextLine();
            }
            s.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Odczytuje labirynt z pliku tekstowego i wpisuje go do tabeli {@link Maze#mAr}
     */
    private void readT() {
        File f = null;
        try {
            f = new File("mazes/" + filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            FileReader r = new FileReader(f);

            int c;
            int x = 0;
            int y = 0;

            while ((c = r.read()) != -1) {
                //System.out.println("Reading: y = "+y+" x = "+x+" c = "+(char)c);
                if ((char) c == '\n') {
                    y++;
                    x = 0;
                } else if ((char) c == '\r') {
                    ;
                } else {
                    if ((char) c == 'P') {
                        start_x = x;
                        start_y = y;
                    }
                    if ((char) c == 'K') {
                        end_x = x;
                        end_y = y;
                    }
                    mAr[y][x] = (char) c;
                    x++;
                }
            }
            r.close();
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku");
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * zczytuje parametry nagłówka pliku binarnego
     */
    private void getParamsB() {

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
            in = new FileInputStream("mazes/" + filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            ByteBuffer b;


            in.read(buff, 0, 4);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            fileID = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 1);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            escape = b.getInt();
            Arrays.fill(buff, (byte) 0);


            in.read(buff, 0, 2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            columns = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            lines = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            entry_x = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            entry_y = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            exit_x = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 2);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            exit_y = b.getInt();
            Arrays.fill(buff, (byte) 0);

            //RESERVED (useless)
            in.read(buff, 0, 12);
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 4);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            counter = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 4);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            solOff = b.getInt();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 1);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            separator = b.getChar();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 1);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            wall = b.getChar();
            Arrays.fill(buff, (byte) 0);

            in.read(buff, 0, 1);
            b = ByteBuffer.wrap(buff);
            b.order(ByteOrder.LITTLE_ENDIAN);
            path = b.getChar();
            Arrays.fill(buff, (byte) 0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.start_x = entry_x - 1;
        this.start_y = entry_y - 1;
        this.end_x = exit_x - 1;
        this.end_y = exit_y - 1;
        this.size_x = columns;
        this.size_y = lines;
        this.separator = separator;
        this.counter = counter;
        this.wall = wall;
        this.path = path;

        System.out.println("\nBinary file parameters:\nFileID = " + Integer.toHexString(fileID) + "\nEscape = " + Integer.toHexString(escape) + "\nColumns = " + columns +
                "\nLines = " + lines + "\nEntryX = " + entry_x + "\nEntryY = " + entry_y + "\nExitX = " + exit_x + "\nExitY = " + exit_y + "\nCounter = " + counter +
                "\nSolutionOffset = " + solOff + "\nSeparator = " + this.separator + "\nWall = " + wall + "\nPath = " + path);

        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Zczytuje labirynt z pliku binarnego do {@link Maze#mAr}
     */
    private void readB() {

        InputStream in = null;
        try {
            in = new FileInputStream("mazes/" + filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < HEADER_SIZE; i++) {
            try {
                in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int sep;
        char val, count;

        int c = 0;
        int cil = 0; //char in line

        int x = 0;
        int y = 0;

        while (c < counter) {

            try {
                sep = in.read();
                val = (char) in.read();
                count = (char) in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (val != path && val != wall)
                break;

            for (int i = 0; i <= count; i++) {

                if (cil == size_x) {
                    cil = 0;
                    y++;
                    x = 0;
                }

                if(val == wall){
                    mAr[y][x] = 'X';
                }
                else if(val == path){
                    mAr[y][x] = ' ';
                }

                x++;
                cil++;

            }

            c++;
        }

        mAr[start_y][start_x] = 'P';
        mAr[end_y][end_x] = 'K';

        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inicjuje labirynt, rozpoznaje rozszerzenie pliku
     */
    public void mazeInit() {

        if (filename.endsWith(".bin")) {
            System.out.println("Working on binary file");
            System.out.println("Filename: " + filename);
            getParamsB();
            mAr = new char[size_y][size_x];
            readB();
        } else if (filename.endsWith(".txt")) {
            System.out.println("Working on text file");
            System.out.println("Filename: " + filename);
            getSizeT();
            mAr = new char[size_y][size_x];
            readT();
        } else {
            System.out.println("File type not recognized");
            System.out.println("Filename: " + filename);
            System.exit(2);
        }

        System.out.println(paramsToString());



    }

    /**
     * Zwraca jako String zawartość tabeli {@link Maze#mAr}
     *
     * @return String
     */
    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < size_y; i++) {

            for (int j = 0; j < size_x; j++) {
                output += mAr[i][j];
            }
            output += '\n';
        }

        return output;
    }

    /**
     * Zwraca parametry labiryntu
     *
     * @return void
     */
    public String paramsToString() {
        return "\nMaze parameters:\nx = " + size_x + "\ny = " + size_y + "\nstart_x = " + start_x + "\nstart_y = " + start_y + "\nend_x = " + end_x + "\nend_y = " + end_y;
    }

    /**
     * wypisuje labirynt do pliku o nazwie podanej jako parametr
     */
    public void arrayToFile(String filename) {

        File file = new File("solutions","rozwiazanie_"+filename);
        FileWriter wr;

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            wr = new FileWriter(file);

        } catch (IOException e) {
            throw new RuntimeException(e + " (Nie udało sie utworzyć pliku)");
        }


        for (int i = 0; i < size_y; i++) {
            for (int j = 0; j < size_x; j++) {
                try {
                    wr.write(mAr[i][j]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                wr.write('\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            wr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
