package battleship;

import java.util.Arrays;
import java.util.HashSet;

/**
 * General class of the ship.
 */
public abstract class Ship {
    // Array of the states of the ship cells.
    private boolean[] fired_cells;
    private int x, y;
    // Field for counting the number of shots.
    private int fire_count = 0;
    // Horizontal or vertical orientation of ship.
    private Orientation orientation;

    /**
     * Constructor of ship.
     *
     * @param numOfCells
     */
    protected Ship(int numOfCells) {
        fired_cells = new boolean[numOfCells];
        orientation = Orientation.horizontal;
    }

    /**
     * Method for blocking cells if the ship settled down successfully.
     *
     * @param y           y coordinate.
     * @param x           x coordinate.
     * @param filledCells filled cells of ocean.
     * @param n           number of rows.
     * @param m           number of columns.
     */
    public void blockCells(int y, int x, Cell[][] filledCells, int n, int m) {
        // Create an array of step to coordinate (cells near the ship).
        int step[] = new int[]{0, 1, -1};
        for (int i = 0; i < step.length; i++) {
            for (int j = 0; j < step.length; j++) {
                if (x + step[i] >= 0 && x + step[i] < m && y + step[j] >= 0 && y + step[j] < n) {
                    filledCells[y + step[j]][x + step[i]].condition = Condition.block;
                }
            }
        }
    }

    /**
     * Trying to place the ship.
     *
     * @param filledCells filled cells of ocean.
     * @param n           number of rows.
     * @param m           number of columns.
     * @return successful or not attempted location.
     */
    public boolean tryToPlace(Cell[][] filledCells, int n, int m) {
        int step = 0;
        // Trying to fill the ocean with a horizontal ship.
        while (x + step < m && step < numOfCells()
                && filledCells[y][x + step].condition == Condition.available) {
            step += 1;
        }
        if (step == numOfCells()) {
            for (int i = 0; i < step; i++) {
                filledCells[y][x + i] = new Cell(y, x + i);
                // We block the cells near the ship by fulfilling the contiguity condition.
                blockCells(y, x, filledCells, n, m);
            }
            orientation = Orientation.horizontal;
            return true;
        }
        // Trying to fill the ocean with a vertical ship.
        step = 0;
        while (y + step < n && step < numOfCells()
                && filledCells[y + step][x].condition == Condition.available) {
            step += 1;
        }
        if (step == numOfCells()) {
            for (int i = 0; i < step; i++) {
                filledCells[y + i][x] = new Cell(y + i, x);
                // We block the cells near the ship by fulfilling the contiguity condition.
                blockCells(y, x, filledCells, n, m);
            }
            orientation = Orientation.vertical;
            return true;
        }
        return false;
    }

    /**
     * Obtaining the type of ship depending on the length.
     *
     * @param cellCount length of ship.
     * @return new ship.
     */
    public static Ship getShip(int cellCount) {
        switch (cellCount) {
            case 1:
                return new Submarine();
            case 2:
                return new Destroyer();
            case 3:
                return new Cruiser();
            case 4:
                return new Battleship();
            case 5:
                return new Carrier();
            default:
                return null;
        }
    }

    /**
     * Restoration of the ship if the recovery mode is enabled.
     */
    public void recoverShip() {
        for (int i = 0; i < fired_cells.length; i++) {
            fired_cells[i] = false;
        }
        fire_count = 0;
    }

    /**
     * Hit the ship whith torpedo.
     *
     * @param y y coordinate.
     * @param x x coordinate.
     * @return Successful or not hitting the ship.
     */
    public boolean hitWithTorpedo(int y, int x) {
        int num_cell = -1;
        if (this.orientation == Orientation.horizontal) {
            if (this.y == y && x >= this.x && x < this.x + fired_cells.length) {
                num_cell = x - this.x;
                if (!fired_cells[num_cell]) {
                    fire_count = fired_cells.length;
                    return true;
                }
            }
        } else {
            if (this.x == x && y >= this.y && y < this.y + fired_cells.length) {
                num_cell = y - this.y;
                if (!fired_cells[num_cell]) {
                    fire_count = fired_cells.length;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Hit the ship.
     *
     * @param y y coordinate.
     * @param x x coordinate.
     * @return Successful or not hitting the ship.
     */
    public boolean hit(int y, int x) {
        int num_cell = -1;
        if (this.orientation == Orientation.horizontal) {
            if (this.y == y && x >= this.x && x < this.x + fired_cells.length) {
                num_cell = x - this.x;
            }
        } else {
            if (this.x == x && y >= this.y && y < this.y + fired_cells.length) {
                num_cell = y - this.y;
            }
        }
        if (num_cell != -1) {
            if (!fired_cells[num_cell]) {
                fire_count++;
                fired_cells[num_cell] = true;
            }
            return true;
        }
        return false;
    }

    /**
     * Getting cells of ocean.
     *
     * @return
     */
    public Cell[] getCells() {
        Cell[] cells = new Cell[fired_cells.length];
        for (int i = 0; i < cells.length; i++) {
            if (orientation == Orientation.horizontal) {
                cells[i] = new Cell(y, x + i);
            } else
                cells[i] = new Cell(y + i, x);

        }
        return cells;
    }

    /**
     * Determination of the sinking of the ship.
     *
     * @return
     */
    public boolean isSunk() {
        return fire_count == fired_cells.length;
    }

    /**
     * Number of cells of ship.
     *
     * @return
     */
    public int numOfCells() {
        return fired_cells.length;
    }

    /**
     * Getting x coordinate.
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Setting x coordinate.
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getting y coordinate.
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Setting y coordinate.
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getting orientation of ship.
     *
     * @return
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Setting orientation of ship.
     *
     * @param orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
