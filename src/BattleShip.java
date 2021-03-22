import java.util.Scanner;
import java.util.Random;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Battleship {
    public static void main(String[] args) throws IOException {
        boolean play = true;
        Random rand = new Random();
        Board yourBoard = new Board();
        Board oppBoard = new Board();
        String validLetters = "ABCDEFGHIJ";
        Scanner in = new Scanner(System.in);
        int numOfMoves = 0;
        while (play) {

            System.out.println("This is your board:");
            System.out.println(yourBoard.formatBoard());

            System.out.println("This is your opponent's board:");
            System.out.println(oppBoard.formatBoard());

            String yc = "";
            int xc = -1;
            while (!(validLetters.contains(yc) && (xc <= 10 && xc > 0))) {
                System.out.print("Enter a letter and a number in the form 'A1': ");
                String input = in.next().toUpperCase().trim();
                if (input.length() > 1) {
                    if (input.length() > 3)
                        input = input.substring(0, 3);

                    if (input.charAt(1) == '1' && input.endsWith("0"))
                        xc = Integer.parseInt(input.substring(1, 3));
                    else
                        xc = Integer.parseInt(input.substring(1, 2));

                    yc = input.substring(0, 1);
                    if (oppBoard.checkIfAlreadyShot(xc - 1, validLetters.indexOf(yc))) {
                        System.out.println("You have already shot there.");
                        xc = -1;
                    }
                }

            }

            oppBoard.shoot(xc - 1, yc.charAt(0));
            yourBoard.shoot(rand.nextInt(10), validLetters.substring(rand.nextInt(10)).charAt(0));
            numOfMoves++;
            if (oppBoard.checkIfSunk().length() != 0) {
              System.out.println("You sank my " + oppBoard.checkIfSunk());
            }
            if (oppBoard.checkWin()) {
                play = false;
                System.out.println(oppBoard.formatBoard() + "You Win!\n");
                win(numOfMoves);
            }
        }

        in.close();
    }

    public static void win(int score) throws IOException {
        FileWriter fileWriter = new FileWriter("highscores.txt", true);
        PrintWriter writer = new PrintWriter(fileWriter);
        writer.print(score);
        writer.close();
    }
}
