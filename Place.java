//Biren

import java.util.*;

public class Place {

    protected int placeId;  //ID of the place
    protected String placeName = "";  //Name of the location
    protected String placeDescription;  //What the place looks like
    protected ArrayList<Direction> directions; //List of places
    protected ArrayList<Artifact> artifacts; //List of artifacts in this Place
    protected ArrayList<Character> characters; //List of characters in this place
    protected String Type;

    public static Map<Integer, Place> placesMap = new HashMap<>();

    /* constructor for place class which creates place objects
     * and assigning the variables to name and location, de //broken comment?
     */

    public Place(Scanner scanner, double version) {
        Type = "R";
        String line = Game.getCleanLine(scanner);
        String[] data = line.split("\\s+"); //what is this?
        this.placeId = Integer.parseInt(data[0]);
        for (int i = 1; i < data.length; i++) {
            placeName = placeName + data[i] + " "; //what is + " " for?
        }
        placeName.trim();

        int iDesLineCount = 0;
        if (scanner.hasNextLine()) {
            iDesLineCount = Integer.parseInt(scanner.nextLine());
        }

        placeDescription = "";

        for (; iDesLineCount > 0; iDesLineCount--) {
            this.placeDescription = this.placeDescription + scanner.nextLine() + '\n';
        }

        this.directions = new ArrayList<Direction>();
        this.artifacts = new ArrayList<Artifact>();
        this.characters = new ArrayList<Character>();
        placesMap.put(this.placeId, this);

    }

    /*
     * Constructor to create Place from given arguments
     * */
    public Place(int ID, String name, String description) {
        this.placeId = ID;
        this.placeName = name;
        this.placeDescription = description;
        placesMap.put(this.placeId, this);

        this.directions = new ArrayList<Direction>();
        this.artifacts = new ArrayList<Artifact>();
        this.characters = new ArrayList<Character>();
    }

    /* Getters */

    //Returns the name of the place
    public String name() {
        return placeName;
    }

    //getter of id
    public int getPlaceId() {
        return placeId;
    }

    //Returns the description of the place
    public String description() {
        return placeDescription;
    }

    //Adds direction to direction object
    public void addDirection(Direction direction) {
        this.directions.add(direction);
    }

    //Adds artifact to artifact list
    public void addArtifact(Artifact artifact) {
        this.artifacts.add(artifact);
    }


    //static method to get place by giving place id
    public static Place getPlaceByID(int placeId) {
        return placesMap.get(placeId);
    }

    /*
     * This method will select random place
     * It will ignore Exit and Nowhere places
     * */
    public static Place getRandomePlace() {
        Random rand = new Random();
        int iRandomePlace = rand.nextInt(placesMap.size());
        Place P = placesMap.get((placesMap.keySet().toArray())[iRandomePlace]);

        //if random generator chose places nowhere, exit, island, or secret room, or poison rooms: Redo.
        if (P.getPlaceId() == 0 || P.getPlaceId() == 1 || P.getPlaceId() == 110 || P.getPlaceId() == 109 || P.getPlaceId() == 94 || P.getPlaceId() == 93) {
            P = getRandomePlace(); //call recuresively until 0 and 1 not selected
        }
        return P;
    }

    //return artifact using its name
    public Artifact getArtifactByName(String name) {
        for (Artifact A : artifacts) {
            if (A.name().equalsIgnoreCase(name)) {
                return A;
            }
        }
        return null; //didn't find it
    }

    /*
     * Remove given artifact from this place
     * */
    public Artifact removeArtifactByName(String artifactName) {
        for (int i = 0; i < artifacts.size(); i++) {             //BIREN, what is this loop for? Matt wants to know.
            if (artifacts.get(i).name().equalsIgnoreCase(artifactName)) {
                return artifacts.remove(i);
            }
        }
        return null;
    }

    /*
     * Use key.
     * Try to toggle matched direction lock status
     * */
    public void useKey(Artifact artifact, Character C) {
        //iterate through the direction list and try to use those
        for (int i = 0; i < directions.size(); i++) {
            directions.get(i).useKey(artifact, C);
        }
    }

    /*
     * Try to move through given direction from this place
     * */
    public Place followDirection(String direction, Character C) {
        for (int i = 0; i < directions.size(); i++) {
            if (directions.get(i).match(direction)) {
                return directions.get(i).follow(C);
            }
        }
        C.getIO().displayln("No passage " + Direction.returnDir(direction) + ". Staying.");
        return this;
    }

    //print a list of all places
    public static void printAll() {
        Console.displayln("places: ");
        int e = 1;
        for (Integer i : placesMap.keySet()) {
            Console.displayln(e + ": " + i + ": " + Place.getPlaceByID(i).name());
            e++;
        }
    }

    //print out place information along with useful information about artifacts and characters
    public void display(char extent, Character C) { //extent: 'b' basic print; 'f' full print
        C.getIO().displayln("Place name : " + placeName);
        C.getIO().display("Place description : " + placeDescription);

        if (extent == 'b') { //displays just a list of artifacts
            C.getIO().display(" * artifacts here: [ ");
            for (int t = 0; t < artifacts.size(); t++) {
                C.getIO().display(artifacts.get(t).name() + ", ");
            }
            C.getIO().displayln("]");

            showAllOtherCharactersInCurrentPlace(C);
        } else if (extent == 'f') { //displays all artifacts and all their information
            C.getIO().displayln("------------------------Artifacts in place---------------------------");
            //print artifact list which stored in place
            for (int t = 0; t < artifacts.size(); t++) {
                artifacts.get(t).display(C);
            }
        }
    }

    //get all char's name in current location and put them into a string of arraylist
    public ArrayList<String> curPlaceCharList() {
        ArrayList<String> CharArray = new ArrayList<>();
        for (Character C : this.characters) {
            CharArray.add(C.name);
        }
        return CharArray;
    }

    //get all artifact's name in current location and put them into a string of arraylist
    public ArrayList<String> curPlaceArtifactList() {
        ArrayList<String> ArtiArray = new ArrayList<>();
        for (Artifact A : this.artifacts) {
            ArtiArray.add(A.zName);
        }
        return ArtiArray;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
    }

    public Artifact getRandomArtifact(Character C) {
        int randomInt;

        try { //trying, but won't work for an empty list.
            randomInt = new Random().nextInt(artifacts.size()); //get a random integer 0..size
        } catch (IllegalArgumentException exc) {
            return null; //empty artifact list -> return null Artifact
        }
        int iterator = 0;
        for (Artifact A : artifacts) {
            if (iterator == randomInt) {    //if matches
                return A;                    //get that artifact
            }
            iterator++;
        }
        C.getIO().displayln(" ! Returning null. Check.");
        return null; //shouldn't really happen.
    }

    public Character getRandomCharacter() {
        Random rand = new Random();
        int x = rand.nextInt(this.characters.size());
        for (int i = 0; i < this.characters.size(); i++) {
            if (i == x)
                return this.characters.get(i);
        }
        return null;
    }

    //returns a valid random direction within this place
    public Direction getRandomDirection(Character C) {
        //int randomInt = new Random().nextInt(directions.size()); //get a random integer 0..size
        if (this.directions.size() > 0) {
            int randomInt = new Random().nextInt(this.directions.size());
            int iterator = 0;
            for (Direction D : directions) {
                if (iterator == randomInt) {    //if matches
                    return D;                    //get that direction
                }
                iterator++;
            }
        }
        C.getIO().displayln(" ! Returning null. Check.");
        return null; //shouldn't really happen.
    }

    //    //retuen all characters in this place

    public void showAllOtherCharactersInCurrentPlace(Character currentChar) { //everyone except current player
        String charactersInRoom = "";
        for (Character C : this.characters) {
            //put everything except myself into string
            if (C != currentChar) {
                charactersInRoom = charactersInRoom + C.getName() + ", ";
            }
        }
        currentChar.getIO().displayln("* other characters here: [ " + charactersInRoom + "]");
    }

    public Character getCharacter(String s) {
        for (Character C : characters) {
            if (C.getName().equalsIgnoreCase(s))
                return C;
        }
        return null;
    }

    public boolean checkActivate(Character character) {
        String s = "Excalibur";
        s = s.trim();
        Artifact A = character.getArtifactByName(s);
        if (A != null)
            return true;
        return false;
    }

    public boolean checkPlayerResponds(Character character) {

        character.getIO().displayln("Welcome to the Final Boss Room, it seems like your qualify to battle the final boss.\nDo you want to the fight YES/NO");
        String s = character.getIO().getLine();
        if (s.equalsIgnoreCase("yes")) {
            character.currentPlace = Place.getPlaceByID(110);
            return true;
        } else {
            character.getIO().displayln("Seems like you're not ready for the final boss fight, come back anytime when you're ready");
            return false;
        }
    }

    public void UseSpeicalEffect(Character character) {
        //override by its inheritance classes
    }

    public String RoomEnvironment() {
        return Type;
    }
}
