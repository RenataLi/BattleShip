package battleship;

/**
 * Class of ship type Cruiser.
 */
public class Cruiser extends Ship{
    public Cruiser(){
        super(3);
    }

    /**
     * String representing of Cruiser.
     * @return
     */
    @Override
    public String toString() {
        return "Cruiser";
    }
}
