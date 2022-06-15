package battleship;

import java.util.Objects;

public final class Cell {
    private final int x, y;
    // Cell state.
    public Condition condition;

    /**
     * Cell constructor.
     *
     * @param y y coordinate.
     * @param x x coordinate.
     */
    public Cell(int y, int x) {
        this.x = x;
        this.y = y;
    }

    /**
     * Overridden method.
     *
     * @param o object.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    /**
     * Overridden method.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Getting coordinate x.
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Getting coordinate y.
     *
     * @return
     */
    public int getY() {
        return y;
    }
}
