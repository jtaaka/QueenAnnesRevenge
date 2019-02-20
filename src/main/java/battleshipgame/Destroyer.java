package battleshipgame;

/**
 * A class that defines ship's name, size and board character. Subclass of Ship class.
 *
 * @author Juho Taakala <juho.taakala@cs.tamk.fi>
 * @version 2017.0212
 * @since 1.0
 */
public class Destroyer extends Ship {

    public Destroyer() {
        super("Destroyer", 2, 'D');
    }
}