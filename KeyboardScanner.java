/**
 * KeyboardScanner Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 */

import java.util.Scanner;

public class KeyboardScanner {
    private static Scanner sc;

    public KeyboardScanner() {
        sc = null;
    }

    //will return a System.in (keyboard) scanner.
    //The purpose is so that the keyboard scanner gets init only once per whole game.
    public static Scanner getKeyboardScanner() {
        if (sc == null) { //was never yet initialized
            sc = new Scanner(System.in);
        }
        return sc;
    }
}

