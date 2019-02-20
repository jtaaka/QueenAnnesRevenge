package battleshipgame;

import java.util.Scanner;

/**
 * A class that contains all the game logic and printing of the gameboards.
 *
 * @author Juho Taakala <juho.taakala@cs.tamk.fi>
 * @version 2017.0212
 * @since 1.0
 */
public class GameLogic {

    /**
     * Size of the board.
     */
    private final int SIZE = 10;

    /**
     * Number of ships.
     */
    private final int SHIPS = 5;

    /**
     * Character for empty space on board.
     */
    private final char EMPTY = '~';

    /**
     * Character for a shot that hit a ship.
     */
    private final char HIT = 'X';

    /**
     * Character for a shot that missed.
     */
    private final char MISS = 'O';

    /**
     * Player's board array.
     */
    private final char[][] playerBoard;

    /**
     * Computer's board array.
     */
    private final char[][] computerBoard;

    /**
     * Player's array of ships.
     */
    private final Ship[] playerShip;

    /**
     * Computer's array of ships.
     */
    private final Ship[] computerShip;

    /**
     * Initializes gameboards and ships.
     */
    public GameLogic() {

        playerBoard = new char[SIZE][SIZE];

        for (int i = 0; i < playerBoard.length; i++) {
            for (int j = 0; j < playerBoard[i].length; j++) {
                playerBoard[i][j] = EMPTY;
            }
        }

        computerBoard = new char[SIZE][SIZE];

        for (int i = 0; i < computerBoard.length; i++) {
            for (int j = 0; j < computerBoard[i].length; j++) {
                computerBoard[i][j] = EMPTY;
            }
        }

        // Allocate ship arrays for Ship class objects
        playerShip = new Ship[SHIPS];
        computerShip = new Ship[SHIPS];

        // Create ship objects in the arrays
        playerShip[0] = new Carrier();      // Size 5
        playerShip[1] = new Battleship();   // Size 4
        playerShip[2] = new Cruiser();    // Size 3
        playerShip[3] = new Submarine();    // Size 3
        playerShip[4] = new Destroyer();   // Size 2

        computerShip[0] = new Carrier();
        computerShip[1] = new Battleship();
        computerShip[2] = new Cruiser();
        computerShip[3] = new Submarine();
        computerShip[4] = new Destroyer();
    }

    /**
     * Initializes empty boards.
     */
    public void displayBoards() {
        displayBoards(null, null, playerBoard, computerBoard);
    }

    /**
     * Displays player's and computer's moves on gameboards.
     *
     * @param playerMove player's move on board
     * @param computerMove computer's move on board
     */
    public void displayBoards(String playerMove, String computerMove) {
        displayBoards(playerMove, computerMove, playerBoard, computerBoard);
    }

    /**
     * Prints player's and computer's gameboards
     *
     * @param playerMove player's move on board
     * @param computerMove computer's move on board
     * @param leftBoard is player's board
     * @param rightBoard is computer's board
     */
    private void displayBoards(String playerMove, String computerMove, char[][] leftBoard, char[][] rightBoard) {
        // Prints first row for both boards.
        System.out.println();
        System.out.println(" Your board \t\t\t\t    Computer's board" + "\n");
        System.out.printf("%2s ", "");

        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2s ", i + 1);   // Row is from 1 to 10.
        }

        System.out.printf("%10s", "");
        System.out.printf("%2s ", "");

        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2s ", i + 1);
        }

        System.out.println();

        // Prints rest of the rows
        for (int i = 0; i < SIZE; i++) {
            char row = (char) (65 + i);         // (int) 'A' = 65
            System.out.printf("%2s ", row);
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%2s ", leftBoard[i][j]);
            }

            System.out.printf("%10s","");       // Print rightside board with
            System.out.printf("%2s ", row);     // spacing to the leftside.

            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%2s ", rightBoard[i][j]);
            }

            System.out.println();
        }

        if (playerMove != null) {
            System.out.println("Your move:");
            System.out.println(playerMove);
        }

        if (computerMove != null) {
            System.out.println("Computer's move:");
            System.out.println(computerMove);
        }
    }

    /**
     * Places player's subs and checks if coordinates are valid.
     */
    public void placePlayerShips() {
        boolean correctPlacement;

        // loop to place all player subs
        for (int i = 0; i < SHIPS; i++) {
            do {
                displayBoards();
                correctPlacement = placeShip(playerShip[i], i);

                if(!correctPlacement) {
                    System.out.println("\nPlease, enter valid coordinates.");
                }
            } while(!correctPlacement);

            addShipToBoard(playerBoard, playerShip[i]);
        }
    }

    /**
     * Places computer's ships in random places horizontally and/or vertically.
     */
    public void placeComputerShips() {
        char[][] board = new char[SIZE][SIZE];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY;
            }
        }

        boolean correctPlacement;

        // Loop to place computer's ships in random places and direction.
        for (int i = 0; i < SHIPS; i++) {
            do {
                int randomColumn = (int) (Math.random() * SIZE);
                int randomRow = (int) (Math.random() * SIZE);

                int randomDirectionNumber = (int) (Math.random() * 4);
                String randomDirection = "";
                switch(randomDirectionNumber) {
                    case 0:
                        randomDirection = "north";
                        break;
                    case 1:
                        randomDirection = "south";
                        break;
                    case 2:
                        randomDirection = "west";
                        break;
                    case 3:
                        randomDirection = "east";
                        break;
                    default:
                        randomDirection = "";
                        break;
                }

                computerShip[i].setShip(randomRow, randomColumn, getFirstChar(randomDirection));
                correctPlacement = isValidPlacement(board, computerShip[i]);
            } while (!correctPlacement);

            addShipToBoard(board, computerShip[i]);
        }
    }

    /**
     * Requests users input for coordinates to shoot at or to place a ship at.
     *
     * @return coordinates to shoot at
     */
    private String requestString() {
        Scanner input = new Scanner(System.in);
        String coordinates = input.nextLine();

        return coordinates;
    }

    /**
     * Gets users input for coordinates and direction to place a ship at.
     *
     * @param ship different ship types from Ship class
     * @param shipNumber number of the ship to place on board (5 in total)
     * @return if it's a valid placement for the ship
     */
    private boolean placeShip(Ship ship, int shipNumber) {

        System.out.println("\nAt which coordinate do you want to place ship " + (shipNumber + 1) + ", your "
                + ship.getName().toLowerCase() + " (length " + ship.getLength() + ") ?");
        String coordinate = requestString().toLowerCase();

        System.out.println("Which direction do you want the ship to go? (north, south, west or east)");
        String direction = requestString().toLowerCase();
        System.out.println("\n\n");

        ship.setShip(rowCharToInt(getFirstChar(coordinate)), getFirstInt(coordinate) -1, getFirstChar(direction));

        return isValidPlacement(playerBoard, ship);
    }

    /**
     * Adds ships (different characters) to gameboards either horizontally
     * or vertically.
     *
     * @param board is a gameboard array
     * @param ship is a ship on board
     */
    private void addShipToBoard(char[][] board, Ship ship) {
        int row = ship.getTopLeftRow();
        int column = ship.getTopLeftColumn();

        for (int i = 0; i < ship.getLength(); i++) {
            board[row][column] = ship.getBoardCharacter();

            if (ship.isHorizontal()) {
                column++;
            } else {
                row++;
            }
        }
    }

    /**
     * Asks a player for coordinates to shoot at and checks if the shot hit or not.
     *
     * @return whether a ship was hit, sunk (ship's name) or shot was missed
     */
    public String playerMove() {
        int shootRow;
        int shootColumn;
        String coordinate;

        do {
            System.out.println("Which coordinates would you like to shoot at?");
            coordinate = requestString().toLowerCase();

            shootRow = rowCharToInt(getFirstChar(coordinate));
            shootColumn = getFirstInt(coordinate) -1;

        } while ((shootRow < 0 || shootRow > SIZE -1) || (shootColumn < 0 || shootColumn > SIZE -1));


        // Loop to check computer ship locations
        for (int k = 0; k < SHIPS; k++) {
            int row = computerShip[k].getTopLeftRow();
            int column = computerShip[k].getTopLeftColumn();

            for (int i = 0; i < computerShip[k].getLength(); i++) {
                if (row == shootRow && column == shootColumn) {
                    computerBoard[shootRow][shootColumn] = HIT;
                    if (isShipSunk(computerBoard, computerShip[k])) {
                        return computerShip[k].getName()
                                + " sunk at: " + getFirstChar(coordinate.toUpperCase()) + getFirstInt(coordinate);
                    }

                    return "Hit at: " + getFirstChar(coordinate.toUpperCase()) + getFirstInt(coordinate);
                }

                if (computerShip[k].isHorizontal()) {
                    column++;
                } else {
                    row++;
                }
            }
        }

        computerBoard[shootRow][shootColumn] = MISS;
        return "Miss at: " + getFirstChar(coordinate.toUpperCase()) + getFirstInt(coordinate);
    }

    /**
     * Shoots randomly at player's gameboard and checks if the shot hit or not.
     *
     * @return whether a ship was hit, sunk (ship's name) or shot was missed
     */
    public String computerMove() {

        int shootRow = (int) (Math.random() * SIZE);
        int shootColumn = (int) (Math.random() * SIZE);

        // Loop to make sure that the computer doesn't shoot same spots twice.
        do {
            shootRow = (int) (Math.random() * SIZE);
            shootColumn = (int) (Math.random() * SIZE);

        } while (playerBoard[shootRow][shootColumn] != EMPTY &&
                playerBoard[shootRow][shootColumn] != 'c' &&    // Ship characters on board
                playerBoard[shootRow][shootColumn] != 'C' &&
                playerBoard[shootRow][shootColumn] != 'D' &&
                playerBoard[shootRow][shootColumn] != 'B' &&
                playerBoard[shootRow][shootColumn] != 'S' ||
                playerBoard[shootRow][shootColumn] == MISS &&
                        playerBoard[shootRow][shootColumn] == HIT);

        // (int) 'A' = 65
        String coordinates = "" + ((char) (shootRow + 65)) + (shootColumn +1);

        for (int s = 0; s < SHIPS; s++) {
            int row = playerShip[s].getTopLeftRow();
            int column = playerShip[s].getTopLeftColumn();

            // Loop to check player's ship locations
            for (int i = 0; i < playerShip[s].getLength(); i++) {
                if (row == shootRow && column == shootColumn) {
                    playerBoard[shootRow][shootColumn] = HIT;
                    if (isShipSunk(playerBoard, playerShip[s])) {

                        return playerShip[s].getName()+ " sunk at: " + coordinates;
                    }

                    return "Hit at: " + coordinates;
                }

                if (playerShip[s].isHorizontal()) {
                    column++;
                } else {
                    row++;
                }
            }
        }

        playerBoard[shootRow][shootColumn] = MISS;
        return "Miss at: " + coordinates;
    }

    /**
     * Checks if a ship has been destroyed.
     *
     * @param board array of the gameboard
     * @param ship ship on gameboard
     * @return true if a ship is sunk, false if not
     */
    public boolean isShipSunk(char[][] board, Ship ship) {
        int row = ship.getTopLeftRow();
        int column = ship.getTopLeftColumn();

        for (int i = 0; i < ship.getLength(); i++) {
            if (board[row][column] != HIT) {
                return false;
            }

            if (ship.isHorizontal()) {
                column++;
            } else {
                row++;
            }
        }

        return true;
    }

    /**
     * Checks if the ship can be placed on certain coordinates.
     *
     * @param board gameboard array
     * @param ship ship on gameboard
     * @return true or false depending on if it's a valid placement or not
     */
    public boolean isValidPlacement(char[][] board, Ship ship) {
        int row = ship.getTopLeftRow();
        int column = ship.getTopLeftColumn();

        // Checks if coordinates are on the board
        if (column < 0 || column >= SIZE || row < 0 || row >= SIZE) {
            return false;
        }

        // Checks if the piece is actually on the board
        if (column < 0 || (column + ship.getLength() -1 > SIZE -1 && ship.isHorizontal())) {
            return false;
        }

        if(row < 0 || (row + ship.getLength() -1 > SIZE -1 && !ship.isHorizontal())) {
            return false;
        }

        // Checks if another ship is occupying the space
        for (int i = 0; i < ship.getLength(); i++) {
            if(board[row][column] != EMPTY) {
                return false;
            }

            if (ship.isHorizontal()) {
                column++;
            } else {
                row++;
            }
        }

        return true;
    }

    /**
     * Gets first character from ship direction input and row character coordinate.
     *
     * @param direction string of ship direction input
     * @return character coordinate
     */
    private char getFirstChar(String direction) {
        int i = 0;
        char character;

        if (direction.length() == 0) {
            character = (char) 0;
        } else {

            while (i < direction.length() && !Character.isLetter(direction.charAt(i))) {
                i++;
            } if(i < direction.length()) {
                character = direction.charAt(i);
            } else {
                character = (char) 0;
            }
        }

        return character;
    }

    /**
     * Converts row character to integer.
     *
     * @param character of a row coordinate
     * @return character converted to integer
     */
    private int rowCharToInt(char character) {
        return ((int)character) - 97;       // (int) 'a' = 97
    }

    /**
     * Gets coordinate number as a string and returns it as an integer.
     *
     * @param num number as a string input
     * @return coordinate number
     */
    private int getFirstInt(String num) {
        String number = "";
        int i = 0;
        if (num.length() == 0) {
            return 0;
        }

        while (i < num.length() && !Character.isDigit(num.charAt(i))) {
            i++;
        }

        while (i < num.length() && Character.isDigit(num.charAt(i))) {
            number += num.charAt(i);
            i++;
        }

        if (number.equals("")) {
            return 0;
        }

        return Integer.parseInt(number);
    }

    /**
     * Checks if a player or the computer has won.
     *
     * @return result 1 = computer won, 2 = player won
     */
    public int isGameOver() {
        int result = 0;
        int computerShipCoordinates = 0;
        int playerShipCoordinates = 0;

        // Loops through ships
        for (int s = 0; s < SHIPS; s++) {
            computerShipCoordinates += computerShip[s].getLength();
            playerShipCoordinates += playerShip[s].getLength();
        }

        int playerBoardHit = 0;

        // Loops through player's board and checks how many ships are hit.
        for (int i = 0; i < playerBoard.length; i++) {
            for (int j = 0; j < playerBoard[i].length; j++) {
                if (playerBoard[i][j] == HIT) {
                    playerBoardHit++;
                }
            }
        }

        if (playerBoardHit == playerShipCoordinates) {
            result += 1;      // Computer won
        }

        int computerBoardHit = 0;

        // Loops through computer's board and checks how many ships are hit.
        for (int i = 0; i < computerBoard.length; i++) {
            for (int j = 0; j < computerBoard[i].length; j++) {
                if (computerBoard[i][j] == HIT) {
                    computerBoardHit++;
                }
            }
        }
        if (computerBoardHit == computerShipCoordinates) {
            result += 2;      // Player won
        }

        return result;
    }
}