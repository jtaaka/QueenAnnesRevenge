package battleshipgame;

public class Ship {

    /**
     * Name of a ship.
     */
    private String name;
    /**
     * Length of a ship.
     */
    private int length;

    /**
     * Row of a ship.
     */
    private int rowPosition;     // Positions are the top left point of the ship

    /**
     * Column of a ship.
     */
    private int columnPosition;

    /**
     * Horizontal ship or not.
     */
    private boolean isHorizontal;

    /**
     * Character of a ship on gameboard.
     */
    private char boardCharacter;

    /**
     * Parent constructor of different ship classes.
     *
     * @param name name of a ship from various ship classes
     * @param length length of a ship from various ship classes
     * @param boardChar board character of a ship from various ship classes
     */
    public Ship(String name, int length, char boardChar) {
        this.name = name;
        this.length = length;
        this.boardCharacter = boardChar;
    }


    /**
     * Checks the position of a ship and which direction it goes.
     *
     * @param row of a ship
     * @param column of a ship
     * @param direction of a ship (n = north, s = south, w = west, e  = east)
     */
    public void setShip(int row, int column, char direction) {

        if (direction == 'n') {
            rowPosition = row - (length - 1);
            columnPosition = column;
            isHorizontal = false;
        } else if (direction == 's') {
            rowPosition = row;
            columnPosition = column;
            isHorizontal = false;
        } else if (direction == 'w') {
            rowPosition = row;
            columnPosition = column - (length - 1);
            isHorizontal = true;
        } else if (direction == 'e') {
            rowPosition = row;
            columnPosition = column;
            isHorizontal = true;
        } else {
            rowPosition = -1;
            columnPosition = -1;
        }
    }

    /**
     * Checks if a ship is set as horizontal or not.
     *
     * @return isHorizontal true or false
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Gets the row position of a ship.
     *
     * @return rowPosition row
     */
    public int getTopLeftRow() {
        return rowPosition;
    }

    /**
     * Gets the column position of a ship.
     *
     * @return columnPosition column
     */
    public int getTopLeftColumn() {
        return columnPosition;
    }

    /**
     * Gets the length of a ship.
     *
     * @return length of the ship
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the name of a ship.
     *
     * @return name of the ship
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the character of the ship on gameboard.
     *
     * @return boardCharacter character on gameboard.
     */
    public char getBoardCharacter() {
        return boardCharacter;
    }
}
