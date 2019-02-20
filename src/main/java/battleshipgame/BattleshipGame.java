package battleshipgame;

/**
 * The main class that starts the game and combines different classes.
 *
 * @author Juho Taakala <juho.taakala@cs.tamk.fi>
 * @version 2017.0212
 * @since 1.0
 */
public class BattleshipGame {

    /**
     * Starts the main program. Implements game logic and gameboards.
     * Checks if the game is over and prints results.
     *
     * @param args the command line arguments - not used
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Battleship! Start by placing your ships on board.");
        GameLogic game = new GameLogic();
        game.placePlayerShips();
        game.placeComputerShips();
        game.displayBoards();

        do {
            String playerMove = game.playerMove();
            String computerMove = game.computerMove();
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            game.displayBoards(playerMove, computerMove);

        } while (game.isGameOver() == 0);

        int gameOver = game.isGameOver();

        if (gameOver == 1) {
            System.out.println("You lost, better luck next time!");
        } else if(gameOver == 2) {
            System.out.println("You won, congratulations!");
        } else if(gameOver == 3) {
            System.out.println("Oh my god, a tie!");
        }
    }
}