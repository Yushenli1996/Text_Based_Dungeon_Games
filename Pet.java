/**
 * Pet Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 */

import java.util.Scanner;

public class Pet extends Character {

    private Player owner;

    //constructor
    public Pet(int inputID, String inputName, String inputDescription, int placeID) {
        super(inputID, inputName, inputDescription, placeID);
        decider = new Pet_MoveGenerator(); //new decisionMaker
        owner = null;
    }

    //Monster scanner constructor
    public Pet(Scanner scanner, int placeID, double version) {
        super(scanner, placeID, version);
        decider = new Pet_MoveGenerator();
        owner = null;
    }

    public Player getOwner() {
        return owner;
    } //getter

    public void setOwner(Player p) {
        owner = p;
    }

    public void setfree(Character C) {
        C.getIO().displayln("Pet is free and goes to the wild");
        owner = null;
    }

    @Override
    //Same as NPC, but supports mainly GO and GET, and will GET items for its owner
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
                if (argument.equals("")) { //Should never happen for Pet
                    getIO().displayln("Need to provide a direction to go.\n");
                    break;
                }

                //obtain direction
                String direction = argument;
                if (!Game.isValidDirection(direction)) { //Should never happen for Pet
                    getIO().displayln("No direction \"" + direction + "\" exists.");
                    break;
                }

                Place previousPlace = currentPlace; //keep the previousPlace
                currentPlace = currentPlace.followDirection(direction, this); //update currentPlace (go)
                currentPlace.addCharacter(this);
                previousPlace.removeCharacter(this);
                break;

            case GET:
                if (argument.equals("")) { //Should never happen for Pet
                    getIO().displayln("Need to provide an artifact to get.");
                    break;
                }

                //obtain artifact
                String artifact = argument;     //scan user input (artifact)
                artifact = artifact.trim();
                Artifact A = currentPlace.getArtifactByName(artifact); //get Artifact object from artifact string

                //get the item if it is there
                if (A != null) {
                    if (A.weight() >= 0) { //give a success message only if not too heavy.
                        getIO().displayln("Picked up the " + artifact.toLowerCase() + ".");
                        currentPlace.removeArtifactByName(A.name());
                        if (owner != null) {
                            owner.addToInventory(A);
                        } else {
                            addToInventory(A);
                        }
                    } else {
                        getIO().displayln("Item is too heavy to pick up");
                    }
                } else { //no item found
                    getIO().displayln("No " + artifact + " here to get!");
                }
                break;


            //when a not-supported command comes up.
            default:
                getIO().displayln("Command not supported.");
                break;
        }
    }
}

