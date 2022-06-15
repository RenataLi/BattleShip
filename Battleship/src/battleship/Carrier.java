package battleship;

/**
 * Carrier ship type.
 */
public class Carrier extends Ship {
    public Carrier() {
        super(5);
    }

    /**
     * The string representation of the ship.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Carrier";
    }
}
