import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class GUI extends JFrame {
    private JPanel rysowanie;
    public Maze m;
    private String filename;
    private int wielkosc_komorki = 10;
    private int new_start_x;
    private int new_start_y;
    private int new_end_x;
    private int new_end_y;

    private boolean npp=false;//punkt poczatkowy
    private boolean npk=false;//punkt koncowy

    public GUI() {
        setTitle("Labirynt");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Menu");

        JMenuItem open = new JMenuItem("Otwórz");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent o) {
                JFileChooser wybor_pliku = new JFileChooser("mazes");
                int returnValue = wybor_pliku.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = wybor_pliku.getSelectedFile();
                    filename = selectedFile.getName();
                    m= new Maze(filename);
                    m.mazeInit();
                    new_end_x=m.getEnd_x();
                    new_end_y=m.getEnd_y();
                    new_start_x=m.getStart_x();
                    new_start_y=m.getStart_y();
                    wielkosc_komorki=10;
                    rysowanie.repaint();
                }
            }
        });

        JMenuItem save = new JMenuItem("Zapisz");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent z) {
                    if (filename != null && m != null) {
                        m.arrayToFile(filename);
                    }
            }
        });

        JMenu toolMenu = new JMenu("Narzędzia");

        JMenuItem start = new JMenuItem("Nowy punkt początkowy");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pp) {
                m= new Maze(filename);
                m.mazeInit();
                m.swapStart(new_start_x, new_start_y);
                m.swapEnd(new_end_x, new_end_y);
                repaint();
                npp=true;
            }
        });

        JMenuItem stop = new JMenuItem("Nowy punkt końcowy");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pk) {m= new Maze(filename);
                m.mazeInit();
                m.swapStart(new_start_x, new_start_y);
                m.swapEnd(new_end_x, new_end_y);
                repaint();
                npk=true;
            }
        });

        JMenuItem findPath = new JMenuItem("Znajdź ścieżkę");
        findPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent s) {
                MazeGraph graph = new MazeGraph(m);
                graph.graphInit();
                graph.dijsktra();
                rysowanie.repaint();
            }
        });

        JMenuItem changeSize = new JMenuItem("Grubość sciany");
        changeSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent gs) {
                suwak_grubosci_sciany();
                if(wielkosc_komorki==0)
                    wielkosc_komorki = 1;
                rysowanie.repaint();
            }
        });

        fileMenu.add(open);
        fileMenu.add(save);
        toolMenu.add(findPath);
        toolMenu.add(start);
        toolMenu.add(stop);
        toolMenu.add(changeSize);
        menuBar.add(fileMenu);
        menuBar.add(toolMenu);

        setJMenuBar(menuBar);

        rysowanie = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (m != null) {

                    for (int i = 0; i < m.getSize_y(); i++) {
                        for (int j = 0; j < m.getSize_x(); j++) {
                            if (m.getCell(i, j) == 'X') {
                                g.setColor(Color.BLACK);
                            } else if (m.getCell(i, j) == 'P') {
                                g.setColor(Color.GREEN);
                            } else if (m.getCell(i, j) == 'K') {
                                g.setColor(Color.GREEN);
                            } else if (m.getCell(i, j) == '.'){
                                g.setColor(Color.RED);
                            } else {
                                g.setColor(Color.WHITE);
                            }
                            g.fillRect(j * wielkosc_komorki, i * wielkosc_komorki, wielkosc_komorki, wielkosc_komorki);
                        }
                    }
                }
            }
        };
        rysowanie.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(npp){
                    Point p = e.getPoint();
                    int x = p.x / wielkosc_komorki;
                    int y = p.y / wielkosc_komorki;

                    if(m!=null && m.getCell(x,y)=='X'&&czy_obramowka(x,y)){
                        m.swapStart(x,y);
                        new_start_x=x;
                        new_start_y=y;
                        repaint();
                        npp=false;
                    }
                }
                if(npk){
                    Point p = e.getPoint();
                    int x = p.x / wielkosc_komorki;
                    int y = p.y / wielkosc_komorki;
                    if(m!=null && m.getCell(x,y)=='X'&&czy_obramowka(x,y)){
                        m.swapEnd(x,y);
                        new_end_x=x;
                        new_end_y=y;
                        repaint();
                        npk=false;
                    }
                }
            }
        });


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(rysowanie, BorderLayout.CENTER);
        add(mainPanel);
        setLocationRelativeTo(null);

    }
    private void suwak_grubosci_sciany(){
        JDialog suwak_okno = new JDialog((Frame) null,"Ustaw grubość ściany",true);

        suwak_okno.setSize(300,150);
        suwak_okno.setLayout(new BorderLayout());

        JSlider suwak =new JSlider(0,30,10);
        suwak.setMajorTickSpacing(5);
        suwak.setPaintLabels(true);
        suwak.setPaintTicks(true);

        JLabel wartosc = new JLabel("Grubość ściany "+suwak.getValue());

        suwak.addChangeListener(e -> wartosc.setText("Grubość ściany "+suwak.getValue()));

        JPanel panel_suwaka = new JPanel();
        panel_suwaka.add(suwak);
        suwak_okno.add(wartosc, BorderLayout.NORTH);
        suwak_okno.add(panel_suwaka, BorderLayout.CENTER);

        JButton suwak_zapisz = new JButton("Zapisz");
        suwak_zapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wielkosc_komorki=suwak.getValue();
                suwak_okno.dispose();
            }
        });

        JPanel panel_suwaka_zapisz = new JPanel();
        panel_suwaka_zapisz.add(suwak_zapisz);
        suwak_okno.add(panel_suwaka_zapisz, BorderLayout.SOUTH);

        suwak_okno.setLocationRelativeTo(null);
        suwak_okno.setVisible(true);

    }

    private boolean czy_obramowka(int x, int y){
        if((x==0 && y==0) || (x==0 && y==m.getSize_y()-1) || (x==m.getSize_x()-1 && y==0) || (x==m.getSize_x()-1 && y==m.getSize_y()-1)){
            return false;
        }
        if(x==0&&y!=0 || y==0&&x!=0 || x==m.getSize_x()-1 ||y==m.getSize_y()-1){
        return true;
        }
        return false;

    }

}
