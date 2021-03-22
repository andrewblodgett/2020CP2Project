import java.util.Random;

public class Board {
    private char[][] board = generateBoard();
    boolean sunkCarrier = false;
    boolean sunkBattleship = false;
    boolean sunkDestroyer = false;
    boolean sunkSubmarine = false;
    boolean sunkPatrolBoat = false;


    public Board() {}


    public char[][] getBoard() {
        return board;
    }

    public boolean checkIfAlreadyShot(int x, int y) {
        if (board[y][x] == 'o' || board[y][x] == 'x') return true;
        else return false;
    }

    public void shoot(int x, char yLetter) {
        int y = "ABCDEFGHIJ".indexOf(yLetter);

        if ("12345".indexOf(board[y][x]) != -1) {
            board[y][x] = 'x';
        } else if (board[y][x] == 'W'){
            board[y][x] = 'o';
        }
    }

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

    public String formatBoard() {
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

    public String checkIfSunk() {
        sunkCarrier = true;
        sunkBattleship = true;
        sunkDestroyer = true;
        sunkSubmarine = true;
        sunkPatrolBoat = true;
        for (char[] row : board) {
            for (char space : row) {
                switch(space) {
                    case '1':
                        sunkSubmarine = false;
                        break;
                    case '2':
                        sunkPatrolBoat = false;
                        break;
                    case '3':
                        sunkDestroyer = false;
                        break;
                    case '4':
                        sunkBattleship = false;
                        break;
                    case '5':
                        sunkCarrier = false;
                        break;
                    default:
                        break;
                }
            }
        }
        if (sunkCarrier)  return "Carrier";
        if (sunkBattleship)  return "Battleship";
        if (sunkDestroyer)  return "Destroyer";
        if (sunkSubmarine)  return "Submarine";
        if (sunkPatrolBoat)  return "Patrol Boat";
        return "";
    }


    private char[][] generateBoard() {
        Random rand = new Random();
        char[][] newBoard = new char[10][10];

        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[0].length; j++) {
                newBoard[i][j] = 'W';
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
