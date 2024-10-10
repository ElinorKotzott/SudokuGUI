public class Game {
    private SudokuMap[][] map;
    private int width;
    private int height;
    int cellSize;


    public Game(SudokuMap[][] map, int width, int height, int cellSize) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
    }


    public Coord handleMouseClick(int x, int y, int cellSize) {
        int rowIndex = y/cellSize;
        int columnIndex = x/cellSize;
        return new Coord(rowIndex, columnIndex);
    }
}


