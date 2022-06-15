package battleship;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import static java.lang.System.*;

public class Main {
    /**
     * Checks a string for a number.
     *
     * @param str Сhecked string
     * @return
     */
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Method if the user entered parameters on the command line.
     *
     * @param args command line params.
     */
    public static void inputFromCmd(String[] args) {
        int[] shipCount = new int[5];
        int count = 0;
        if (!isNumeric(args[0]) || !isNumeric(args[1])) {
            out.println("You entered the wrong format!");
            inputFromColsole();
            return;
        }
        if (Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[1]) <= 0) {
            out.println("You entered the wrong format!");
            inputFromColsole();
            return;
        }
        for (int i = 2; i < args.length; i++) {
            if (isNumeric(args[i])) {
                if (Integer.parseInt(args[i]) >= 0) {
                    shipCount[i - 2] = Integer.parseInt(args[i]);
                    count++;
                }
            }
        }
        if (count != 5) {
            out.println("You have entered the wrong number of ship types \n" +
                    "or the arguments were entered in the wrong format!");
            out.println("");
            inputFromColsole();
            return;
        }
        Ocean ocean = createOcean(Integer.parseInt(args[0]), Integer.parseInt(args[1]), shipCount);
        if (ocean != null) {
            game(ocean);
        }
    }

    /**
     * Method if user entered parametrs from console.
     */
    public static void inputFromColsole() {
        Scanner input = new Scanner(in);
        int n = 0, m = 0;
        int[] shipCount = new int[5];
        String ans1 = "", ans2 = "";
        do {
            out.println("Enter a size of ocean(number of rows and columns):");
            ans1 = input.next();
            ans2 = input.next();
        } while (!isNumeric(ans1) || !isNumeric(ans2));
        n = Integer.parseInt(ans1);
        m = Integer.parseInt(ans2);
        if (n <= 0 || m <= 0) {
            inputFromColsole();
            return;
        }
        out.println("Enter the number of ships in order - ");
        out.println("carrier battleship cruiser destroyer submarine");
        int count = 0;
        for (int i = 0; i < shipCount.length; i++) {
            out.println("Enter a number of another ship:");
            if (isNumeric(ans1 = input.next())) {
                if (Integer.parseInt(ans1) >= 0) {
                    shipCount[i] = Integer.parseInt(ans1);
                    count++;
                }
            }
        }
        if (count != 5) {
            out.println("You have entered the wrong number of ship types \n" +
                    "or the arguments were entered in the wrong format!");
            inputFromColsole();
            return;
        }
        Ocean ocean = createOcean(n, m, shipCount);
        if (ocean != null) {
            game(ocean);
        }
    }

    /**
     * Method for creating ocean.
     *
     * @param n         num of rows.
     * @param m         num of columns.
     * @param shipCount num of ships for each type.
     * @return
     */
    public static Ocean createOcean(int n, int m, int[] shipCount) {
        Scanner input = new Scanner(in);
        Ocean ocean = null;
        do {
            try {
                ocean = new Ocean(n, m, shipCount);
                out.println("The ocean has been successfully created!");
                out.println(ocean);
                String ans1 = "";
                do {
                    out.println("Add count of torpedoes:");
                    ans1 = input.next();
                } while (!isNumeric(ans1));
                int count_of_torpedoes = Integer.parseInt(ans1);
                if (count_of_torpedoes >= 0 && count_of_torpedoes <= shipCount.length) {
                    ocean.setCount_of_torpedoes(count_of_torpedoes);
                } else {
                    out.println("The number of torpedoes cannot be negative or be more than" +
                            "number of ships");
                    break;
                }
                out.println("Do you want to add recovery mode?");
                if (!answerDialog(input)) ocean.setRecovery_mode(true);
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage());
                out.println("Do you want to continue the game?");
                if (answerDialog(input)) return null;
                else {
                    inputFromColsole();
                    return null;
                }
            }
        } while (ocean == null);
        return ocean;
    }

    /**
     * Method for getting a response from the user.
     *
     * @param input scanner variable for reading from user.
     * @return
     */
    private static boolean answerDialog(Scanner input) {
        String answer;
        do {
            out.println("Enter 'yes' or 'no':");
            answer = input.next();
            if (answer.equals("yes")) {
                break;
            } else if (answer.equals("no")) {
                return true;
            }
        } while (input.nextLine() != "yes" || input.nextLine() != "no");
        return false;
    }

    /**
     * Method for starting BattleShip.
     *
     * @param ocean created ocean.
     */
    public static void game(Ocean ocean) {
        Scanner input = new Scanner(in);
        boolean isRun = true;
        int x, y;
        while (isRun) {
            out.println("Enter a row and a column:");
            String tmp = input.next();
            if (tmp.equals("T")) {
                String ans1 = "", ans2 = "";
                ans1 = input.next();
                ans2 = input.next();
                if (!isNumeric(ans1) || !isNumeric(ans2)) {
                    continue;
                }
                y = Integer.parseInt(ans1);
                x = Integer.parseInt(ans2);
                if (!(y > 0 && y <= ocean.getN() && x > 0 && x <= ocean.getM())) {
                    continue;
                }
                out.println(ocean.tryToHitWithTorpedo(y, x));
            } else {
                String ans2 = "";
                ans2 = input.next();
                if (!isNumeric(tmp) || !isNumeric(ans2)) {
                    continue;
                }
                y = Integer.parseInt(tmp);
                x = Integer.parseInt(ans2);
                if (!(y > 0 && y <= ocean.getN() && x > 0 && x <= ocean.getM())) {
                    continue;
                }
                out.println(ocean.tryToHit(y, x));
            }
            out.println(ocean);
            if (ocean.getIsKilled()) {
                out.println("You win!");
                out.println("Total count of shots: " + ocean.getCount_of_shots());
                out.println("Bye!");
                isRun = false;
            }
        }
    }

    /**
     * The original start point of the program.
     *
     * @param args argument parametrs.
     */
    public static void main(String[] args) {
        try {
            if (args.length != 7) {
                inputFromColsole();
            } else {
                inputFromCmd(args);
            }
        } catch (Exception e) {
            out.println("Unknown error");
        }
    }
}
