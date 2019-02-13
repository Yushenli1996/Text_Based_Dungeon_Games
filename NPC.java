/**
 * NPC Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 */

import java.util.Scanner;

public class NPC extends Character {


    //constructor
    public NPC(int inputID, String inputName, String inputDescription, int placeID) {
        super(inputID, inputName, inputDescription, placeID);
        decider = new NPC_MoveGenerator(); //new decisionMaker
    }

    //NPC scanner constructor
    public NPC(Scanner scanner, int placeID, double version) {
        super(scanner, placeID, version);
        decider = new NPC_MoveGenerator();
    }

    //the method that executes the move based on output from getMove();
    //allowed moves are DROP, USE, GO, PASS, and GET
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

            //shouldn't happen in current versions
            case EXIT:
                getIO().displayln("Thanks for playing the game! Quitting...");
                System.exit(0); //exit

            case PASS:
                break;

            case GO:
                if (argument.equals("")) { //Should never happen for NPC
                    getIO().displayln("Need to provide a direction to go.\n");
                    break;
                }

                //obtain direction
                String direction = argument;
                if (!Game.isValidDirection(direction)) { //Should never happen for NPC
                    getIO().displayln("No direction \"" + direction + "\" exists.");
                    break;
                }

                Place previousPlace = currentPlace; //keep the previousPlace
                currentPlace = currentPlace.followDirection(direction, this); //update currentPlace (go)
                currentPlace.addCharacter(this);
                previousPlace.removeCharacter(this);
                break;

            case GET:
                if (argument.equals("")) { //Should never happen for NPC
                    getIO().displayln("Need to provide an artifact to get.");
                    break;
                }

                //obtain artifact
                String artifact = argument;     //scan user input (artifact)
                artifact = artifact.trim();
                Artifact A = currentPlace.getArtifactByName(artifact); //get Artifact object from artifact string

                //get the item if it is there
                if (A != null) {
                    //print statement (can be remove later):
                    //C.getIO().displayln("Putting " + Artifact.name() + " into your inventory.");
                    //currentPlace.removeArtifactByName(A.name());
                    //addToInventory(A);

                    if (A.weight() >= 0) { //give a success message only if not too heavy.
                        getIO().displayln("Picked up the " + artifact.toLowerCase() + ".");
                        currentPlace.removeArtifactByName(A.name());
                        addToInventory(A);
                    } else {
                        getIO().displayln("Item is too heavy to pick up");
                    }
                } else { //no item found
                    getIO().displayln("No " + artifact + " here to get!");
                }
                break;

            case DROP:
                if (argument.equals("")) {  //Should never happen for NPC
                    getIO().displayln("Need to provide an artifact to drop.");
                    break;
                }

                //obtain artifact
                artifact = argument;     //scan user input (artifact)
                artifact = artifact.trim();
                A = this.getArtifactByName(artifact);

                //drop the item from the inventory if it's there
                if (A != null) {
                    removeFromInventory(A);
                    currentPlace.addArtifact(A);
                    getIO().displayln("Dropped the " + artifact.toLowerCase() + ".");
                } else {
                    getIO().displayln("No " + artifact + " in inventory to drop!");
                }
                break;

            case USE:
                if (argument.equals("")) {  //Should never happen for NPC
                    getIO().displayln("Need to provide an artifact to use.");
                    break;
                }

                //obtain artifact
                artifact = argument;     //scan user input (artifact)
                artifact = artifact.trim();
                A = getArtifactByName(artifact);


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