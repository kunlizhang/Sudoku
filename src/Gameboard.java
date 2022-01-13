import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gameboard extends JPanel {
    private Sudoku g;
    public static final int BOARD_WIDTH = 540;
    public static final int BOARD_HEIGHT = 540;
    private Font f;

    public Gameboard() {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        this.g = new Sudoku();
        f = new Font("Arial", Font.PLAIN, 32);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                g.select(p.y / 60, p.x / 60);
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    g.left();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    g.up();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    g.right();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    g.down();
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    g.setTileValue(g.getSelected()[0], g.getSelected()[1], 0);
                } else if (48 < e.getKeyCode() && 58 > e.getKeyCode()) {
                    int inValue = Integer.parseInt(String.valueOf(e.getKeyChar()));
                    g.setTileValue(g.getSelected()[0], g.getSelected()[1], inValue);
                }
                repaint();
            }
        });
    }

    /**
     * Resets the gameboard and the game.
     */
    public void resetBoard() {
        g.resetGame();
        repaint();
        requestFocusInWindow();
    }

    /**
     * Generates a new gameboard.
     */
    public void generateBoard() {
        this.g.generateNewBoard(32);
        repaint();
        requestFocusInWindow();
    }

    /**
     * Solves the board.
     */
    public void solveBoard() {
        if (!g.solve()) {
            JOptionPane.showMessageDialog(null,
                    "This sudoku has no solution!",
                    "No solution", JOptionPane.PLAIN_MESSAGE
            );
        }
        repaint();
        requestFocusInWindow();
    }

    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(this.f);
        g.setColor(Color.BLACK);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int currTileValue = this.g.getTileValue(i, j);
                if (currTileValue != 0) {
                    if (!this.g.checkTileValidity(i, j)) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.drawString(String.valueOf(currTileValue), j * 60 + 20, i * 60 + 40);
                }
                g.setColor(Color.BLACK);
                g.drawRect(j * 60, i * 60, 60, 60);
            }
        }

        Graphics2D g2 = (Graphics2D) g;

        // Drawing thick lines for the grid
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.BLACK);
        g2.drawLine(180, 0, 180, 540);
        g2.drawLine(360, 0, 360, 540);
        g2.drawLine(0, 180, 540, 180);
        g2.drawLine(0, 360, 540, 360);

        // Draw selected rectangle
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.RED);
        g2.drawRect(this.g.getSelected()[1] * 60, this.g.getSelected()[0] * 60, 60, 60);
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
