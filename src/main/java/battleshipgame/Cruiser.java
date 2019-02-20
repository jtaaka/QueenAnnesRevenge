package battleshipgame;

/**
 * A class that defines ship's name, size and board character. Subclass of Ship class.
 *
 * @author Juho Taakala <juho.taakala@cs.tamk.fi>
 * @version 2017.0212
 * @since 1.0
 */
public class Cruiser extends Ship {

    public Cruiser() {
        super("Cruiser", 3, 'c');
    }
}