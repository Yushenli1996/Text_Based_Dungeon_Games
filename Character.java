/**
 * Character Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 * <p>
 * "Character is effectively abstract, and should not be instantiated."  - Pr. Bell
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class Character {

    private int ID;
    protected String name;
    protected String description;
    protected Place currentPlace;
    protected ArrayList<Artifact> inventory; //charcter's possessions
    protected static TreeMap<Integer, Character> characters = new TreeMap<Integer, Character>();
    protected DecisionMaker decider;
    protected int health;
    protected int Defaulthealth;
    private IO io;

    //constructor
    protected Character(int inputID, String inputName, String inputDescription, int placeID) {
        ID = inputID;
        name = inputName;
        description = inputDescription;
        inventory = new ArrayList<Artifact>();  //initizalize arraylist -> call to new
        characters.put(ID, this); //put product into static collection
        currentPlace = Place.getPlaceByID(placeID);
    }

    //scanner constructor
    protected Character(Scanner scanner, int placeID, double version) {
        //init constructors
        inventory = new ArrayList<Artifact>();

        //Get ID and long name
        String line = Game.getCleanLine(scanner);

        Scanner lineScanner = new Scanner(line);
        //parse line
        ID = lineScanner.nextInt();
        name = lineScanner.nextLine();
        name = name.trim();

        if (ID <= 0) { //check enforce: exit if ID is not positive
            Console.displayln("ID must be a positive integer. Exitting.");
            System.exit(0);
        }

        if (version >= 5.1) { //only 5.1 and above has health
            line = Game.getCleanLine(scanner);
            health = Integer.parseInt(line);
            Defaulthealth = Integer.parseInt(line);
        }

        //Get num description line
        line = Game.getCleanLine(scanner);
        lineScanner = new Scanner(line);
        //parse line
        int numDescriptionLines = lineScanner.nextInt();
        if (numDescriptionLines <= 0) {
            Console.displayln("Must have at least one character description line. Exitting.");
            System.exit(0);
        }

        //rerieve the full description and save it into one variable
        description = ""; //start with empty string
        for (int j = 0; j < numDescriptionLines; j++) {
            String lineD = Game.getCleanLine(scanner);    //Get description lines
            lineScanner = new Scanner(lineD);
            String d = lineScanner.nextLine(); //
            description = description.concat("   " + d + "\n"); //concat next line
        }

        characters.put(this.ID, this); //put product into static collection

        if (placeID == 0) {
            Random rand = new Random();
            int x = rand.nextInt(14) + 1;
            currentPlace = Place.getRandomePlace();
        } else
            currentPlace = Place.getPlaceByID(placeID);

        currentPlace.addCharacter(this);


    } //end character scanner constructor

    //get character's io
    public IO getIO() {
        return io;
    }


    public void Shout() {
        getIO().displayln("I am a Character!!!");
    }

    //ID getter
    public int getID() {
        return ID;
    }

    //Name getter
    public String getName() {
        return name;
    }

    //currentPlace getter
    public Place getCurrentPlace() {
        return currentPlace;
    }

    //get health
    public int getHP() {
        return health;
    }

    //adds an Artifact object to character's inventory
    public void addToInventory(Artifact A) {
        if (A.weight() >= 0) { //check if pickable. This if is necessary.
            inventory.add(A);
        } else { //the weight is negative. Thus, it's too heavy and can't be picked up.
            //a print here results in a double print. So just skip on..
        }
    }

    //return an Artifact given its name
    public Artifact getArtifactByName(String name) {
        for (Artifact A : inventory) {
            if (A.name().equalsIgnoreCase(name)) {
                return A;
            }
        }
        return null; //didn't find it
    }

    //removes object from inventory
    public void removeFromInventory(Artifact A) {
        this.inventory.remove(A);
    }


    //remove myself from the game (character tree map)
    public void removeFromGame() {
        characters.remove(this.getID());
    }

    //returns either the character with required ID
    //or null if not found. But will exit first if that's the case.
    public static Character getCharacterByID(int ID) {
        Character C = characters.get(ID);
        if (C == null) {
            C.getIO().displayln(" ! Alert! Returning null character. Exiting.");
            System.exit(0);
        }
        return C;
    }

    //returns a valid random artifact from Character's inventory
    public Artifact getRandomArtifact() {
        int randomInt;

        try { //trying, but won't work for an empty list.
            randomInt = new Random().nextInt(inventory.size()); //get a random integer 0..size
        } catch (IllegalArgumentException exc) {
            return null; //empty artifact list -> return null Artifact
        }
        int iterator = 0;
        for (Artifact A : inventory) {
            if (iterator == randomInt) {    //if matches
                return A;                    //get that artifact
            }
            iterator++;
        }
        getIO().displayln(" ! Returning null. Check.");
        return null; //shouldn't really happen.
    }


    //user friendly printout:
    public void display(char extent) {
        if (extent == 'f') { //include this part only for full display
            getIO().displayln(name.toUpperCase());
            getIO().display(description);
            getIO().displayln(" * " + "location: " + currentPlace.name());
        }
        //print inventory
        if (Game.getGameVersion() <= 5.1)
            getIO().displayln(" * health: " + health);
        getIO().display(" * inventory: [ ");


        for (Artifact A : inventory) {
            getIO().display(A.name() + ", ");
        }
        getIO().displayln("]");
    }

    public void setIO(IO myIO) {
        io = myIO;
    }

    //the method that executes the move based on output from getMove();
    public void makeMove() {
        //overriden in children classes
    }

    //remove character from game (tree map)
    public void removeCharacter(int key) {
        characters.remove(key);
    }

    //drop all artifacts from char's inventory to current room
    public void DropAllAritfacts() {
        for (int i = 0; i < this.inventory.size(); i++) {
            currentPlace.addArtifact(this.inventory.get(i));
            this.removeFromInventory(this.inventory.get(i));
        }
    }

    //drops all artifacts in an inventory throughout the gameboard randomly
    public void disperseArtifacts() {
        for (int i = 0; i < this.inventory.size(); i++) {
            Place.getRandomePlace().addArtifact(this.inventory.get(i));
        }
        this.inventory.clear();
    }

    public void freepet() {
        Player p = (Player) this;
        p.freeAllPet();
    }
}
