import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunSudoku implements Runnable {
    public void run() {
        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Sudoku");
        frame.setLocation(600, 600);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel score = new JLabel();
        status_panel.add(score);


        // Game board
        final Gameboard board = new Gameboard();
        frame.add(board, BorderLayout.CENTER);

        // Game control panel with buttons
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.resetBoard();
            }
        });
        control_panel.add(reset);

        // Generate button
        final JButton generate = new JButton("Generate");
        generate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.generateBoard();
            }
        });
        control_panel.add(generate);

        // Solve button
        final JButton solve = new JButton("Solve");
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.solveBoard();
            }
        });
        control_panel.add(solve);

        // Put the frame on the screen
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        board.requestFocusInWindow();
    }

}
