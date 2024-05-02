import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Maze {
    private String name;
    private int size_x = 0;
    private int size_y = 0;
    private char mAr[][];

    public Maze(String name) {
        this.name = name;
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
    public void getSizeT(){
        File f = new File("mazes/"+name);

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
    public void readT(){
        File f = new File("mazes/"+name);
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

    /**
     * Inicjuje labirynt dla pliku tekstowego - zczytuje wielkość labiryntu za pomocą {@link Maze#getSizeT()}, tworzy tablicę 2D {@link Maze#mAr}, zczytuje labirynt do tablicy za pomocą {@link Maze#readT()}
     */
    public void mInitT(){
        getSizeT();
        mAr = new char[size_y][size_x];
        readT();
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






}
