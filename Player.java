/**
 * Player Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Character {

    private ArrayList<Pet> playerPets;

    //constructor
    public Player(int inputID, String inputName, String inputDescription, int placeID) {
        super(inputID, inputName, inputDescription, placeID);
        health = 100;
        decider = new UI(); //new decisionMaker
        playerPets = new ArrayList<Pet>();
    }

    //scanner constructor
    public Player(Scanner scanner, int placeID, double version) {
        super(scanner, placeID, version); //call parent (Character) constructor
        decider = new UI(); //call default constructor. User Interface may need a custom constructor later.
        playerPets = new ArrayList<Pet>();
    }

    public ArrayList<Pet> getPets() {
        return playerPets;
    }

    //the method that executes the move based on output from getMove();
    public void makeMove() {
        boolean moved = false;

        getIO().displayln("");
        getIO().displayln("> " + this.name.toUpperCase() + "'s move");

        this.display('b'); //Inside player's class
        currentPlace.display('b', this);

        while (!moved) {
            Move move = decider.getMove(this, this.currentPlace); //get a move based on a decider
            String argument = move.getArgument();
            //compare generated move with one of the following moves:
            switch (move.getMov()) {
                case NONE:
                    //getIO().displayln("No such command exists.");
                    //this runs when a bad command is typed, or an empty command is sent (ex: "interfaces buttons")
                    break;

                case EXIT:
                    getIO().displayln("Thanks for playing the game! Quitting...");
                    System.exit(0); //exit

                case PASS:
                    getIO().displayln("Passing");
                    moved = true;
                    break;

                case GO:
                    if (argument.equals("")) { //no argument provided - message
                        getIO().displayln("Need to provide a direction to go.\n");
                        break;
                    }

                    String direction = argument;
                    if (!Game.isValidDirection(direction)) {
                        getIO().displayln("No direction \"" + direction + "\" exists.");
                        break;
                    }

                    //'Go' operations
                    Place previousPlace = currentPlace; //keep the previousPlace
                    currentPlace = currentPlace.followDirection(direction, this); //update currentPlace (go)
                    currentPlace.addCharacter(this);
                    previousPlace.removeCharacter(this);

                    if (currentPlace.RoomEnvironment().equalsIgnoreCase("H") || currentPlace.RoomEnvironment().equalsIgnoreCase("P")) {
                        currentPlace.UseSpeicalEffect(this);
                    }

                    //if player enters the secret room, lock the door behind itself
                    if (currentPlace.getPlaceId() == 109) {
                        for (Direction D : previousPlace.directions) {
                            if (D.getID() == 33) {
                                D.lock();
                            }
                        }
                    }
                    moved = true;
                    break;

                case GET:
                    if (argument.equals("")) { //no argument provided - message
                        getIO().displayln("Need to provide an artifact to get.");
                        break;
                    }

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
                    moved = true;
                    break;

                case DROP:
                    if (argument.equals("")) { //no argument provided - message
                        getIO().displayln("Need to provide an artifact to drop.");
                        break;
                    }

                    artifact = argument;     //scan user input (artifact)
                    artifact = artifact.trim();
                    A = this.getArtifactByName(artifact);

                    //drop the item from the inventory if it's there
                    if (A != null) {
                        removeFromInventory(A);
                        currentPlace.addArtifact(A);
                        getIO().displayln("Dropped the " + artifact.toLowerCase() + ".");
                    } else {
                        getIO().displayln("No " + artifact + " in your inventory to drop!");
                    }
                    moved = true;
                    break;

                case USE:
                    if (argument.equals("")) { //no argument provided - message
                        getIO().displayln("Need to provide an artifact to use.");
                        break;
                    }

                    artifact = argument;     //scan user input (artifact)
                    artifact = artifact.trim();
                    A = getArtifactByName(artifact);

                    //use the item if available
                    if (A != null) {
                        A.use(currentPlace, this);
                    } else {
                        getIO().displayln("No " + artifact + " in your inventory to use!");
                    }
                    moved = true;
                    break;

                //print out inventory
                case INVE:
                    getIO().display("Artifacts in " + name + "'s inventory: [ ");
                    for (Artifact A_ : inventory) {
                        getIO().display(A_.name() + ", ");
                    }
                    getIO().displayln("]");
                    break;

                //take a look around you (you can also type: 'look here', 'look around', 'look at me')
                case LOOK:
                    if (argument.equals("")) { //no argument provided - message

                        getIO().displayln(this.name.toUpperCase() + " ");
                        currentPlace.display('f', this); //Place's display
                        this.display('b'); //Player display
                        getIO().displayln("");
                        break;
                    }

                    //user is requesting info about the place
                    if (argument.equalsIgnoreCase(currentPlace.name()) ||
                            argument.equalsIgnoreCase("here") ||
                            argument.equalsIgnoreCase("around")) {
                        currentPlace.display('f', this);
                        break;
                    }

                    //user is requesting info about the character
                    else if (argument.equalsIgnoreCase(name)
                            || argument.equalsIgnoreCase("at me")) {
                        display('f');
                        break;
                    }

                    artifact = argument;     //scan user input (artifact)
                    artifact = artifact.trim();
                    A = getArtifactByName(artifact);
                    if (A == null) { //not in the inventory
                        A = currentPlace.getArtifactByName(artifact);
                    }
                    if (A == null) { //not in current place (...nor inve) ... nor current C or P

                        getIO().displayln(" ! \"" + argument + "\" is out of scope.\n"
                                + "   Can look only at the character & its artifacts,\n"
                                + "   OR at the current place & its artifacts.");
                        break;
                    }

                    A.display(this);
                    getIO().displayln("");
                    break;


                //Gives some help with commands and their arguments for the user
                case HELP:
                    getIO().displayln(" ? Any player can enter one of these Commands:");
                    getIO().displayln(" ? Go, Get, Drop, Use, Exit, Inve, Look, Help, Pass, Dance, Sleep, Tame");
                    getIO().displayln(" ? With 'Go' provide a valid direction.");
                    getIO().displayln(" ? With 'Get', 'Drop', and 'Use', provide an artifact.");
                    getIO().displayln(" ? With 'Look', provide something around you to look at.");
                    getIO().displayln(" ? With 'Tame', provide the name of a pet in this room.");
                    getIO().displayln("Type in gui1 or gui2, gui3 to switch GUI");
                    break;

                //use ascii art to show a dancing figure
                case DANCE:
                    getIO().displayln(" _   \\");
                    getIO().displayln("  \\o_/");
                    getIO().displayln("   #");
                    getIO().displayln(" _/ \\_");
                    moved = true;
                    break;

                //use ascii art to show a sleeping figure
                case SLEEP:
                    getIO().displayln("      ZZ");
                    getIO().displayln("   zzZ");
                    getIO().displayln("\\\\o<--<");
                    getIO().displayln("/```````\\");
                    if (this.health < 100) {
                        this.health = this.health + 3;
                        getIO().displayln("Had a good rest, health retrieved by 3");
                    }
                    moved = true;
                    break;

                //tame a pet that is in the same room
                case TAME:
                    if (argument.equals("")) { //no argument provided - message
                        getIO().displayln("Need to select a pet to tame.");
                        break;
                    }

                    String pet = argument;     //scan user input (artifact)
                    pet = pet.trim();
                    Character C = currentPlace.getCharacter(pet);
                    if (!(C instanceof Pet)) {
                        getIO().displayln("That was not a valid pet.");
                        break;
                    }

                    if (C instanceof Pet) { //if user entered a pet name
                        Pet P = (Pet) C;   //cast character to a pet
                        if (P != null) {   //use the item if available
                            tamePet(P);
                            //A.use(currentPlace, this);
                        } else {
                            C.getIO().displayln("No " + pet + " in room to tame!");
                        }
                    } else {
                        C.getIO().displayln("No! " + C.getName() + " is a " + C.getClass().getSimpleName() + ". Only a pet can be tamed!");
                    }
                    moved = true;
                    break;

                //when a not-supported command comes up.
                default:
                    getIO().displayln("Command not supported.");
                    break;
            }//end switch statement
        }
    }//end while loop
    //end makeMove

    //used by player to tame a given pet
    public void tamePet(Pet P) {
        if (P.getOwner() == null) { //pet does not have a owner
            getIO().displayln("Taming " + P.getName() + ".");
            this.playerPets.add(P);
            P.setOwner(this);
            for (Artifact A : P.inventory) {
                this.inventory.add(A);
            }
            P.inventory.clear();
        } else {
            getIO().displayln("Pet is owned by " + P.getOwner().getName() + ". Cannot tame.");
        }
    }

    //overrides character's display(). Difference: This one also prints the pet list.
    @Override
    public void display(char extent) {
        if (extent == 'f') { //include this part only for full display
            getIO().displayln(name.toUpperCase());
            getIO().display(description);
            getIO().displayln(" * " + "location: " + currentPlace.name());
        }
        //print inventory
        getIO().displayln(" * health: " + health);
        getIO().display(" * inventory: [ ");
        for (Artifact A : inventory) {
            getIO().display(A.name() + ", ");
        }
        getIO().displayln("]");

        //print pet list
        getIO().display(" * pets: [ ");
        for (Pet P : playerPets) {
            getIO().display(P.getName() + ", ");
        }
        getIO().displayln("]");
    }

    public void freeAllPet() {
        for (Pet p : this.playerPets) {
            p.setfree(this);
        }
    }
}