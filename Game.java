//Author: Yushen Li, yli265
//This is a Game class. It stores all the game information, users access
//by commands to interact things in game.

import java.util.Scanner;

public class Game {
    private String gameName = "";
    private static Place currentPlace;
    private static Character currentMover;
    private static double GameVersion;

    //    A constructor for creating the Game object. The Scanner should be connected to an open
    //    file conforming to file format GDF ( Game Data Format ) 3.1 or 4.0 or 5.0 This method will pass the
    //    Scanner on to Place, Direction, and Artifact constructors as needed
    Game(Scanner sc) {
        //READ gamename info until find something that is different from a blank line or start with
        String readIndex = Game.getCleanLine(sc);

        //Get the game name
        this.gameName = readIndex;
        String gameVersion[] = readIndex.split("\\s+");

        double version = Double.parseDouble(gameVersion[1]);
        GameVersion = version;
        readIndex = Game.getCleanLine(sc);

        //Split again and get the total number of places
        String placesplit[] = readIndex.split("\\s+");
        //throw away all useless spaces in between
        //numPlace is the total count of places that need to be inserted
        int numPlace = Integer.parseInt(placesplit[1]);

        //Start to loop through found places and add them into Place vector
        if (version < 5.1) {
            for (int i = 0; i < numPlace; i++) {
                new Place(sc, version);
            }
        }

        if (version >= 5.1) {
            for (int i = 0; i < numPlace; i++) {
                readIndex = getCleanLine(sc);
                if (readIndex.equalsIgnoreCase("R")) {
                    new Place(sc, version);
                } else if (readIndex.equalsIgnoreCase("H")) {
                    new HealingRoom(sc, version);
                } else if (readIndex.equalsIgnoreCase("P")) {
                    new PoisonRoom(sc, version);
                } else if (readIndex.equalsIgnoreCase("F")) {
                    new FinalBossRoom(sc, version);
                } else {
                    Console.displayln("Places type not found : " + readIndex);
                }
            }
        }

        //Adding no existing places
        Place Exit = new Place(1, "Exit", "THIS IS EXIT");
        Place Nowhere = new Place(0, "Nowhere", "THIS IS NOWHERE");


        /*-----------------------------------------------------------------------------*/
        /*-----------------------------------------------------------------------------*/
        //update the scanner location
        //READ DIRECTION INFO such as Direction 35
        readIndex = Game.getCleanLine(sc);
        //Parse the total number of direction out by using split
        String directionInfo[] = readIndex.split("\\s+");
        //throw away all useless spaces in between

        //numDirection is total # of direction
        int numDirection = Integer.parseInt(directionInfo[1]);
        for (int i = 0; i < numDirection; i++) {
            new Direction(sc);
        }

        /*-----------------------------------------------------------------------------*/
        /*-----------------------------------------------------------------------------*/
        //update scanner location to Character section
        if (version == 3.1) {  //backwards compatibility
            Console.displayln("No Character Found, Type in your name to create a character");
            String input = KeyboardScanner.getKeyboardScanner().nextLine();
            while (input.length() < 1) {
                Console.displayln("Character name can't be empty, please retry input a name: ");
                input = KeyboardScanner.getKeyboardScanner().nextLine();
                if (input.equalsIgnoreCase("EXIT") || input.equalsIgnoreCase("QUIT")) {
                    Console.displayln("User chose to exit, game exiting...");
                    System.exit(0);
                }
                if (input.length() > 0)
                    break;
            }
            Player p = new Player(1000, input, "This is a player", 12);
        }
        if (version > 3.1) { //backwards compatibility
            readIndex = Game.getCleanLine(sc);
            String CharacterInfo[] = readIndex.split("\\s+");
            //numArtifacts is the total # found known artifact
            int numCharacter = Integer.parseInt(CharacterInfo[1]);
            for (int i = 0; i < numCharacter; i++) {
                readIndex = Game.getCleanLine(sc);
                if (version < 5.1) { //backwards compatibility
                    String characterInfo[] = readIndex.split("\\s+"); //Player 12 or NPC 0...
                    //get the placeid and insert id
                    if (version < 5.0) {
                        if (characterInfo[0].equalsIgnoreCase("PLAYER")) {
                            int id = Integer.parseInt(characterInfo[1]);
                            new Player(sc, id, version);
                        } else {
                            int id = Integer.parseInt(characterInfo[1]);
                            new NPC(sc, id, version);
                        }
                    } else {
                        if (characterInfo[0].equalsIgnoreCase("PLAYER")) {
                            readIndex = Game.getCleanLine(sc);
                            int id = Integer.parseInt(readIndex);
                            new Player(sc, id, version);
                        } else {
                            readIndex = Game.getCleanLine(sc);
                            int id = Integer.parseInt(readIndex);
                            new NPC(sc, id, version);
                        }
                    }
                } else {
                    if (readIndex.equalsIgnoreCase("PLAYER")) {
                        readIndex = Game.getCleanLine(sc); // location 12
                        int id = Integer.parseInt(readIndex);
                        new Player(sc, id, version);
                    } else if (readIndex.equalsIgnoreCase("MONSTER")) {
                        readIndex = Game.getCleanLine(sc);
                        int id = Integer.parseInt(readIndex);
                        new Monster(sc, id, version);
                    } else if (readIndex.equalsIgnoreCase("PET")) {
                        readIndex = Game.getCleanLine(sc);
                        int id = Integer.parseInt(readIndex);
                        new Pet(sc, id, version);
                    } else if (readIndex.equalsIgnoreCase("NPC")) {
                        readIndex = Game.getCleanLine(sc);
                        int id = Integer.parseInt(readIndex);
                        new NPC(sc, id, version);
                    }
                }
            }
        }

        if (version < 5.1) {
            readIndex = Game.getCleanLine(sc);
            //Once it find Artifact section, split string again
            String ArtifactInfo[] = readIndex.split("\\s+");
            //throw away all useless spaces in between
            //numArtifacts is the total # found known artifact
            int numArtifacts = Integer.parseInt(ArtifactInfo[1]);
            //loop and create all known artifact in GDF
            for (int i = 0; i < numArtifacts; i++) {
                new Artifact(sc, version);
            }
        } else {
            readIndex = Game.getCleanLine(sc);
            //Once it find Artifact section, split string again
            String ArtifactInfo[] = readIndex.split("\\s+");
            //throw away all useless spaces in between
            //numArtifacts is the total # found known artifact
            int numArtifacts = Integer.parseInt(ArtifactInfo[1]);
            //loop and create all known artifact in GDF
            for (int i = 0; i < numArtifacts; i++) {
                readIndex = Game.getCleanLine(sc);
                if (readIndex.equalsIgnoreCase("A")) {
                    new Artifact(sc, version);
                } else if (readIndex.equalsIgnoreCase("F")) {
                    new Food(sc, version);
                } else if (readIndex.equalsIgnoreCase("M")) {
                    new Medicine(sc, version);
                } else if (readIndex.equalsIgnoreCase("W")) {
                    new Weapon(sc, version);
                } else if (readIndex.equalsIgnoreCase("K")) {
                    new Key(sc, version);
                } else {
                    Console.displayln("Artifact type not found : " + readIndex);
                }
            }
        }
    }

    //COMMENT PLEASE EXPLAIN WHAT THIS DOES.
    Game(Scanner sc, int people) {
        //READ gamename info until find something that is different from a blank line or start with
        String readIndex = Game.getCleanLine(sc);

        //Get the game name
        this.gameName = readIndex;
        String gameVersion[] = readIndex.split("\\s+");

        double version = Double.parseDouble(gameVersion[1]);
        readIndex = Game.getCleanLine(sc);

        //Split again and get the total number of places
        String placesplit[] = readIndex.split("\\s+");
        //throw away all useless spaces in between
        //numPlace is the total count of places that need to be inserted
        int numPlace = Integer.parseInt(placesplit[1]);

        //Start to loop through found places and add them into Place vector
        for (int i = 0; i < numPlace; i++) {
            new Place(sc, version);
        }
        //Adding no existing places
        Place Exit = new Place(1, "EXIT", "THIS IS EXIT");
        Place Nowhere = new Place(0, "Nowhere", "ThIS IS NOWHERE");

        /*-----------------------------------------------------------------------------*/
        /*-----------------------------------------------------------------------------*/
        //update the scanner location READ DIRECTION INFO such as Direction 35
        readIndex = Game.getCleanLine(sc);
        //Parse the total number of direction out by using split
        String directionInfo[] = readIndex.split("\\s+");
        //throw away all useless spaces in between

        //numDirection is total # of direction
        int numDirection = Integer.parseInt(directionInfo[1]);
        for (int i = 0; i < numDirection; i++) {
            new Direction(sc);
        }
        /*-----------------------------------------------------------------------------*/
        /*-----------------------------------------------------------------------------*/
        //update scanner location
        readIndex = sc.nextLine();
        if (version == 3.1) { //backwards compatibility
            int numCharacter = people;
            Console.displayln("Please input play name: ");
            String input = KeyboardScanner.getKeyboardScanner().nextLine();
            int playerID = 1000;
            int flag = 1;
            while (flag <= people) {
                new Player(playerID + numCharacter, input, "This is a player" + playerID, Place.getRandomePlace().getPlaceId());
                numCharacter++;
                flag++;
                if (flag != people)
                    input = KeyboardScanner.getKeyboardScanner().nextLine();
            }
            while (sc.hasNextLine()) {
                readIndex = sc.nextLine();
                if (readIndex.contains("ARTIFACTS"))
                    break;
            }
        }


        if (version > 3.1) {  //backwards compatibility
            while (readIndex.startsWith("//") || readIndex.equalsIgnoreCase(""))
                readIndex = sc.nextLine();
            //Once it find Artifact section, split string again
            String CharacterInfo[] = readIndex.split("//");
            //throw away all useless spaces in between
            String playerinfo[] = CharacterInfo[0].split("\\s+");

            if (Integer.parseInt(playerinfo[1]) == 0) {
                while (sc.hasNextLine()) {
                    readIndex = sc.nextLine();
                    if (readIndex.contains("ARTIFACTS"))
                        break;
                }
                int numCharacter = people;
                Console.displayln("Please input play name: ");
                String input = KeyboardScanner.getKeyboardScanner().nextLine();
                int playerID = 1000;
                int flag = 1;

                while (flag <= people) {
                    new Player(playerID + numCharacter, input, "This is a player" + playerID, Place.getRandomePlace().getPlaceId());
                    numCharacter++;
                    flag++;
                    if (flag != people)
                        input = KeyboardScanner.getKeyboardScanner().nextLine();
                }
            }
        }

        if (version < 5.1) {
            //Once it find Artifact section, split string again
            String ArtifactInfo[] = readIndex.split("\\s+");
            //throw away all useless spaces in between
            //numArtifacts is the total # found known artifact
            int numArtifacts = Integer.parseInt(ArtifactInfo[1]);
            //loop and create all known artifact in GDF
            for (int i = 0; i < numArtifacts; i++) {
                new Artifact(sc, version);
            }
        } else {
            //Once it find Artifact section, split string again
            String ArtifactInfo[] = readIndex.split("\\s+");
            //throw away all useless spaces in between
            //numArtifacts is the total # found known artifact
            int numArtifacts = Integer.parseInt(ArtifactInfo[1]);
            //loop and create all known artifact in GDF
            for (int i = 0; i < numArtifacts; i++) {
                readIndex = Game.getCleanLine(sc);
                if (readIndex.equalsIgnoreCase("A")) {
                    new Artifact(sc, version);
                } else if (readIndex.equalsIgnoreCase("F")) {
                    new Food(sc, version);
                } else if (readIndex.equalsIgnoreCase("M")) {
                    new Medicine(sc, version);
                } else if (readIndex.equalsIgnoreCase("W")) {
                    new Weapon(sc, version);
                } else if (readIndex.equalsIgnoreCase("K")) {
                    new Key(sc, version);
                } else {
                    Console.displayln("Artifact type not found : " + readIndex);
                }
            }
        }
    }

    public static double getGameVersion() {
        return GameVersion;
    }


    public static Place getCurrentPlace() {
        return currentPlace;
    }

    //Adds a Place to the collection of Places in the game. DEBUG USE ONLY
    public void prints(Character C) {
        C.getIO().displayln(this.gameName);
    }

    //gets a clean line void of comments and skips over empty lines
    public static String getCleanLine(Scanner sc) {
        String str;
        if (sc.hasNextLine()) {
            str = sc.nextLine();
        } else {
            Console.displayln("There is no next line to get.");
            return null;
        }
        int indexOfComment = str.indexOf("//");
        if (indexOfComment > -1) {
            str = str.substring(0, indexOfComment);
        }
        str = str.trim(); //trim off leading and trailing spaces and tabs

        if (str.length() > 0) {
            return str;
        } else
            return getCleanLine(sc); //recursively call itself until gets an actual clean line
    }

    //returns whether the passed direction checkDir is a valid one
    public static boolean isValidDirection(String checkDir) {
        String[] validDirections = {"N", "S", "E", "W", "U", "D", "NE", "NW", "SE", "SW",
                "NNE", "NNW", "ENE", "WNW", "ESE", "WSW", "SSE", "SSW",
                "North", "South", "East", "West", "Up", "Down",
                "Northeast", "Northwest", "Southeast", "Southwest",
                "North-Northeast", "North-Northwest", "East-Northeast", "West-Northwest",
                "East-Southeast", "West-Southwest", "South-Southeast", "South-Southwest"};

        for (String d : validDirections) {
            if (checkDir.equalsIgnoreCase(d))
                return true;
        }
        return false;
    }

    /*
    Play the game. Runs an infinite loop displaying the current Place and
    processing user input commands until the user enters QUIT or EXIT as
    described above. ( The user can also exit the game by navigating to a
    Place with ID of 1, e.g. by travelling west from the Entrance Hall in the
    example shown above. This should probably be implemented as an “Exit” Place,
    and checked for whenever the player moves from place to place.)
    */
    public void play() {

        if (GameVersion == 5.1) {
            System.out.println("\nWELCOME TO THE DUNGEON UNIVERSE GAME, WHERE PLAYERS BATTLE FOR VICTORY.");
            System.out.println("The Goal is to be the first player to kill the Big Boss, also known as The Titan.");
            System.out.println("The Titan lives in the lost island, a place even beyond the Secret Room.");
            System.out.println("Before entering the Secret Room, you have to find the correct keys and the excalibur.");

            System.out.println("\nHint: find golden key in Room 108 to enter the Treasure Storeroom. " +
                    "\nThere you can find the secret key. " +
                    "\nSpoiler: The excalibur is in Poison Room 1, but first find the obsidian key!" +
                    "\n(Using a map is very helpful)");

            for (Integer I : Character.characters.keySet()) {
                Character C = Character.getCharacterByID(I);
                C.setIO(new IO(C));
            }

            System.out.println("\nType in Yes whenever you're ready or No to quit");
            String s = KeyboardScanner.getKeyboardScanner().nextLine();
            if (s.equalsIgnoreCase("No")) {
                System.out.println("Thanks for playing the game.");
                System.out.println("Game Exiting...");
                System.exit(0);
            }
            System.out.println("Here we go...Starting with GUI mode.");
            new Console();
        }

        boolean flag = true; //flag for infinite loop use only
        while (flag) {
            for (Integer I : Character.characters.keySet()) {
                currentMover = Character.getCharacterByID(I);
                if (GameVersion < 5.1) {
                    currentMover.makeMove();
                    if (currentMover.getCurrentPlace().getPlaceId() == 1 || currentMover.getCurrentPlace().getPlaceId() == 0) {
                        //Print out exiting info.
                        currentMover.getIO().displayln("Thanks for playing the game.");
                        currentMover.getIO().displayln("Game Exiting...");
                        System.exit(0);
                    }
                } else { //if it's above 5.0, then this should activate a battle system underneath
                    if (currentMover.getID() == 15) { //if it's the titan, skip.
                        continue;
                    } else {
                        if (currentMover.health <= 0) //dead, then remove character from current location
                        {
                            currentMover.disperseArtifacts();
                            Place P = currentMover.getCurrentPlace();
                            P.removeCharacter(currentMover);
                            continue;
                        }
                        currentMover.makeMove(); //if character is alive, then make move


                        if (currentMover.getCurrentPlace().getPlaceId() == 1 || currentMover.getCurrentPlace().getPlaceId() == 0) {
                            //Print out exiting info.
                            currentMover.getIO().displayln("Thanks for playing the game.");
                            currentMover.getIO().displayln("Game Exiting...");
                            System.exit(0);
                        }
                        if (currentMover.getCurrentPlace().getPlaceId() == 109) {
                            boolean check = false; //
                            //check if player has the correct artifact to unlock the final Titan location
                            check = currentMover.getCurrentPlace().checkActivate(currentMover);
                            if (!check)
                                currentMover.getIO().displayln("Seems like you're not ready for the final Titan fight, come back anytime when you're ready");
                            if (check) {
                                boolean battleMode = false;
                                //check if player is willing to fight against the Titan (yes/no)
                                battleMode = currentMover.getCurrentPlace().checkPlayerResponds(currentMover);
                                Place p = Place.getPlaceByID(110);
                                p.addCharacter(currentMover);
                                Character Titan = Character.getCharacterByID(15); //Initialize Titan

                                //while the fight mode is on
                                while (battleMode) {
                                    //15% chance
                                    if (currentMover.getCurrentPlace().RoomEnvironment().equalsIgnoreCase("F") && currentMover.getID() != 15) {
                                        Place finalbossroom = currentMover.getCurrentPlace();
                                        finalbossroom.UseSpeicalEffect(currentMover);
                                    }

                                    if (currentMover.health <= 0) {
                                        currentMover.getIO().displayln("After a tough fight, " + currentMover.getName() + " lost the battle against the Titan!");
                                        currentMover.disperseArtifacts();
                                        currentMover.freepet();
                                        Titan.getCurrentPlace().removeCharacter(currentMover);
                                        currentMover.removeFromGame();
                                        currentMover.getIO().displayln("Resuming normal game mode");
                                        break;
                                    }
                                    currentMover.makeMove();
                                    if (Titan.health <= 0) {
                                        currentMover.getIO().displayln("\nAfter a tough fight, " + Titan.getName() + "'s is finally dead!");
                                        Titan.DropAllAritfacts(); //titan died, so drops all artifacts
                                        currentMover.getCurrentPlace().removeCharacter(Titan); //removed from place
                                        currentMover.getIO().displayln("Congratulations, you have defeat the Titan, game finished");
                                        currentMover.getIO().displayln("\nThanks for playing the game.");
                                        currentMover.getIO().displayln("Type EXIT to exit.");
                                        if (currentMover.getIO().getLine().equalsIgnoreCase("Exit"))
                                            System.exit(0);
                                    }
                                    Titan.makeMove(); //Titan make decision
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}