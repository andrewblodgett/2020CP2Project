import java.util.Scanner;
import java.util.Random;

public class BattleShip {
    public static void main(String[] args) {
        boolean play = true;
        Random rand = new Random();
        Board yourBoard = new Board();
        Board oppBoard = new Board();
        String validLetters = "ABCDEFGHIJ";
        Scanner in = new Scanner(System.in);
        while (play) {

            System.out.println("This is your board:");
            System.out.println(yourBoard.formatBoard());
            // for(char[] a : yourBoard.getBoard()) {
            //     for(char c: a) {
            //         System.out.print(c + " ");
            //     }
            //     System.out.println("");
            // }
            System.out.println("This is your opponent's board:");
            System.out.println(oppBoard.formatBoard());
            // for(char[] a : oppBoard.getBoard()) {
            //     for(char c: a) {
            //         System.out.print(c + " ");
            //     }
            //     System.out.println("");
            // }

            String yc = "";
            int xc = -1;
            while (!(validLetters.contains(yc) && (xc <= 10 && xc > 0))) {
                System.out.print("Enter a letter and a number in the form 'A1': ");
                String input = in.next().toUpperCase().strip();
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

            if (oppBoard.checkWin())
                play = false;
        }

        in.close();
    }
}
