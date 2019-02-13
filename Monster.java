/**
 * Monster Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 */

import java.util.Scanner;

public class Monster extends Character {

    //constructor
    public Monster(int inputID, String inputName, String inputDescription, int placeID) {
        super(inputID, inputName, inputDescription, placeID);
        decider = new Monster_MoveGenerator();
    }

    //Monster scanner constructor
    public Monster(Scanner scanner, int placeID, double version) {
        super(scanner, placeID, version);
        decider = new Monster_MoveGenerator();
    }

    @Override
    //Same as NPC, but will mainly use GO and USE
    // -> use means 'attack' since weapons are the only artifacts monsters can posses.
    public void makeMove() {
        getIO().displayln("");
        getIO().displayln("> " + this.name.toUpperCase() + "'s move");

        Move move = decider.getMove(this, this.currentPlace); //get a move based on a decider
        String argument = move.getArgument();

        //compare generated move with one of the following moves:
        switch (move.getMov()) {
            case NONE:
                getIO().displayln("No such command exists.");
                break;

            case EXIT:
                getIO().displayln("Thanks for playing the game! Quitting...");
                System.exit(0); //exit

            case PASS:
                break;

            case GO:
                if (argument.equals("")) { //Should never happen for Monster
                    getIO().displayln("Need to provide a direction to go.\n");
                    break;
                }

                //obtain direction
                String direction = argument;
                if (!Game.isValidDirection(direction)) { //Should never happen for Monster
                    getIO().displayln("No direction \"" + direction + "\" exists.");
                    break;
                }

                Place previousPlace = currentPlace; //keep the previousPlace
                currentPlace = currentPlace.followDirection(direction, this); //update currentPlace (go)
                currentPlace.addCharacter(this);
                previousPlace.removeCharacter(this);
                break;

            case USE: //means: attack
                if (argument.equals("")) {  //Should never happen for Monster
                    getIO().displayln("Need to provide an artifact to use.");
                    break;
                }

                //obtain artifact
                String artifact = argument;     //scan user input (artifact)
                artifact = artifact.trim();
                Artifact A = getArtifactByName(artifact);

                //use the item if available
                if (A != null) {
                    A.use(currentPlace, this);
                } else {
                    getIO().displayln("No " + artifact + " in inventory to use!");
                }
                break;

            //when a not-supported command comes up.
            default:
                getIO().displayln("Command not supported.");
                break;
        }
    }
}