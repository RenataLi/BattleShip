package battleship;

/**
 * Class Battleship type.
 */
public class Battleship extends Ship{
    public Battleship(){
        super(4);
    }

    /**
     * The string representation of the ship.
     * @return
     */
    @Override
    public String toString() {
        return "Battleship";
    }
}
