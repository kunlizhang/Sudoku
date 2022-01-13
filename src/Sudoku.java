import com.sun.source.tree.Tree;

import java.util.*;

/**
 * This class models the sudoku game, handling core functionality.
 */

public class Sudoku {
    private Tile[][] gb;
    private final Random rand;
    private boolean boardIsValid;
    private int[] selected;

    /**
     * Constructor for the sudoku game. Just resets the game.
     */
    public Sudoku() {
        this.rand = new Random();
        this.resetGame();
        this.selected = new int[2];
    }

    /**
     * Resets the game, creating a new Tile 2D array.
     */
    public void resetGame() {
        this.gb = new Tile[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gb[i][j] = new Tile();
            }
        }
        this.boardIsValid = true;
    }

    /**
     * Sets the value of a Tile in the grid.
     * @param r The row of the Tile.
     * @param c The column of the Tile.
     * @param v The value to be set.
     */
    public void setTileValue(int r, int c, int v) {
        this.gb[r][c].setValue(v);
        this.gb[r][c].setValid(checkValidMove(r, c, v));
        checkBoardIsValid();
    }

    /**
     * Gets the value of a Tile in the grid.
     * @param r The row of the Tile.
     * @param c The column of the Tile.
     * @return  The value of the tile at the coordinate.
     */
    public int getTileValue(int r, int c) {
        return this.gb[r][c].getValue();
    }

    /**
     * Generates a new board.
     * @param numTiles The number of tiles to generate.
     */
    public void generateNewBoard(int numTiles) {
        this.resetGame();
        solve();
        TreeSet<Integer> coordinatesToGenerate = new TreeSet<>();
        do {
            coordinatesToGenerate.add(rand.nextInt(81));
        } while (coordinatesToGenerate.size() < 81 - numTiles);

        for (int coord : coordinatesToGenerate) {
            int r = coord / 9;
            int c = coord % 9;
            setTileValue(r, c, 0);
        }
    }

    /**
     * Checks if a move is valid in the given gameboard.
     * @param r The row where the value is to be changed.
     * @param c The column.
     * @param v The new value.
     * @return  true if the move is valid. false otherwise.
     */
    public boolean checkValidMove(Tile[][] board, int r, int c, int v) {
        if (v < 1 || v > 9) {
            return false;
        }
        for (int i = 0; i < 9; i++) {
            if (board[i][c].getValue() == v && i != r) {
                return false;
            }
            if (board[r][i].getValue() == v && i != c) {
                return false;
            }
        }
        int blockRowStart = r - r % 3;
        int blockColStart = c - c % 3;
        for (int i = blockRowStart; i < blockRowStart + 3; i++) {
            for (int j = blockColStart; j < blockColStart + 3; j++) {
                if ((i != r || j != c) && board[i][j].getValue() == v) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a move is valid in the gameboard.
     * @param r The row.
     * @param c Column
     * @param v Value to check
     * @return
     */
    public boolean checkValidMove(int r, int c, int v) {
        return checkValidMove(this.gb, r, c, v);
    }

    /**
     * Returns if the board is currently valid.
     * @return  True if the current board is valid.
     */
    public boolean checkBoardIsValid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int tempV = getTileValue(i, j);
                if (tempV != 0 && !checkValidMove(i, j, tempV)) {
                    boardIsValid = false;
                    return false;
                }
            }
        }
        boardIsValid = true;
        return true;
        // @todo fix the way in which we choose a tile to be invalid when we set a tile value.
    }

    /**
     * Gets the validity of a given tile in the grid.
     * @param r The row.
     * @param c The column.
     * @return  boolean, true if it is valid.
     */
    public boolean checkTileValidity(int r, int c) {
        return this.gb[r][c].getValid();
    }

    /**
     * Selects a tile.
     * @param r The row of the selected tile.
     * @param c The column of the selected tile.
     */
    public void select(int r, int c) {
        this.selected[0] = r;
        this.selected[1] = c;
    }

    /**
     * Moves the currently selected tile up one.
     */
    public void up() {
        this.selected[0] = Math.max(this.selected[0] - 1, 0);
    }

    /**
     * Moves the currently selected tile down one.
     */
    public void down() {
        this.selected[0] = Math.min(this.selected[0] + 1, 8);
    }

    /**
     * Moves the currently selected tile left one.
     */
    public void left() {
        this.selected[1] = Math.max(this.selected[1] - 1, 0);
    }

    /**
     * Moves the currently selected tile right one.
     */
    public void right() {
        this.selected[1] = Math.min(this.selected[1] + 1, 8);
    }

    /**
     * Gets the array coordinates of the selected grid.
     * @return int array, where first position is the row, and second is the column.
     */
    public int[] getSelected() {
        return this.selected;
    }

    /**
     * Solves a given sudoku board using a backtracking algorithm.
     */
    public boolean solve(Tile[][] board) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].getValue() == 0) {
                    row = i;
                    col = j;

                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }
        if (isEmpty) {
            return true;
        }
        List<Integer> guessOrder = Arrays.asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        Collections.shuffle(guessOrder);
        for (int num : guessOrder) {
            if (checkValidMove(board, row, col, num)) {
                board[row][col].setValue(num);
                if (solve(board)) {
                    return true;
                } else {
                    board[row][col].setValue(0);
                }
            }
        }
        return false;
    }

    /**
     * Solves the gameboard of the game state.
     * @return True if the board can be solved, false otherwise.
     */
    public boolean solve() {
        if (!this.boardIsValid) {
            return false;
        }
        return solve(this.gb);
    }

    @Override
    public String toString() {
    String res = "";
    for (int i = 0; i < 9; i++) {
        if (i % 3 == 0) {
            res += "-------------------------------\n";
        }
        for (int j = 0; j < 9; j++) {
            if (j % 3 == 0) {
                res += "|" + " " + getTileValue(i, j) + " ";
            } else {
                res += " " + getTileValue(i, j) + " ";
            }
        }
        res += "|\n";
    }
    res += "-------------------------------";
    return res;
    }
}
