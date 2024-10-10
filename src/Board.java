import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class Board extends JPanel {
    private SudokuMap[][] map;
    Game game;
    int size;
    int cellSize;
    Coord c;
    List <List<SudokuMap>> mainList;
    private int index = 0;


    public Board(SudokuMap[][] map, int width, int height) {
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(width, height));
        size = Math.min(width, height);
        cellSize = size / 9;
        this.map = map;
        this.game = new Game(this.map, width, height, cellSize);

        int k = 0;
        mainList = MyFileReader.getSudokuTemplate();
        if (mainList == null) {
            System.exit(0);
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
              map[i][j] = mainList.get(index).get(k);
                k++;
            }
        }

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                c = game.handleMouseClick(me.getX(), me.getY(), cellSize);
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                try {
                    if (keyCode == 77 || keyCode == 78) {
                        int k = 0;
                        // save current game to main list's current index
                        for (int i = 0; i < map.length; i++) {
                            for (int j = 0; j < map[i].length; j++) {
                                mainList.get(index).set(k, map[i][j]);
                                k++;
                            }
                        }
                        k = 0;
                        if (keyCode == 78) {
                            index = index - 1;
                        } else {
                            index = index + 1;
                        }
                        if (index == mainList.size()) {
                            index = 0;
                        } else if (index == -1) {
                            index = mainList.size()-1;
                        }
                        // update current game
                        for (int i = 0; i < map.length; i++) {
                            for (int j = 0; j < map[i].length; j++) {
                                map[i][j] = mainList.get(index).get(k);
                                k++;
                            }
                        }
                        repaint();
                        return;
                    }
                    if (c == null) {
                        System.out.println("Please select a cell!");
                        return;
                    }
                    if (keyCode == 37 || keyCode == 65) {
                        if (c.getY() >= 1) {
                            c.setY(c.getY() - 1);
                            repaint();
                            return;
                        }
                    }
                    if (keyCode == 39 || keyCode == 68) {
                        if (c.getY() < 8) {
                            c.setY(c.getY() + 1);
                            repaint();
                            return;
                        }
                    }
                    if (keyCode == 38 || keyCode == 87) {
                        if (c.getX() >= 1) {
                            c.setX(c.getX() - 1);
                            repaint();
                            return;
                        }
                    }
                    if (keyCode == 40 || keyCode == 83) {
                        if (c.getX() < 8) {
                            c.setX(c.getX() + 1);
                            repaint();
                            return;
                        }
                    }
                    if (keyCode == 8) {
                        emptyBoard();
                        return;
                    }

                    if (map[c.getX()][c.getY()] != null) {
                        if (Boolean.TRUE.equals(map[c.getX()][c.getY()].getFixedNumber())) {
                            System.out.println("You cannot change this number!");
                            return;
                        }
                    }
                    if (keyCode == 48) {
                        map[c.getX()][c.getY()] = null;
                        repaint();
                        return;
                    }
                    map[c.getX()][c.getY()] = new SudokuMap(Integer.parseInt(KeyEvent.getKeyText(keyCode)));
                    repaint();
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter a number");
                }
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        for (int i = 1; i < 9; i++) {
            g.drawLine(0, i * cellSize, size, i * cellSize);
            g.drawLine(i * cellSize, 0, i * cellSize, size);
            if (i % 3 == 0) {
                g.drawLine(0, i * cellSize + 1, size, i * cellSize + 1);
                g.drawLine(0, i * cellSize - 1, size, i * cellSize - 1);
                g.drawLine(i * cellSize + 1, 0, i * cellSize + 1, size);
                g.drawLine(i * cellSize - 1, 0, i * cellSize - 1, size);
            }
        }

        Font font = new Font("Comic Sans", Font.BOLD, 50);
        g.setFont(font);
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                if (map[row][column] == null) {
                    continue;
                }
                FontMetrics metrics = g.getFontMetrics(font);
                int x = column * cellSize + (cellSize - metrics.stringWidth(String.valueOf(map[row][column].getNumber()))) / 2;
                int y = row * cellSize + ((cellSize - metrics.getHeight()) / 2) + metrics.getAscent();

                g.drawString(String.valueOf(map[row][column].getNumber()), x, y);
            }
        }

        List<Integer> numbers = new ArrayList<>();
        boolean canBeWin = true;
        for (int i = 0; i < map.length; i++) {
            canBeWin = true;
            numbers.clear();
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == null) {
                    continue;
                }
                if (numbers.contains(map[i][j].getNumber())) {
                    canBeWin = false;
                    g.setColor(Color.red);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setStroke((new BasicStroke(8.0F)));
                    if (c != null) {
                        g2d.drawRect(0, i * size/9, size, cellSize);
                    }
                    break;
                } else {
                    numbers.add(map[i][j].getNumber());
                }

            }
        }

        for (int i = 0; i < map.length; i++) {
            canBeWin = true;
            numbers.clear();
            for (int j = 0; j < map[i].length; j++) {
                if (map[j][i] == null) {
                    continue;
                }
                if (numbers.contains(map[j][i].getNumber())) {
                    canBeWin = false;
                    g.setColor(Color.red);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setStroke((new BasicStroke(8.0F)));
                    if (c != null) {
                        g2d.drawRect(i * size/9, 0, cellSize, size);
                    }
                    break;
                } else {
                    numbers.add(map[j][i].getNumber());
                }

            }
        }
        for (int k = 0; k < map.length / 3; k++) {
            for (int m = 0; m < map[k].length / 3; m++) {
                canBeWin = true;
                numbers.clear();
                for (int i = 0; i < map.length / 3; i++) {

                    for (int j = 0; j < map[i].length / 3; j++) {
                        if (map[k * 3 + i][m * 3 + j] == null) {
                            continue;
                        }
                        if (numbers.contains(map[k * 3 + i][m * 3 + j].getNumber())) {
                            canBeWin = false;
                            g.setColor(Color.red);
                            Graphics2D g2d = (Graphics2D) g;
                            g2d.setStroke((new BasicStroke(8.0F)));
                            if (c != null) {
                                g2d.drawRect(m * size/3, k * size/3, cellSize * 3, cellSize * 3);
                            }
                            break;
                        } else {
                            numbers.add(map[k * 3 + i][m * 3 + j].getNumber());
                        }
                    }
                }
            }
        }

        if (c != null) {
            g.setColor(Color.blue);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(8.0F));

            g2d.drawRect(c.getY() * size/9, c.getX() * size/9, cellSize, cellSize);
            //or with an extra rectangle: g.drawRect(c.getX()*100-1, c.getY()*100-1, cellSize+2, cellSize+2);
        }

        if (checkIfAllCellsAreOccupied()) {
            if (canBeWin) {
                System.out.println("Congrats, you won!");
                System.exit(0);
            }
        }
    }

    public boolean checkIfAllCellsAreOccupied() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void emptyBoard() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != null && map[i][j].getFixedNumber() == null) {
                    map[i][j] = null;
                    repaint();
                }
            }
        }
    }

}








