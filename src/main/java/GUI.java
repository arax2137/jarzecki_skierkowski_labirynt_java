import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JFrame {
    private JPanel rysowanie;
    private Maze m;
    private String filename;
    private int wielkosc_komorki = 10;

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
                    System.out.println(filename);
                    m.mazeInit();
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
                // Tutaj możesz dodać logikę ustawiania nowego punktu początkowego
            }
        });

        JMenuItem stop = new JMenuItem("Nowy punkt końcowy");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pk) {
                // Tutaj możesz dodać logikę ustawiania nowego punktu końcowego
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

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(rysowanie, BorderLayout.CENTER);
        add(mainPanel);
        setLocationRelativeTo(null);
    }
    private void suwak_grubosci_sciany(){
        JDialog suwak_okno = new JDialog((Frame) null,"Ustaw gróbość ściany",true);

        suwak_okno.setSize(300,150);
        suwak_okno.setLayout(new BorderLayout());

        JSlider suwak =new JSlider(5,30,10);
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
}
