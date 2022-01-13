/**
 * This is the class for the tiles in the sudoku board.
 */
public class Tile {
    private int value;
    private boolean valid;

    /**
     * Constructor with no input defaults 0.
     */
    public Tile() {
        this(0);
    }

    /**
     * Constructor for the Tile class.
     * @param v The value which this Tile is to have.
     */
    public Tile(int v) {
        this.value = v;
        this.valid = true;
    }

    /**
     * Getter for the value.
     * @return The current value of the tile.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Setter for the value.
     * @param v The value to set the tile to.
     */
    public void setValue(int v) {
        this.value = v;
    }

    /**
     * Sets the validity of this tile.
     * @param b The boolean state to be set.
     */
    public void setValid(boolean b) {
        this.valid = b;
    }

    /**
     * Gets the validity.
     * @return  boolean of the validity of the current tile.
     */
    public boolean getValid() {
        return this.valid;
    }
}
