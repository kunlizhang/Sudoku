import javax.swing.*;

/**
 * This class runs the game. Run this file to run Sudoku.
 */

public class Game {
    public static void main(String[] args) {
        Runnable game = new RunSudoku();
        SwingUtilities.invokeLater(game);
    }
}
