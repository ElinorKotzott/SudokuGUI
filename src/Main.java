import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SudokuMap [][] map = new SudokuMap [9][9];


        JFrame jframe = new JFrame("EllysSudoku");
        Board board = new Board(map, 999, 999);


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(board);
        jframe.pack();
        jframe.setVisible(true);

System.out.println("Welcome to Sudoku! Press a cell with left mouse button to enter a number. Zero will eliminate any number from the current cell. You can reset your sudoku by pressing backspace." +
        " Select a different sudoku by pressing space. You can keep selecting cells with left mouse click or use w, a, s, d or up, down, left and right key to maneuver across the board");


    }
}