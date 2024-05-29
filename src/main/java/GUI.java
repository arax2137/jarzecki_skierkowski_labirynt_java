import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JFrame {
    private JPanel drawPanel;
    public Maze m;
    private String filename;

    public GUI() {
        setTitle("Labirynt");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Menu");

        JMenuItem open = new JMenuItem("Otwórz");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("mazes");
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filename = selectedFile.getName();
                    m= new Maze(filename);
                    System.out.println(filename);
                    m.mazeInit();
                    drawPanel.repaint();
                }
            }
        });

        JMenuItem save = new JMenuItem("Zapisz");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filename != null && m != null) {
                    saveMazeToFile();
                }
            }
        });

        JMenu toolMenu = new JMenu("Narzędzia");

        JMenuItem start = new JMenuItem("Nowy punkt początkowy");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutaj możesz dodać logikę ustawiania nowego punktu początkowego
            }
        });

        JMenuItem stop = new JMenuItem("Nowy punkt końcowy");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutaj możesz dodać logikę ustawiania nowego punktu końcowego
            }
        });

        JMenuItem findPath = new JMenuItem("Znajdź ścieżkę");
        findPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutaj możesz dodać logikę wyszukiwania ścieżki
            }
        });

        fileMenu.add(open);
        fileMenu.add(save);
        toolMenu.add(findPath);
        toolMenu.add(start);
        toolMenu.add(stop);
        menuBar.add(fileMenu);
        menuBar.add(toolMenu);

        setJMenuBar(menuBar);

        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (m != null) {
                    int wielkosc_komorki = 10;

                    for (int i = 0; i < m.getSize_y(); i++) {
                        for (int j = 0; j < m.getSize_x(); j++) {
                            if (m.getCell(i, j) == 'X') {
                                g.setColor(Color.BLACK);
                            } else if (m.getCell(i, j) == 'P') {
                                g.setColor(Color.GREEN);
                            } else if (m.getCell(i, j) == 'K') {
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
        mainPanel.add(drawPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void saveMazeToFile() {
        // Implementacja zapisu labiryntu do pliku
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            m.arrayToFile(filename);
        }
    }

    private class Maze {

        public Maze(String mazeData) {
            String[] rows = mazeData.split("\n");
            m.size_y = rows.length;
            m.size_x = rows[0].trim().length();
            m.mAr = new char[m.size_y][m.size_x];
            for (int i = 0; i < maze.size_y; i++) {
                String row = rows[i].trim();
                for (int j = 0; j < m.size_x; j++) {
                    m.mAr[i][j] = row.charAt(j);
                }
            }
        }

    }
}
