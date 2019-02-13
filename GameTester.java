//Author: Yushen Li, yli265
//This is the GameTester class, where the main is located
//at, which calls the game constructor to initialize game

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class GameTester {
    //public static Console gameConsole = new Console(); //Create a always-display console for game history logging.

    public static void main(String[] args) {

        System.out.println("\n\n * Yushen Li, yli265");
        System.out.println(" * Biren Patel, bpate37");
        System.out.println(" * Matt Jankowski, mjanko5");


        //set up a file reader
        File infile = null;
        //Getting the filename
        if (args.length > 0) {
            infile = new File(args[0]);
        }
        //if fail to get filename then show error
        else {
            infile = new File("Error404");
        }

        while (true) {
            try {
                //Initialize game
                Scanner sc = new Scanner(infile);
                if (args.length == 2) {
                    if (args[1].matches("^[1-9]\\d*$")) {
                        Game game = new Game(sc, Integer.parseInt(args[1]));
                        game.play();
                    } else {
                        System.out.println("Argument should be a positive integer indicating the number of players");
                        break;
                    }
                } else {
                    Game game = new Game(sc);
                    game.play();
                }
                break;
            }
            //If error found, such as user's file input name is wrong
            //or user didn't input a filename, catch the error and ask
            //for user to re-input the filename
            //otherwise, if user type QUIT, then exit
            catch (FileNotFoundException F) {
                System.out.println("File not found");
                System.out.println("Please input a file name: ");
                System.out.flush();
                String userinput = KeyboardScanner.getKeyboardScanner().nextLine();

                //if user decided to quit without running
                if (userinput.equalsIgnoreCase("quit")) {
                    System.out.println("Quiting program...");
                    break;
                }
                //replace new input as file input name and check all condition again
                File infile2 = new File(userinput);
                infile = infile2;
            }
        }
        System.exit(0);
        return;
    }
}
