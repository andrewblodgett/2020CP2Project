/**
 * Battleship
 * The classic paper-and-pen battleship, now playable in the console. Compete against the computer to get the best score.
 * @author Andrew Blodgett
 * @since 2021-3-24
 * @version 1.0
 */

import java.util.Scanner;
import java.util.Random;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class Battleship {
    /**
     * The main method; This is where all the game logic is contained.
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException {
        // Declaring variables
        boolean play = true;
        Random rand = new Random();
        Board yourBoard = new Board();
        Board oppBoard = new Board();
        String validLetters = "ABCDEFGHIJ";
        Scanner in = new Scanner(System.in);
        // Scanner in = new Scanner(new File("test.txt"));
        int numOfMoves = 0;

        // Welcome statement and instructions
        System.out.print("\nWelcome to Battleship!\n\nWould you like to read the instructions? (y/n) ");
        if (in.next().charAt(0) == 'y') {
            clearCons();
            System.out.println("This is Battleship. \nYou are trying to sink all of your opponent's ships before he sinks yours\nWhen it's your turn, you will enter coordinates of the space you want to shoot at. \nIf you hit a ship, you will see an 'x' show up on your opponent's board where you shot.\nIf you missed, that will be shown as an 'o'.\nIt will show any ships that you have sunk at the bottom of the board.\nOn your board you can see your ships that your opponent is trying to sink, shown with different numbers.\n\n");
        }
        System.out.print("Type 'start' or 's' to start: ");
        in.next();

        // Game loop
        do {     
            while (play) {
                // Shows the user their board and their opponent's board.
                clearCons();

                System.out.println("\nThis is your board:");
                System.out.println(yourBoard.formatBoard());

                // This part prints to the user any ships that their opponent has sunk.
                boolean sunkAny = false;
                if (yourBoard.checkIfSunk(5)) {
                    System.out.println("I sank your Carrier. 5 5 5 5 5");
                    sunkAny = true;
                } 
                if (yourBoard.checkIfSunk(4)) {
                    System.out.println("I sank your Battleship. 4 4 4 4");
                    sunkAny = true;
                } 
                if (yourBoard.checkIfSunk(3)) {
                    System.out.println("I sank your Destroyer. 3 3 3");
                    sunkAny = true;
                } 
                if (yourBoard.checkIfSunk(1)) {
                    System.out.println("I sank your Submarine. 1 1 1");
                    sunkAny = true;
                } 
                if (yourBoard.checkIfSunk(2)) {
                    System.out.println("I sank your Patrol Boat. 2 2");
                    sunkAny = true;
                } 
                if (!sunkAny) {
                    System.out.println("No ships are sunk.");
                }

                // Prints the opponent's board for the user
                System.out.println("\nThis is your opponent's board:");
                System.out.println(oppBoard.formatHiddenBoard());

                // This part prints to the user any ships that they have sunk.
                sunkAny = false;
                if (oppBoard.checkIfSunk(5)) {
                    System.out.println("You sank my Carrier. 5 5 5 5 5");
                    sunkAny = true;
                } 
                if (oppBoard.checkIfSunk(4)) {
                    System.out.println("You sank my Battleship. 4 4 4 4");
                    sunkAny = true;
                } 
                if (oppBoard.checkIfSunk(3)) {
                    System.out.println("You sank my Destroyer. 3 3 3");
                    sunkAny = true;
                } 
                if (oppBoard.checkIfSunk(1)) {
                    System.out.println("You sank my Submarine. 1 1 1");
                    sunkAny = true;
                } 
                if (oppBoard.checkIfSunk(2)) {
                    System.out.println("You sank my Patrol Boat. 2 2");
                    sunkAny = true;
                } 
                if (!sunkAny) {
                    System.out.println("No ships are sunk.");
                }

                // This block gets the coordinates to shoot from the user.
                String yc = "";
                int xc = -1;
                while (!(validLetters.contains(yc) && (xc <= 10 && xc > 0))) {
                    System.out.print("Enter a letter and a number in the form 'A1': ");
                    String input = in.next().toUpperCase().trim();
                    if (input.length() > 1) {
                        if (input.length() > 3)
                            input = input.substring(0, 3);

                        if (input.charAt(1) == '1' && input.endsWith("0")) {
                            xc = Integer.parseInt(input.substring(1, 3));
                        } else if ("123456789".contains(input.substring(1,2))) {
                            xc = Integer.parseInt(input.substring(1, 2));
                        }
                        yc = input.substring(0, 1);
                        if (xc == 0) {
                            System.out.println("That is not a valid space to shoot. ");
                            xc = -1;
                        } else {
                            if (validLetters.contains(yc)) {
                                if (oppBoard.checkIfAlreadyShot(xc - 1, validLetters.indexOf(yc))) {
                                    System.out.println("You have already shot there or that is not a valid space to shoot.");
                                    xc = -1;
                                }
                            } else {
                                System.out.println("That is not a valid space to shoot. ");
                                xc = -1;
                            }    
                        }
                        
                    }

                }

                // Both players shoot and then check to see if they've won.
                numOfMoves++;

                // The user gets to shoot 
                oppBoard.shoot(xc - 1, yc.charAt(0));
                // See if the user has won
                if (oppBoard.checkWin()) {
                    play = false;
                    clearCons();
                    System.out.println(oppBoard.formatBoard() + "\nYou Win!\nYou won in " + numOfMoves + " moves.\nPlease type in your name to save your highscore: ");
                    storeWin(numOfMoves, in.next());
                    printHighscore();
                }

                // The computer gets to shoot
                // Hunting mode - tries to finish off any reamining ships
                boolean shotThisTurn = false;
                loop:
                for (int i = 0; i < 10; i ++) {
                    for (int j = 0; j < 10; j++) {
                        if (yourBoard.getHiddenBoard()[j][i] == 'x') {
                            if (i >= 1) {
                                if (!yourBoard.checkIfAlreadyShot(i-1, j)) {
                                    yourBoard.shoot(i-1, validLetters.charAt(j));
                                    shotThisTurn = true;
                                    break loop;
                                }
                            }
                            if (j < 9) {
                                if (!yourBoard.checkIfAlreadyShot(i, j+1)) {
                                    yourBoard.shoot(i, validLetters.charAt(j+1));
                                    shotThisTurn = true;
                                    break loop;
                                }
                            }
                            if (i < 9) {
                                if (!yourBoard.checkIfAlreadyShot(i+1, j)) {
                                    yourBoard.shoot(i+1, validLetters.charAt(j));
                                    shotThisTurn = true;
                                    break loop;
                                }
                            }
                            if (j >= 1) {
                                if (!yourBoard.checkIfAlreadyShot(i, j-1)) {
                                    yourBoard.shoot(i, validLetters.charAt(j-1));
                                    shotThisTurn = true;
                                    break loop;
                                }
                            }
                        }
                    }
                }
                // Random - if there are no ships to finish off, just shoot randomly.
                while (!shotThisTurn) {
                    int x = rand.nextInt(10);
                    int y = rand.nextInt(10);
                    if (!yourBoard.checkIfAlreadyShot(x, y)) {
                        yourBoard.shoot(x, validLetters.substring(y).charAt(0));
                        shotThisTurn = true;
                    }
                }
                // check to see if the computer has won
                if(yourBoard.checkWin()) {
                    play = false;
                    clearCons();
                    storeWin(numOfMoves, "Computer");
                    System.out.println(yourBoard.formatBoard() + "\nYou Lose.\nYour opponent won in " + numOfMoves + " moves.");
                    printHighscore();
                }
            }
            // Play Again
            System.out.println("Do you want to play agian? (y/n) ");
        } while (in.next().charAt(0) == 'y');

        in.close();
    }

    /**
     * Stores your score and your name in the highscores file.
     * @param score
     * @param name
     * @throws IOException
     */
    public static void storeWin(int score, String name) throws IOException {
        FileWriter fileWriter = new FileWriter("highscores.txt", true);
        PrintWriter writer = new PrintWriter(fileWriter);
        writer.printf("\n%3d  %20.20s", score, name);
        writer.close();
    }

    /**
     * Prints the current best score and the name of the person who got that score.
     * @throws FileNotFoundException
     */
    public static void printHighscore() throws FileNotFoundException{
        File file = new File("highscores.txt");
        Scanner highscores = new Scanner(file);
        int lowestScore = Integer.MAX_VALUE;
        String name = "";
        while(highscores.hasNextLine()) {
            String line = highscores.nextLine();
            int num = Integer.parseInt(line.substring(0,3).trim());
            if (num < lowestScore) {
                lowestScore = num;
                name = line.substring(3).trim();
            }
        }
        System.out.println(name + " has the current highscore of " + lowestScore + " moves.");
        highscores.close();
    }

    /**
     * Clears the console.
     * Note: I havent tested it on a Mac, but I think it should work in any linux environment. It works great in Windows.
     */
    public static void clearCons() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException e) {}
    }
}
