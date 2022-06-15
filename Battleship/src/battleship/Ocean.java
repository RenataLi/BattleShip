package battleship;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Ocean {
    // Array of ships in the ocean.
    private final Ship[] ships;
    // Count of rows and columns.
    private final int n, m;
    // X and Y coordinate for las hitted ship.
    private int last_x, last_y;
    // String representation of ocean.
    private String[][] fieldArray;
    private boolean isKilled = false;
    private int count_of_shots = 0;
    private int count_of_torpedoes = 0;
    private boolean recovery_mode = false;
    // Last hitted ship.
    private Ship last_ship;
    // Cell representation of ocean.
    private Cell[][] filledCells;

    /**
     * Constructor of ocean.
     *
     * @param n         number of rows of ocean.
     * @param m         number of columns of ocean.
     * @param shipCount number of ships of each type.
     */
    public Ocean(int n, int m, int[] shipCount) {
        int totalShips = 0;
        filledCells = new Cell[n][m];
        for (int i = 0; i < n; i++) {
            filledCells[i] = new Cell[m];
            for (int j = 0; j < m; j++) {
                filledCells[i][j] = new Cell(i, j);
                filledCells[i][j].condition = Condition.available;
            }
        }
        Random rnd = new Random();
        int numOfShip = 0;
        for (int i : shipCount) {
            totalShips += i;
        }
        if (totalShips <= 0)
            throw new IllegalArgumentException("You have not entered any ships in your field, please try again.");
        this.ships = new Ship[totalShips];
        this.n = n;
        this.m = m;
        this.fieldArray = new String[n][m];
        for (int i = shipCount.length - 1; i >= 0; i--) {
            for (int j = 0; j < shipCount[i]; j++) {
                ships[numOfShip++] = Ship.getShip(shipCount.length - 1 - i + 1);
            }
        }
        for (Ship ship : ships) {
            int countOfIterations = totalShips + 20000;
            do {
                if (countOfIterations-- == 0)
                    throw new IllegalArgumentException("The computer could not generate this field, please try again.");
                ship.setX((int) (Math.random() * m) % m);
                int k = 0;
                ship.setY((int) (Math.random() * n) % n);

            } while (!ship.tryToPlace(filledCells, n, m));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                fieldArray[i][j] = "not-fired ";
            }
        }
    }

    /**
     * Method for hit with torpedoes.
     *
     * @param y y coordinate.
     * @param x x coordinate.
     * @return state of cell.
     */
    public String tryToHitWithTorpedo(int y, int x) {
        count_of_shots++;
        for (Ship ship : ships) {
            if (ship.hitWithTorpedo(y - 1, x - 1)) {
                if (last_ship != null && !last_ship.isSunk() && last_ship != ship) {
                    last_ship.recoverShip();
                    last_ship = null;
                }
                return sunkShip(ship);
            }
        }
        if (last_ship != null && !last_ship.isSunk()) {
            last_ship.recoverShip();
            last_ship = null;
        }
        return missShip(x, y);
    }

    /**
     * Method for sinking all cells of ship.
     *
     * @param ship ship.
     * @return state of cell.
     */
    private String sunkShip(Ship ship) {
        for (Cell cell : ship.getCells()) {
            fieldArray[cell.getY()][cell.getX()] = "    sunk   ";
        }
        for (Ship ship1 : ships) {
            if (ship1.isSunk()) {
                isKilled = true;
            } else {
                isKilled = false;
                break;
            }
        }
        return "You just have sunk a " + ship + "\n";
    }

    /**
     * Method for hit the ship.
     *
     * @param y y coordinate.
     * @param x x coordinate.
     * @return state of cell.
     */
    public String tryToHit(int y, int x) {
        count_of_shots++;
        for (Ship ship : ships) {
            if (ship.hit(y - 1, x - 1)) {
                fieldArray[y - 1][x - 1] = "fired-hit ";
                if (ship.isSunk()) {
                    return sunkShip(ship);
                } else {
                    if (recovery_mode) {
                        last_ship = ship;
                        last_x = x - 1;
                        last_y = y - 1;
                    }
                    return "hit\n";
                }

            }
        }
        return missShip(y, x);
    }

    /**
     * Method for missing ship.
     *
     * @param y y coordinate.
     * @param x x coordinate.
     * @return state of cell.
     */
    private String missShip(int y, int x) {
        fieldArray[y - 1][x - 1] = "fired-miss ";
        if (last_ship != null && !last_ship.isSunk()) {
            last_ship.recoverShip();
            fieldArray[last_y][last_x] = "not-fired ";
            last_ship = null;
        }
        return "miss\n";
    }

    /**
     * Setting the recovery mode.
     *
     * @param recovery_mode
     */
    public void setRecovery_mode(boolean recovery_mode) {
        this.recovery_mode = recovery_mode;
    }

    /**
     * Getting width.
     *
     * @return
     */
    public int getM() {
        return m;
    }

    /**
     * Getting hight.
     *
     * @return
     */
    public int getN() {
        return n;
    }

    /**
     * Getting a variable showing the state of the ship.
     *
     * @return
     */
    public boolean getIsKilled() {
        return isKilled;
    }

    /**
     * Getting total count of shots.
     *
     * @return
     */
    public int getCount_of_shots() {
        return count_of_shots;
    }

    /**
     * Getting count of torpedoes
     *
     * @return
     */
    public int getCount_of_torpedoes() {
        return count_of_torpedoes;
    }

    /**
     * Setting count of torpedoes.
     *
     * @param count_of_torpedoes
     */
    public void setCount_of_torpedoes(int count_of_torpedoes) {
        this.count_of_torpedoes = count_of_torpedoes;
    }

    /**
     * String representation of ocean.
     *
     * @return
     */
    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res += fieldArray[i][j] + " ";
            }
            res += "\n";
        }
        return res;
    }
}
