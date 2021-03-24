/**
 * The Board 
 * This class represents the board for battleship. 
 * @author Andrew Blodgett
 * @since 2021-3-24
 * @version 1.0
 */

import java.util.Random;

public class Board {
    private char[][] board = generateBoard();

    /**
     * Returns the board.
     * @return 
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Returns a version of the board where you can't see the ships.
     * @return
     */
    public char[][] getHiddenBoard() {
        char[][] hiddenBoard = new char[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if (board[i][j] == 'o' || board[i][j] == 'x') {
                    hiddenBoard[i][j] = board[i][j];
                } else {
                    hiddenBoard[i][j] = '.';
                }
            }
        }
        return hiddenBoard;
    }

    /**
     * Checks to see if a square on this board has already been shot at.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if that space has been shot at
     */
    public boolean checkIfAlreadyShot(int x, int y) {
        if (board[y][x] == 'o' || board[y][x] == 'x') return true;
        else return false;
    }

    /**
     * Mutator method that 'shoots' at a square on the board, turning it to a hit if it was a ship, or a miss if it was water.
     * @param x the x coordinate
     * @param yLetter the letter for the y coordinate
     */
    public void shoot(int x, char yLetter) {
        int y = "ABCDEFGHIJ".indexOf(yLetter);

        if ("12345".indexOf(board[y][x]) != -1) {
            board[y][x] = 'x';
        } else if (board[y][x] == '.'){
            board[y][x] = 'o';
        }
    }

    /**
     * Checks if this board has no ships unsunk.
     * @return whether or not you have won
     */
    public boolean checkWin() {
        boolean win = true;
        loop:
        for (char[] row : board) {
            for (char space : row) {
                if (space == '1' || space == '2' || space == '3' || space == '4' || space == '5') {
                    win = false;
                    break loop;
                }
            }
        }
        return win;
    }

    /**
     * This method gets a formatted version of the board that doesn't show the locations of the ships.
     * @return a string representing the board
     */
    public String formatHiddenBoard() {
        String formatted = "  ";
        for (int i = 0; i < board.length; i++) {
            formatted = formatted +  (i + 1) + " ";
        }
        for (int i = 0; i < board.length; i++) {
            char letter = "ABCDEFGHIJ".charAt(i);
            formatted = formatted + "\n" + letter + " ";
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'o' || board[i][j] == 'x') {
                    formatted = formatted + board[i][j] + " ";
                } else {
                    formatted = formatted + ". ";
                }
            }
        }
        return formatted;
    }

    /**
     * This method gets a formatted version of the board that shows the locations of the ships.
     * @return a string representing the board
     */
    public String formatBoard() {
        String formatted = "  ";
        for (int i = 0; i < board.length; i++) {
            formatted = formatted +  (i + 1) + " ";
        }
        for (int i = 0; i < board.length; i++) {
            char letter = "ABCDEFGHIJ".charAt(i);
            formatted = formatted + "\n" + letter + " ";
            for (int j = 0; j < board[0].length; j++) {
                formatted = formatted + board[i][j] + " ";
            }
        }
        return formatted;
    }

    /**
     * Checks if a certain ship is sunk.
     * @param shipNum the number of a ship (5 for carries, 4 for battleship, 3 for destroyer, 2 for patrol boat, 1 for submarine)
     * @return true if there are noe spaces of that ship left on the board.
     */
    public boolean checkIfSunk(int shipNum) {
        boolean sunk = true;
        for (char[] row : board) {
            for (char space : row) {
                if (space == Character.forDigit(shipNum, 10)) {
                    sunk = false;
                }
            }
        }
        return sunk;
    }

    /**
     * This method randomly generates each board, making sure no ships overlap.
     * @return a randomly generated 2D char array
     */
    private char[][] generateBoard() {
        Random rand = new Random();
        char[][] newBoard = new char[10][10];

        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[0].length; j++) {
                newBoard[i][j] = '.';
            }
        }
        for (int j = 5; j > 0; j--) {
            int shipSize = j;
            if (j == 1) shipSize = 3;
            if (rand.nextBoolean()) {
                int y = rand.nextInt(11-shipSize);
                int x = rand.nextInt(10);

                boolean isClear = true;
                for (int i = y; i < y + shipSize; i++) {
                    if ("12345".indexOf(newBoard[i][x]) != -1) {
                      isClear = false;
                      break;
                    }
                }
                if (isClear) {
                    for (int i = y; i < y + shipSize; i++) {
                        newBoard[i][x] = Character.forDigit(j, 10);
                    }
                } else {
                    j++;
                }
            } else {
                int y = rand.nextInt(10);
                int x = rand.nextInt(11-shipSize);

                boolean isClear = true;
                for (int i = x; i < x + shipSize; i++) {
                    if ("12345".indexOf(newBoard[y][i]) != -1) {
                      isClear = false;
                      break;
                    }
                }
                if (isClear) {
                    for (int i = x; i < x + shipSize; i++) {
                        newBoard[y][i] = Character.forDigit(j, 10);
                    }
                } else {
                    j++;
                }
            }
        }
        return newBoard;
    }
}
