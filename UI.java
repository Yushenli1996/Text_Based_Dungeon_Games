/**
 * UserInterface Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 */

public class UI implements DecisionMaker {

    public Move getMove(Character C, Place P) {

        String wholeLine = C.getIO().getLine();

        String[] arrayLine = wholeLine.split("\\s+");
        String command = arrayLine[0];

        command = (command.equalsIgnoreCase("inventory")) ? "inve" : command;   //accept the string "inventory" as "inve"
        command = (command.equalsIgnoreCase("quit")) ? "exit" : command;        //accept the string "quit" as "exit"
        command = (command.equalsIgnoreCase("eat")) ? "use" : command;        //accept the string "eat" as "use"
        command = (command.equalsIgnoreCase("drink")) ? "use" : command;        //accept the string "drink" as "use"
        command = (command.equalsIgnoreCase("p")) ? "pass" : command;        //accept the string "p" as "pass"
        command = (command.equalsIgnoreCase("move")) ? "go" : command;        //accept the string "move" as "go"

        String argument = "";
        for (int i = 1; i < arrayLine.length; i++)
            argument = argument + arrayLine[i] + " "; //Example: drop [leather bag]
        argument = argument.trim();

        return new Move(command, argument);
    }
}